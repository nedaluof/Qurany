package com.nedaluof.quranyplayer.service

import com.nedaluof.quranyplayer.model.AudioData

/**
 * To make an interaction between [PlayerService] & [BasePlayerActivity]
 * */
interface OnPlayerServiceCallback {

    fun updateAudioData(audioData: AudioData)

    fun updateAudioProgress(duration: Long, position: Long)

    fun setBufferingData(isBuffering: Boolean)

    fun setVisibilityData(isVisibility: Boolean)

    fun setPlayStatus(isPlay: Boolean)

    fun stopService()
}