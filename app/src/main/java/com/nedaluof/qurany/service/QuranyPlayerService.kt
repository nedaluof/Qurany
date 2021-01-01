package com.nedaluof.qurany.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import androidx.core.app.TaskStackBuilder
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.nedaluof.qurany.R
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Sura
import com.nedaluof.qurany.ui.suras.SurasActivity
import com.nedaluof.qurany.util.Utility.getLogoAsBitmap
import com.nedaluof.qurany.util.Utility.getSuraPath
import com.nedaluof.qurany.util.toastySuccess

/**
 * Created by nedaluof on 12/27/2020.
 */
class QuranyPlayerService : Service() {

    private val player: SimpleExoPlayer by lazy {
        SimpleExoPlayer.Builder(this).build()
    }
    private var playerNotificationManager: PlayerNotificationManager? = null
    lateinit var sura: Sura // coming sura to run on the player
    lateinit var reciter: Reciter// coming reciter to serve the state of the suras activity
    private var isRunning = false

    // binder of the service to connect to the PlayerView in [SurasActivity]
    private var playerBinder = PlayerBinder()
    override fun onBind(intent: Intent?): IBinder = playerBinder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sura = intent?.getParcelableExtra(SURA_KEY)!!
        reciter = intent.getParcelableExtra(RECITER_KEY)!!
        startPlayer()
        isRunning = true
        return START_NOT_STICKY
    }

    private fun startPlayer() {
        if (this::sura.isInitialized) {
            when (sura.playingType) {
                PLAYING_ONLINE -> {
                    val suraURI = Uri.parse(sura.suraUrl)
                    val suraMediaSource = buildMediaSource(suraURI)
                    player.apply {
                        prepare(suraMediaSource)
                        playWhenReady = true
                    }
                }
                PLAYING_LOCALLY -> {
                    val localSuraPath = this.getSuraPath(sura.suraSubPath)
                    val suraURI = Uri.parse(localSuraPath)
                    val suraMediaSource = buildMediaSource(suraURI)
                    player.apply {
                        prepare(suraMediaSource)
                        playWhenReady = true
                    }
                    this@QuranyPlayerService.toastySuccess("Playing Locally")
                }
            }
            initPlayerNotification()
        }
    }

    fun playerIsRunning() = isRunning
    fun getCurrentSuraRunning(): Sura {
        return if (this::sura.isInitialized) {
            sura
        } else {
            Sura()
        }
    }

    private fun initPlayerNotification() {
        val mediaDescriptionAdapter = object : PlayerNotificationManager.MediaDescriptionAdapter {
            override fun getCurrentContentTitle(player: Player): CharSequence =
                    sura.reciterName

            override fun createCurrentContentIntent(player: Player): PendingIntent? {
                val resultIntent = Intent(this@QuranyPlayerService, SurasActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
                    reciter.isPlayingNow = true
                    putExtra(RECITER_KEY, reciter)
                }
                // Create the TaskStackBuilder
                return TaskStackBuilder.create(this@QuranyPlayerService).run {
                    addNextIntentWithParentStack(resultIntent)
                    getPendingIntent(System.currentTimeMillis().toInt(), PendingIntent.FLAG_UPDATE_CURRENT)
                }
            }

            override fun getCurrentContentText(player: Player): CharSequence = sura.suraName
            override fun getCurrentLargeIcon(player: Player, callback: PlayerNotificationManager.BitmapCallback) =
                    this@QuranyPlayerService.getLogoAsBitmap()
        }

        val notificationListener = object :
                PlayerNotificationManager.NotificationListener {
            override fun onNotificationStarted(notificationId: Int, notification: Notification) {
                startForeground(notificationId, notification)
            }

            override fun onNotificationCancelled(notificationId: Int) {
                stopSelf()
            }
        }

        playerNotificationManager = PlayerNotificationManager.createWithNotificationChannel(
                this,
                R.string.notification_id.toString(),
                R.string.app_name,
                R.string.notification_channel_des,
                1,
                mediaDescriptionAdapter,
                notificationListener
        ).also { it.setPlayer(player) }
    }

    fun getPlayerInstance(): SimpleExoPlayer {
        if (!player.isPlaying) {
            startPlayer()
        }
        return player
    }

    fun releasePlayer() {
        playerNotificationManager?.setPlayer(null)
        player.release()
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(this, "Qurany_Player")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri)
    }

    fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        releasePlayer()
        super.onDestroy()
    }

    inner class PlayerBinder : Binder() {
        val playerService: QuranyPlayerService
            get() = this@QuranyPlayerService
    }

    companion object {
        private const val TAG = "QuranyPlayerService"
        const val SURA_KEY = "SURA_KEY"
        const val RECITER_KEY = "RECITER_KEY"
        const val PLAYING_ONLINE = 1
        const val PLAYING_LOCALLY = 2
    }
}


/*val subPath = "/Qurany/${sura.reciterName}/${SuraUtil.getSuraName(sura.id)}.mp3"
            if (!this.checkIfSuraExist(subPath)) {
                if (NetworkUtil.isNetworkOk(this)) {
                    val suraURI = Uri.parse(sura.suraUrl)
                    val suraMediaSource = buildMediaSource(suraURI)
                    player.apply {
                        prepare(suraMediaSource)
                        playWhenReady = true
                    }
                    initPlayerNotification()
                } else {
                    this@QuranyPlayerService.toastyError(R.string.alrt_no_internet_msg)
                    stopSelf()
                }
            } else if (this.checkIfSuraExist(subPath)) {
                val localSuraPath = this.getSuraPath(subPath)
                val suraURI = Uri.parse(localSuraPath)
                val suraMediaSource = buildMediaSource(suraURI)
                player.apply {
                    prepare(suraMediaSource)
                    playWhenReady = true
                }
                initPlayerNotification()
                this@QuranyPlayerService.toastySuccess("Playing Locally")
            } else {
                this@QuranyPlayerService.toastyError(R.string.alrt_no_internet_msg)
                stopSelf()
            }*/