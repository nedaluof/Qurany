package com.nedaluof.quranyplayer.exo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.C.CONTENT_TYPE_MUSIC
import com.google.android.exoplayer2.C.USAGE_MEDIA
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.nedaluof.quranyplayer.model.AudioData
import com.nedaluof.quranyplayer.service.PlayerService

/**
 * This class is responsible for managing the quranyplayer(actions, state, ...) using [ExoPlayer]
 * */
class ExoPlayerManager(val context: Context) : OnExoPlayerManagerCallback {

    private var wifiLock: WifiManager.WifiLock? = null
    private var audioManager: AudioManager? = null
    private val eventListener = ExoPlayerEventListener()
    private val audioNoisyIntentFilter = IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY)
    private var exoAudioStateCallback: OnExoPlayerManagerCallback.OnAudioStateCallback? = null
    private var audioNoisyReceiverRegistered: Boolean = false
    private var currentAudio: AudioData? = null
    private var currentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
    private var exoPlayer: SimpleExoPlayer? = null
    private var playOnFocusGain = false


    private val mAudioNoisyReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY == intent.action) {
                Log.d(TAG, "Headphones disconnected.")
                if (playOnFocusGain || exoPlayer != null && exoPlayer?.playWhenReady == true) {
                    val i = Intent(context, PlayerService::class.java).apply {
                        action = PlayerService.ACTION_CMD
                        putExtra(PlayerService.CMD_NAME, PlayerService.CMD_PAUSE)
                    }
                    context.applicationContext.startService(i)
                }
            }
        }
    }

    private val mUpdateProgressHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val duration = exoPlayer?.duration ?: 0
            val position = exoPlayer?.currentPosition ?: 0
            onUpdateProgress(position, duration)
            sendEmptyMessageDelayed(0, UPDATE_PROGRESS_DELAY)
        }
    }

    // Whether to return STATE_NONE or STATE_STOPPED when mExoPlayer is null;
    private var mExoPlayerIsStopped = false
    private val mOnAudioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        //Log.d(TAG, "onAudioFocusChange. focusChange= $focusChange")
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> currentAudioFocusState = AUDIO_FOCUSED
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ->
                // Audio focus was lost, but it's possible to duck (i.e.: play quietly)
                currentAudioFocusState = AUDIO_NO_FOCUS_CAN_DUCK
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                // Lost audio focus, but will gain it back (shortly), so note whether
                // playback should resume
                currentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
                playOnFocusGain = exoPlayer != null && exoPlayer?.playWhenReady ?: false
            }
            AudioManager.AUDIOFOCUS_LOSS ->
                // Lost audio focus, probably "permanently"
                currentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
        }
        // Update the quranyplayer state based on the change
        configurePlayerState()
    }

    init {
        this.audioManager =
                context.applicationContext?.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        // Create the Wifi lock (this does not acquire the lock, this just creates it)
        this.wifiLock =
                (context.applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager)
                        .createWifiLock(WifiManager.WIFI_MODE_FULL, "app_lock")
    }

    override fun setCallback(callback: OnExoPlayerManagerCallback.OnAudioStateCallback) {
        exoAudioStateCallback = callback
    }

    private fun onUpdateProgress(position: Long, duration: Long) {
        Log.d(TAG, "onUpdateProgress() called with: position = $position, duration = $duration")
        exoAudioStateCallback?.setCurrentPosition(position, duration)
    }


    private fun setCurrentAudioState() {
        var state = 0
        if (exoPlayer == null) {
            state = if (mExoPlayerIsStopped) PlaybackState.STATE_STOPPED
            else PlaybackState.STATE_NONE
            exoAudioStateCallback?.onPlaybackStatusChanged(state)
        }
        when (exoPlayer?.playbackState) {
            Player.STATE_IDLE -> PlaybackState.STATE_PAUSED
            Player.STATE_BUFFERING -> PlaybackState.STATE_BUFFERING
            Player.STATE_READY -> {
                if (exoPlayer?.playWhenReady == true) PlaybackState.STATE_PLAYING
                else PlaybackState.STATE_PAUSED
            }
            Player.STATE_ENDED -> PlaybackState.STATE_STOPPED
            else -> PlaybackState.STATE_NONE
        }.also { state = it }
        exoAudioStateCallback?.onPlaybackStatusChanged(state)
    }


    override fun getCurrentStreamPosition(): Long {
        return exoPlayer?.currentPosition ?: 0
    }

    override fun play(audioData: AudioData) {
        playOnFocusGain = true
        tryToGetAudioFocus()
        registerAudioNoisyReceiver()

        val audioHasChanged = audioData.audioId != currentAudio?.audioId
        if (audioHasChanged) currentAudio = audioData

        if (audioHasChanged || exoPlayer == null) {
            releaseResources(false) // release everything except the quranyplayer
            val source = currentAudio?.source
            if (exoPlayer == null) {
                exoPlayer = SimpleExoPlayer.Builder(context).build()
                exoPlayer?.addListener(eventListener)
            }

            // Android "O" makes much greater use of AudioAttributes, especially
            // with regards to AudioFocus. All of tracks are music, but
            // if your content includes spoken word such as audio books or pod casts
            // then the content type should be set to CONTENT_TYPE_SPEECH for those
            // tracks.
            val audioAttributes = AudioAttributes.Builder()
                    .setContentType(CONTENT_TYPE_MUSIC)
                    .setUsage(USAGE_MEDIA)
                    .build()
            exoPlayer?.audioAttributes = audioAttributes

            // Produces DataSource instances through which media data is loaded.
            val dataSourceFactory = buildDataSourceFactory(context)
            // Produces Extractor instances for parsing the media data.
            val extractorsFactory = DefaultExtractorsFactory()


            val mediaSource = when (currentAudio?.audioType) {
                C.TYPE_OTHER ->
                    ProgressiveMediaSource.Factory(dataSourceFactory , extractorsFactory)
                            .createMediaSource(Uri.parse(source))
                else ->
                    HlsMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(source))
            }

            // Prepares media to play (happens on background thread) and triggers
            // {@code onPlayerStateChanged} callback when the stream is ready to play.
            exoPlayer?.prepare(mediaSource)

            // If we are streaming from the internet, we want to hold a
            // Wifi lock, which prevents the Wifi radio from going to
            // sleep while the audio is playing.
            wifiLock?.acquire()
        }
        configurePlayerState()
    }


    private fun buildDataSourceFactory(context: Context): DataSource.Factory {
        val dataSourceFactory = DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, BuildConfig.APPLICATION_ID),
                BANDWIDTH_METER
        )
        return DefaultDataSourceFactory(context, BANDWIDTH_METER, dataSourceFactory)
    }

    override fun pause() {
        Log.d(TAG, "pause() called")
        exoPlayer?.playWhenReady = false
        // While paused, retain the quranyplayer instance, but give up audio focus.
        releaseResources(false)
        unregisterAudioNoisyReceiver()
    }

    override fun stop() {
        Log.d(TAG, "stop() called")
        giveUpAudioFocus()
        releaseResources(true)
        unregisterAudioNoisyReceiver()
        setCurrentAudioState()
    }

    override fun seekTo(position: Long) {
        Log.d(TAG, "seekTo() called with: position = $position")
        registerAudioNoisyReceiver()
        exoPlayer?.seekTo(position)
    }

    private fun tryToGetAudioFocus() {
        Log.d(TAG, "tryToGetAudioFocus")
        val result = audioManager?.requestAudioFocus(
                mOnAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
        )
        currentAudioFocusState = if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            AUDIO_FOCUSED
        } else {
            AUDIO_NO_FOCUS_NO_DUCK
        }
    }

    private fun giveUpAudioFocus() {
        Log.d(TAG, "giveUpAudioFocus")
        if (audioManager?.abandonAudioFocus(mOnAudioFocusChangeListener) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            currentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
        }
    }

    /**
     * Reconfigures the quranyplayer according to audio focus settings and starts/restarts it. This method
     * starts/restarts the ExoPlayer instance respecting the current audio focus state. So if we
     * have focus, it will play normally; if we don't have focus, it will either leave the quranyplayer
     * paused or set it to a low volume, depending on what is permitted by the current focus
     * settings.
     */
    private fun configurePlayerState() {
        Log.d(TAG, "configurePlayerState. mCurrentAudioFocusState= $currentAudioFocusState")
        if (currentAudioFocusState == AUDIO_NO_FOCUS_NO_DUCK) {
            // We don't have audio focus and can't duck, so we have to pause
            pause()
        } else {
            registerAudioNoisyReceiver()

            if (currentAudioFocusState == AUDIO_NO_FOCUS_CAN_DUCK)
            // We're permitted to play, but only if we 'duck', ie: play softly
                exoPlayer?.volume = VOLUME_DUCK
            else
                exoPlayer?.volume = VOLUME_NORMAL


            // If we were playing when we lost focus, we need to resume playing.
            if (playOnFocusGain) {
                exoPlayer?.playWhenReady = true
                playOnFocusGain = false
            }
        }
    }

    /**
     * Releases resources used by the service for playback, which is mostly just the WiFi lock for
     * local playback. If requested, the ExoPlayer instance is also released.
     *
     * @param releasePlayer Indicates whether the quranyplayer should also be released
     */
    private fun releaseResources(releasePlayer: Boolean) {
        Log.d(TAG, "releaseResources. releasePlayer= $releasePlayer")

        // Stops and releases quranyplayer (if requested and available).
        if (releasePlayer) {
            mUpdateProgressHandler.removeMessages(0)
            exoPlayer?.release()
            exoPlayer?.removeListener(eventListener)
            exoPlayer = null
            mExoPlayerIsStopped = true
            playOnFocusGain = false
        }

        if (wifiLock?.isHeld == true) {
            wifiLock?.release()
        }
    }

    private fun registerAudioNoisyReceiver() {
        if (!audioNoisyReceiverRegistered) {
            context.applicationContext.registerReceiver(
                    mAudioNoisyReceiver,
                    audioNoisyIntentFilter
            )
            audioNoisyReceiverRegistered = true
        }
    }

    private fun unregisterAudioNoisyReceiver() {
        if (audioNoisyReceiverRegistered) {
            context.applicationContext.unregisterReceiver(mAudioNoisyReceiver)
            audioNoisyReceiverRegistered = false
        }
    }

    private inner class ExoPlayerEventListener : Player.EventListener {

        override fun onLoadingChanged(isLoading: Boolean) {
            // Nothing to do.
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            when (playbackState) {
                Player.STATE_IDLE, Player.STATE_BUFFERING, Player.STATE_READY -> {
                    setCurrentAudioState()
                    mUpdateProgressHandler.sendEmptyMessage(0)
                }
                Player.STATE_ENDED -> {
                    // The media quranyplayer finished playing the current audio.
                    mUpdateProgressHandler.removeMessages(0)
                    exoAudioStateCallback?.onCompletion()
                }
            }
        }

        override fun onPlayerError(error: ExoPlaybackException) {
            val what: String = when (error.type) {
                ExoPlaybackException.TYPE_SOURCE -> error.sourceException.message ?: ""
                ExoPlaybackException.TYPE_RENDERER -> error.rendererException.message ?: ""
                ExoPlaybackException.TYPE_UNEXPECTED -> error.unexpectedException.message ?: ""
                else -> "onPlayerError: $error"
            }
            Log.e(TAG, "onPlayerError: $what")
        }

        override fun onPositionDiscontinuity(reason: Int) {
            // Nothing to do.
        }

        override fun onSeekProcessed() {
            // Nothing to do.
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            // Nothing to do.
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            // Nothing to do.
        }
    }

    companion object {

        private val TAG = "ExoPlayerManager"
        const val UPDATE_PROGRESS_DELAY = 500L
        private val BANDWIDTH_METER = DefaultBandwidthMeter()

        // The volume we set the media quranyplayer to when we lose audio focus, but are
        // allowed to reduce the volume instead of stopping playback.
        private const val VOLUME_DUCK = 0.2f

        // The volume we set the media quranyplayer when we have audio focus.
        private const val VOLUME_NORMAL = 1.0f

        // we don't have audio focus, and can't duck (play at a low volume)
        private const val AUDIO_NO_FOCUS_NO_DUCK = 0

        // we don't have focus, but can duck (play at a low volume)
        private const val AUDIO_NO_FOCUS_CAN_DUCK = 1

        // we have full audio focus
        private const val AUDIO_FOCUSED = 2
    }
}