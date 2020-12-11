package com.nedaluof.quranyplayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.nedaluof.quranyplayer.PlayerViewModel.Companion.getPlayerViewModelInstance
import com.nedaluof.quranyplayer.model.AudioData
import com.nedaluof.quranyplayer.service.OnPlayerServiceCallback
import com.nedaluof.quranyplayer.service.PlayerService


open class BasePlayerActivity : AppCompatActivity(), OnPlayerServiceCallback {


    private var service: PlayerService? = null
    private var bound = false
    private var audioData: AudioData? = null
    private var audioList: MutableList<AudioData>? = null
    private var msg = 0
    val playerViewModel: PlayerViewModel = getPlayerViewModelInstance()


    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                ACTION_PLAY_AUDIO -> audioData?.let { service?.play(it) }
                ACTION_PLAY_AUDIO_IN_LIST -> service?.play(audioList, audioData)
                ACTION_PAUSE -> service?.pause()
                ACTION_STOP -> {
                    service?.stop()
                    playerViewModel.stop()
                }
            }
        }
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private val mConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to PlayerService, cast the IBinder and get PlayerService instance
            val binder = service as PlayerService.LocalBinder
            this@BasePlayerActivity.service = binder.service
            bound = true
            this@BasePlayerActivity.service?.subscribeToAudioPlayerUpdates()
            handler.sendEmptyMessage(msg)
            this@BasePlayerActivity.service?.addListener(this@BasePlayerActivity)
        }

        override fun onServiceDisconnected(classname: ComponentName) {
            bound = false
            service?.removeListener()
            service = null
        }
    }

    private fun bindPlayerService() {
        if (!bound) bindService(Intent(this, PlayerService::class.java), mConnection, Context.BIND_AUTO_CREATE)
    }


    fun play(audioList: MutableList<AudioData>?, audioData: AudioData) {
        msg = ACTION_PLAY_AUDIO_IN_LIST
        this.audioData = audioData
        this.audioList = audioList
        playerViewModel.setPlayStatus(true)
        if (service == null) bindPlayerService()
        else handler.sendEmptyMessage(msg)
    }

    fun play(audioData1: AudioData) {
        msg = ACTION_PLAY_AUDIO
        audioData = audioData1
        if (service == null) bindPlayerService()
        else handler.sendEmptyMessage(msg)
    }

    private fun pause() {
        msg = ACTION_PAUSE
        playerViewModel.setPlayStatus(false)
        if (service == null) bindPlayerService()
        else handler.sendEmptyMessage(msg)
    }

    fun stop() {
        msg = ACTION_STOP
        playerViewModel.setPlayStatus(false)
        if (service == null) bindPlayerService()
        else handler.sendEmptyMessage(msg)
    }

    fun next() {
        service?.skipToNext()
    }

    fun previous() {
        service?.skipToPrevious()
    }

    fun toggle() {
        if (playerViewModel.isPlayData.value == true) pause()
        else playerViewModel.playerData.value?.let { it1 -> play(audioList, it1) }
    }

    fun seekTo(position: Long?) {
        position?.let { nonNullPosition ->
            playerViewModel.seekTo(nonNullPosition)
            service?.seekTo(nonNullPosition)
        }
    }

    fun addNewPlaylistToCurrent(songList: ArrayList<AudioData>) {
        service?.addNewPlaylistToCurrent(songList)
    }

    fun shuffle() {
        service?.onShuffle(playerViewModel.isShuffleData.value ?: false)
        playerViewModel.shuffle()
    }

    fun repeatAll() {
        service?.onRepeatAll(playerViewModel.isRepeatAllData.value ?: false)
        playerViewModel.repeatAll()
    }

    fun repeat() {
        service?.onRepeat(playerViewModel.isRepeatData.value ?: false)
        playerViewModel.repeat()
    }

    override fun updateAudioData(audioData: AudioData) {
        playerViewModel.updateAudio(audioData)
    }

    override fun setPlayStatus(isPlay: Boolean) {
        playerViewModel.setPlayStatus(isPlay)
    }

    override fun updateAudioProgress(duration: Long, position: Long) {
        playerViewModel.setChangePosition(position, duration)
    }

    override fun setBufferingData(isBuffering: Boolean) {
        playerViewModel.setBuffering(isBuffering)
    }

    override fun setVisibilityData(isVisibility: Boolean) {
        playerViewModel.setVisibility(isVisibility)
    }

    private fun unbindService() {
        if (bound) {
            unbindService(mConnection)
            bound = false
        }
    }

    override fun stopService() {
        unbindService()
        service = null
    }

    override fun onDestroy() {
        stopService()
        super.onDestroy()
    }


    companion object {

        private val TAG = BasePlayerActivity::class.java.name
        const val AUDIO_LIST_KEY = "AUDIO_LIST_KEY"
        private const val ACTION_PLAY_AUDIO_IN_LIST = 1
        private const val ACTION_PAUSE = 2
        private const val ACTION_STOP = 3
        private const val ACTION_PLAY_AUDIO = 4
    }
}