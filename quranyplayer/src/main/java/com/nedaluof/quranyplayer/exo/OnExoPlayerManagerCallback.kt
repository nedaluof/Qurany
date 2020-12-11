package com.nedaluof.quranyplayer.exo

import com.nedaluof.quranyplayer.model.AudioData
import java.util.*

/**
 * To make an interaction between [ExoPlayerManager] & [MediaController]
 * and to return result from [ExoPlayerManager]
 * */
interface OnExoPlayerManagerCallback {

    fun getCurrentStreamPosition(): Long

    fun stop()

    fun play(audioData: AudioData)

    fun pause()

    fun seekTo(position: Long)

    fun setCallback(callback: OnAudioStateCallback)

    /**
     * This class gives the information about current audio
     * (position, the state of completion, when it`s changed, ...)
     * */
    interface OnAudioStateCallback {

        fun onCompletion()

        fun onPlaybackStatusChanged(state: Int)

        fun setCurrentPosition(position: Long, duration: Long)

        fun getCurrentAudio(): AudioData?

        fun getCurrentAudioList(): ArrayList<AudioData>?

        fun shuffle(isShuffle: Boolean)

        fun repeat(isRepeat: Boolean)

        fun repeatAll(isRepeatAll: Boolean)

    }

}
