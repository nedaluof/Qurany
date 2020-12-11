package com.nedaluof.quranyplayer.media

import android.util.Log
import com.nedaluof.quranyplayer.exo.OnExoPlayerManagerCallback
import com.nedaluof.quranyplayer.model.AudioData
import com.nedaluof.quranyplayer.playlist.PlaylistManager


/**
 * This class is used to interact with [ExoPlayerManager] & [PlaylistManager]
 * */
class MediaAdapter(
        private val onExoPlayerManagerCallback: OnExoPlayerManagerCallback,
        private val mediaAdapterCallback: OnMediaAdapterCallback
) : OnExoPlayerManagerCallback.OnAudioStateCallback, PlaylistManager.OnAudioUpdateListener {

    private var playlistManager: PlaylistManager? = null

    init {
        onExoPlayerManagerCallback.setCallback(this)
        playlistManager = PlaylistManager(this)
    }

    fun play(audioData: AudioData) {
        onExoPlayerManagerCallback.play(audioData)
    }

    fun play(audioList: MutableList<AudioData>, audioData: AudioData) {
        playlistManager?.setCurrentPlaylist(audioList, audioData)
    }

    fun pause() {
        onExoPlayerManagerCallback.pause()
    }

    fun seekTo(position: Long) {
        onExoPlayerManagerCallback.seekTo(position)
    }

    fun stop() {
        onExoPlayerManagerCallback.stop()
    }

    fun skipToNext() {
        playlistManager?.skipPosition(1)
    }

    fun skipToPrevious() {
        playlistManager?.skipPosition(-1)
    }

    fun addToCurrentPlaylist(audioList: ArrayList<AudioData>) {
        Log.d(TAG, "addToCurrentPlaylist() called with: audioList = $audioList")
        playlistManager?.addToPlaylist(audioList)
    }

    fun addToCurrentPlaylist(audioData: AudioData) {
        Log.d(TAG, "addToCurrentPlaylist() called with: audio = $audioData")
        playlistManager?.addToPlaylist(audioData)
    }

    override fun shuffle(isShuffle: Boolean) {
        playlistManager?.setShuffle(isShuffle)
    }

    override fun repeatAll(isRepeatAll: Boolean) {
        playlistManager?.setRepeatAll(isRepeatAll)
    }

    override fun repeat(isRepeat: Boolean) {
        playlistManager?.setRepeat(isRepeat)
    }


    override fun onAudioChanged(audioData: AudioData) {
        play(audioData)
        mediaAdapterCallback.onAudioChanged(audioData)
    }

    override fun onAudioRetrieveError() {
        //Log.d(TAG, "onAudioRetrieveError called")
    }

    override fun onPlaybackStatusChanged(state: Int) {
        mediaAdapterCallback.onPlaybackStateChanged(state)
    }

    override fun getCurrentAudioList(): ArrayList<AudioData>? {
        return playlistManager?.getCurrentAudioList()
    }

    override fun getCurrentAudio(): AudioData? {
        return playlistManager?.getCurrentAudio()
    }

    override fun setCurrentPosition(position: Long, duration: Long) {
        mediaAdapterCallback.setDuration(duration, position)
    }


    override fun onCompletion() {
        if (playlistManager?.isRepeat() == true) {
            onExoPlayerManagerCallback.stop()
            playlistManager?.repeat()
            return
        }

        if (playlistManager?.hasNext() == true) {
            playlistManager?.skipPosition(1)
            return
        }

        if (playlistManager?.isRepeatAll() == true) {
            playlistManager?.skipPosition(-1)
            return
        }

        onExoPlayerManagerCallback.stop()
    }


    companion object {
        private val TAG = MediaAdapter::class.java.name
    }

}