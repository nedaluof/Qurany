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
import com.nedaluof.qurany.util.SuraUtil
import com.nedaluof.qurany.util.Utility.getLogoAsBitmap


/**
 * Created by nedaluof on 12/27/2020.
 */
class QuranyPlayerService : Service() {

    // binder of the service to connect to the PlayerView
    private var player: SimpleExoPlayer? = null
    private var playerBinder = PlayerBinder()
    private var playerNotificationManager: PlayerNotificationManager? = null
    lateinit var sura: Sura //coming sura to play on the player
    lateinit var reciter: Reciter
    override fun onBind(intent: Intent?): IBinder = playerBinder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sura = intent?.getParcelableExtra(SURA_KEY)!!
        reciter = intent.getParcelableExtra(RECITER_KEY)!!
        startPlayer()
        return START_STICKY
    }

    private fun startPlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        if (this::sura.isInitialized && this::reciter.isInitialized) {
            val server = reciter.server + "/" + SuraUtil.getSuraIndex(sura.id) + ".mp3"
            val suraURI = Uri.parse(server)
            val suraMediaSource = buildMediaSource(suraURI)
            player?.apply {
                prepare(suraMediaSource)
                playWhenReady = true
            }
            initPlayerNotification()
        }
    }

    private fun initPlayerNotification() {
        val mediaDescriptionAdapter = object : PlayerNotificationManager.MediaDescriptionAdapter {
            override fun getCurrentContentTitle(player: Player): CharSequence =
                    reciter.name!!

            override fun createCurrentContentIntent(player: Player): PendingIntent? {
                val resultIntent = Intent(this@QuranyPlayerService, SurasActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
                }
                // Create the TaskStackBuilder
                return TaskStackBuilder.create(this@QuranyPlayerService).run {
                    // Add the intent, which inflates the back stack
                    addNextIntentWithParentStack(resultIntent)
                    // Get the PendingIntent containing the entire back stack
                    getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
                }
            }

            override fun getCurrentContentText(player: Player): CharSequence = sura.name
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
        if (player == null) {
            startPlayer()
        }
        return player!!
    }

    private fun releasePlayer() {
        if (player != null) {
            playerNotificationManager?.setPlayer(null)
            player?.release()
            player = null
        }
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(this, "Qurany_Player")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri)
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
    }
}