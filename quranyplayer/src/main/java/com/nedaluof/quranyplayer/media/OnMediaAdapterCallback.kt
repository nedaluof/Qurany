package com.nedaluof.quranyplayer.media

import com.nedaluof.quranyplayer.model.AudioData
import java.util.*

/**
 * To return the result of [MediaAdapter]
 * and also to make an interaction between [PlayerService] & [MediaAdapter]
 * */
interface OnMediaAdapterCallback {

    fun onAudioChanged(audioData: AudioData)

    fun onPlaybackStateChanged(state: Int)

    fun setDuration(duration: Long, position: Long)

    fun addNewPlaylistToCurrent(audioList: ArrayList<AudioData>)

    fun onShuffle(isShuffle: Boolean)

    fun onRepeat(isRepeat: Boolean)

    fun onRepeatAll(repeatAll: Boolean)

}