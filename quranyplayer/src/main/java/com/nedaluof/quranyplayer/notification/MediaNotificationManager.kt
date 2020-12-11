package com.nedaluof.quranyplayer.notification

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.RemoteException
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmap
import coil.Coil
import coil.api.load
import com.nedaluof.quranyplayer.BasePlayerActivity
import com.nedaluof.quranyplayer.R
import com.nedaluof.quranyplayer.exo.PlaybackState
import com.nedaluof.quranyplayer.model.AudioData
import com.nedaluof.quranyplayer.service.PlayerService

/**
 * This class is responsible for managing Notification
 * */
class MediaNotificationManager @Throws(RemoteException::class)
constructor(private val service: PlayerService) : BroadcastReceiver() {


    private var notificationManager: NotificationManager? = null
    private val playIntent: PendingIntent
    private val pauseIntent: PendingIntent
    private val previousIntent: PendingIntent
    private val nextIntent: PendingIntent
    private val stopIntent: PendingIntent
    private var collapsedRemoteViews: RemoteViews? = null
    private var expandedRemoteViews: RemoteViews? = null
    private var notificationBuilder: NotificationCompat.Builder? = null
    var started = false //To check if notification manager is started or not!


    private fun getPackageName(): String {
        return service.packageName
    }

    init {
        notificationManager = service.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        pauseIntent = PendingIntent.getBroadcast(
                service, NOTIFICATION_REQUEST_CODE,
                Intent(ACTION_PAUSE).setPackage(getPackageName()), PendingIntent.FLAG_CANCEL_CURRENT
        )
        playIntent = PendingIntent.getBroadcast(
                service, NOTIFICATION_REQUEST_CODE,
                Intent(ACTION_PLAY).setPackage(getPackageName()), PendingIntent.FLAG_CANCEL_CURRENT
        )
        previousIntent = PendingIntent.getBroadcast(
                service, NOTIFICATION_REQUEST_CODE,
                Intent(ACTION_PREV).setPackage(getPackageName()), PendingIntent.FLAG_CANCEL_CURRENT
        )
        nextIntent = PendingIntent.getBroadcast(
                service, NOTIFICATION_REQUEST_CODE,
                Intent(ACTION_NEXT).setPackage(getPackageName()), PendingIntent.FLAG_CANCEL_CURRENT
        )
        stopIntent = PendingIntent.getBroadcast(
                service, NOTIFICATION_REQUEST_CODE,
                Intent(ACTION_STOP).setPackage(getPackageName()), PendingIntent.FLAG_CANCEL_CURRENT
        )

        // Cancel all notifications to handle the case where the Service was killed and restarted by the system.
        notificationManager?.cancelAll()
    }

    /**
     * To start notification and service
     */
    fun createMediaNotification() {
        Log.i(TAG, "notifyMediaNotification called()")
        // The notification must be updated after setting started to true
        val filter = IntentFilter().apply {
            addAction(ACTION_NEXT)
            addAction(ACTION_PAUSE)
            addAction(ACTION_PLAY)
            addAction(ACTION_PREV)
            addAction(ACTION_STOP)
        }
        service.registerReceiver(this, filter)

        if (!started) {
            started = true
            service.startForeground(NOTIFICATION_ID, generateNotification())
        }
    }


    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_PAUSE -> service.pause()
            ACTION_PLAY -> service.playCurrentAudio()
            ACTION_NEXT -> service.skipToNext()
            ACTION_PREV -> service.skipToPrevious()
            ACTION_STOP -> {
                service.run {
                    unregisterReceiver(this@MediaNotificationManager)
                    stop()

                }
            }
            else -> Log.w(TAG, "Unknown intent ignored.")
        }
    }


    fun generateNotification(): Notification? {
        if (notificationBuilder == null) {
            notificationBuilder = NotificationCompat.Builder(service, CHANNEL_ID)
            notificationBuilder?.setSmallIcon(R.drawable.itunes)
                    ?.setLargeIcon(BitmapFactory.decodeResource(service.resources, R.drawable.itunes))
                    ?.setContentTitle(service.getString(R.string.app_name))
                    ?.setContentText(service.getString(R.string.app_name))
                    ?.setDeleteIntent(stopIntent)
                    ?.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    ?.setCategory(NotificationCompat.CATEGORY_TRANSPORT)
                    ?.setOnlyAlertOnce(true)

            // Notification channels are only supported on Android O+.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel()
            }
        }

        collapsedRemoteViews = RemoteViews(getPackageName(), R.layout.player_collapsed_notification)
        notificationBuilder?.setCustomContentView(collapsedRemoteViews)
        expandedRemoteViews = RemoteViews(getPackageName(), R.layout.player_expanded_notification)
        notificationBuilder?.setCustomBigContentView(expandedRemoteViews)

        notificationBuilder?.setContentIntent(createContentIntent())

        // To make sure that the notification can be dismissed by the user when we are not playing.
        notificationBuilder?.setOngoing(true)

        collapsedRemoteViews?.let { createCollapsedRemoteViews(it) }
        expandedRemoteViews?.let { createExpandedRemoteViews(it) }

        Coil.load(service, R.drawable.placeholder) {
            error(R.drawable.placeholder)
            target {
                collapsedRemoteViews?.setImageViewBitmap(
                        R.id.collapsed_image_view,
                        it.toBitmap()
                )
                expandedRemoteViews?.setImageViewBitmap(
                        R.id.expanded_image_view,
                        it.toBitmap()
                )
            }
        }


        if (service.getPlayState() == PlaybackState.STATE_PLAYING ||
                service.getPlayState() == PlaybackState.STATE_BUFFERING) showPauseIcon() else showPlayIcon()

        notificationManager?.notify(NOTIFICATION_ID, notificationBuilder?.build())
        return notificationBuilder?.build()
    }


    private fun createContentIntent(): PendingIntent {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("quranyplayer://")).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra(AudioData::class.java.name, service.getCurrentAudio())
            service.getCurrentAudioList().let {
                putExtra(BasePlayerActivity.AUDIO_LIST_KEY, it)
            }
        }

        return TaskStackBuilder.create(service).run {
            // Add the intent, which inflates the back stack
            addNextIntentWithParentStack(intent)
            // Get the PendingIntent containing the entire back stack
            getPendingIntent(NOTIFICATION_REQUEST_INTENT_CODE, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    private fun showPlayIcon() {
        collapsedRemoteViews?.setViewVisibility(R.id.collapsed_pause_image_view, View.GONE)
        collapsedRemoteViews?.setViewVisibility(R.id.collapsed_play_image_view, View.VISIBLE)
        expandedRemoteViews?.setViewVisibility(R.id.expanded_pause_image_view, View.GONE)
        expandedRemoteViews?.setViewVisibility(R.id.expanded_play_image_view, View.VISIBLE)
    }

    private fun showPauseIcon() {
        collapsedRemoteViews?.setViewVisibility(R.id.collapsed_pause_image_view, View.VISIBLE)
        collapsedRemoteViews?.setViewVisibility(R.id.collapsed_play_image_view, View.GONE)
        expandedRemoteViews?.setViewVisibility(R.id.expanded_pause_image_view, View.VISIBLE)
        expandedRemoteViews?.setViewVisibility(R.id.expanded_play_image_view, View.GONE)
    }

    private fun createExpandedRemoteViews(expandedRemoteViews: RemoteViews) {
        expandedRemoteViews.setOnClickPendingIntent(R.id.expanded_skip_back_image_view, previousIntent)
        expandedRemoteViews.setOnClickPendingIntent(R.id.expanded_clear_image_view, stopIntent)
        expandedRemoteViews.setOnClickPendingIntent(R.id.expanded_pause_image_view, pauseIntent)
        expandedRemoteViews.setOnClickPendingIntent(R.id.expanded_skip_next_image_view, nextIntent)
        expandedRemoteViews.setOnClickPendingIntent(R.id.expanded_play_image_view, playIntent)

        expandedRemoteViews.setImageViewResource(R.id.expanded_image_view, R.drawable.placeholder)
        expandedRemoteViews.setViewVisibility(R.id.expanded_skip_next_image_view, View.VISIBLE)
        expandedRemoteViews.setViewVisibility(R.id.expanded_skip_back_image_view, View.VISIBLE)
        expandedRemoteViews.setTextViewText(R.id.expanded_sura_name_text_view, service.getCurrentAudio()?.title)
        expandedRemoteViews.setTextViewText(R.id.expanded_reciter_name_text_view, service.getCurrentAudio()?.artist)

    }

    private fun createCollapsedRemoteViews(collapsedRemoteViews: RemoteViews) {
        collapsedRemoteViews.setOnClickPendingIntent(R.id.collapsed_skip_back_image_view, previousIntent)
        collapsedRemoteViews.setOnClickPendingIntent(R.id.collapsed_clear_image_view, stopIntent)
        collapsedRemoteViews.setOnClickPendingIntent(R.id.collapsed_pause_image_view, pauseIntent)
        collapsedRemoteViews.setOnClickPendingIntent(R.id.collapsed_skip_next_image_view, nextIntent)
        collapsedRemoteViews.setOnClickPendingIntent(R.id.collapsed_play_image_view, playIntent)
        // use a placeholder art while the remote art is being downloaded
        collapsedRemoteViews.setImageViewResource(R.id.collapsed_image_view, R.drawable.placeholder)
        collapsedRemoteViews.setViewVisibility(R.id.collapsed_skip_next_image_view, View.VISIBLE)
        collapsedRemoteViews.setViewVisibility(R.id.collapsed_skip_back_image_view, View.VISIBLE)
        collapsedRemoteViews.setTextViewText(R.id.collapsed_sura_name_text_view, service.getCurrentAudio()?.title)
        collapsedRemoteViews.setTextViewText(R.id.collapsed_reciter_name_text_view, service.getCurrentAudio()?.artist)
    }


    /**
     * Creates Notification Channel. This is required in Android O+ to display notifications.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        if (notificationManager?.getNotificationChannel(CHANNEL_ID) == null) {
            val notificationChannel = NotificationChannel(
                    CHANNEL_ID,
                    service.getString(R.string.notification_channel),
                    NotificationManager.IMPORTANCE_LOW
            )
            notificationChannel.description =
                    service.getString(R.string.notification_channel_description)
            notificationManager?.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        private val TAG = MediaNotificationManager::class.java.name
        private const val ACTION_PAUSE = "app.pause"
        private const val ACTION_PLAY = "app.play"
        private const val ACTION_PREV = "app.prev"
        private const val ACTION_NEXT = "app.next"
        private const val ACTION_STOP = "app.stop"
        private const val CHANNEL_ID = "app.QURANY_CHANNEL_ID"
        private const val NOTIFICATION_ID = 412
        private const val NOTIFICATION_REQUEST_CODE = 100
        private const val NOTIFICATION_REQUEST_INTENT_CODE = 125245
    }
}

