package com.nedaluof.quranyplayer.service

import android.app.Service
import android.content.Intent
import android.media.session.PlaybackState
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.content.ContextCompat
import com.nedaluof.quranyplayer.exo.ExoPlayerManager
import com.nedaluof.quranyplayer.media.MediaAdapter
import com.nedaluof.quranyplayer.media.OnMediaAdapterCallback
import com.nedaluof.quranyplayer.model.AudioData
import com.nedaluof.quranyplayer.notification.MediaNotificationManager
import java.util.*


class PlayerService : Service(), OnMediaAdapterCallback {

    private var mediaAdapter: MediaAdapter? = null
    private var notificationManager: MediaNotificationManager? = null
    private val binder = LocalBinder()
    private var playState = 0
    var callback: OnPlayerServiceCallback? = null
    var command: String? = null


    override fun onCreate() {
        super.onCreate()
        val exoPlayerManager = ExoPlayerManager(this)
        mediaAdapter = MediaAdapter(exoPlayerManager, this)
        notificationManager = MediaNotificationManager(this)
        notificationManager?.createMediaNotification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand() called with: intent = $intent, flags = $flags, startId = $startId")
        return START_NOT_STICKY
    }

    fun subscribeToAudioPlayerUpdates() {
        Log.d(TAG, "subscribeToAudioPlayerUpdates() called")

        /* Binding to this service doesn't actually trigger onStartCommand(). That is needed to
        * ensure this Service can be promoted to a foreground service.
        * */
        ContextCompat.startForegroundService(applicationContext, Intent(this, PlayerService::class.java))
    }

    fun addListener(callback: OnPlayerServiceCallback) {
        this.callback = callback
    }

    fun removeListener() {
        callback = null
    }

    fun getCurrentAudio(): AudioData? {
        return mediaAdapter?.getCurrentAudio()
    }

    fun getCurrentAudioList(): ArrayList<AudioData>? {
        return mediaAdapter?.getCurrentAudioList()
    }

    fun getPlayState(): Int = playState

    override fun onAudioChanged(audioData: AudioData) {
        callback?.updateAudioData(audioData)
    }

    override fun onShuffle(isShuffle: Boolean) {
        mediaAdapter?.shuffle(isShuffle)
    }

    override fun onRepeatAll(repeatAll: Boolean) {
        mediaAdapter?.repeatAll(repeatAll)
    }

    override fun onRepeat(isRepeat: Boolean) {
        mediaAdapter?.repeat(isRepeat)
    }

    fun playCurrentAudio() {
        getCurrentAudio()?.let { play(it) }
    }

    fun play(audioData: AudioData?) {
        audioData?.let { mediaAdapter?.play(it) }
    }

    fun play(audioList: MutableList<AudioData>?, audioData: AudioData?) {
        audioData?.let { nonNullAudio ->
            audioList?.let { mediaAdapter?.play(it, nonNullAudio) } ?: play(nonNullAudio)
        }
    }

    fun pause() {
        mediaAdapter?.pause()
    }

    fun stop() {
        mediaAdapter?.stop()
        stopForeground(true)
        notificationManager = null
        stopSelf()
        callback?.stopService()
    }

    override fun addNewPlaylistToCurrent(audioList: ArrayList<AudioData>) {
        mediaAdapter?.addToCurrentPlaylist(audioList)
    }

    override fun setDuration(duration: Long, position: Long) {
        callback?.updateAudioProgress(duration, position)
    }

    fun skipToNext() {
        mediaAdapter?.skipToNext()
    }

    fun skipToPrevious() {
        mediaAdapter?.skipToPrevious()
    }

    fun seekTo(position: Long) {
        mediaAdapter?.seekTo(position)
    }


    override fun onPlaybackStateChanged(state: Int) {
        playState = state
        when (state) {
            PlaybackState.STATE_BUFFERING -> {
                callback?.setBufferingData(true)
                callback?.setVisibilityData(true)
                callback?.setPlayStatus(true)
            }

            PlaybackState.STATE_PLAYING -> {
                callback?.setBufferingData(false)
                callback?.setVisibilityData(true)
                callback?.setPlayStatus(true)
            }

            PlaybackState.STATE_PAUSED -> {
                callback?.setBufferingData(false)
                callback?.setVisibilityData(true)
                callback?.setPlayStatus(false)
            }

            else -> {
                callback?.setBufferingData(false)
                callback?.setVisibilityData(false)
                callback?.setPlayStatus(false)
            }
        }
        notificationManager?.generateNotification()
    }

    private fun unsubscribeToAudioPlayerUpdates() {
        Log.d(TAG, "unsubscribeToAudioPlayerUpdates() called")
        removeListener()
    }

    override fun onDestroy() {
        unsubscribeToAudioPlayerUpdates()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        val action = intent.action
        command = intent.getStringExtra(CMD_NAME)
        if (ACTION_CMD == action && CMD_PAUSE == command) {
            mediaAdapter?.pause()
        }
        return binder
    }

    inner class LocalBinder : Binder() {
        // Return this instance of PlayerService so clients can call public methods
        val service: PlayerService
            get() = this@PlayerService
    }



    companion object {

        private val TAG = PlayerService::class.java.name
        const val ACTION_CMD = "app.ACTION_CMD"
        const val CMD_NAME = "CMD_NAME"
        const val CMD_PAUSE = "CMD_PAUSE"
    }
}
