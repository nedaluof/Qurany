package com.nedaluof.quranyplayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nedaluof.quranyplayer.model.AudioData
import com.nedaluof.quranyplayer.util.formatTimeInMillisToString


class PlayerViewModel : ViewModel() {

    private val _playerData = MutableLiveData<AudioData>()
    val playerData: LiveData<AudioData> = _playerData
    private val _isVisibleData = MutableLiveData<Boolean>()
    val isVisibleData: LiveData<Boolean> = _isVisibleData
    private val _isBufferingData = MutableLiveData<Boolean>()
    val isBufferingData: LiveData<Boolean> = _isBufferingData
    private val _isPlayData = MutableLiveData<Boolean>()
    val isPlayData: LiveData<Boolean> = _isPlayData
    private val _playingPercentData = MutableLiveData<Int>()
    val playingPercentData: LiveData<Int> = _playingPercentData
    private val _audioDurationTextData = MutableLiveData<String>()
    val audioDurationTextData: LiveData<String> = _audioDurationTextData
    private val _audioPositionTextData = MutableLiveData<String>()
    val audioPositionTextData: LiveData<String> = _audioPositionTextData
    private val _audioDurationData = MutableLiveData<Int>()
    val audioDurationData: LiveData<Int> = _audioDurationData
    private val _audioPositionData = MutableLiveData<Int>()
    val audioPositionData: LiveData<Int> = _audioPositionData
    private val _isShuffleData = MutableLiveData<Boolean>()
    val isShuffleData: LiveData<Boolean> = _isShuffleData
    private val _isRepeatAllData = MutableLiveData<Boolean>()
    val isRepeatAllData: LiveData<Boolean> = _isRepeatAllData
    private val _isRepeatData = MutableLiveData<Boolean>()
    val isRepeatData: LiveData<Boolean> = _isRepeatData

    val audioData: AudioData?
        get() = _playerData.value

    init {
        _isPlayData.value = false
        _isRepeatData.value = false
        _isVisibleData.value = false
    }

    fun updateAudio(audioData: AudioData) {
        _playerData.value = audioData
    }

    fun setData(audioData: AudioData?) {
        if (audioData == _playerData.value) return
        this._playerData.value = audioData
        this._isRepeatData.value = false
        _audioPositionTextData.value = formatTimeInMillisToString(0)
        _audioPositionData.value = 0
        _audioDurationTextData.value = formatTimeInMillisToString(0)
        _audioDurationData.value = 0
    }

    fun shuffle() {
        _isShuffleData.value = _isShuffleData.value != true
    }

    fun repeatAll() {
        _isRepeatAllData.value = _isRepeatAllData.value != true
    }

    fun repeat() {
        _isRepeatData.value = _isRepeatData.value != true
    }

    fun setVisibility(isVisible: Boolean) {
        this._isVisibleData.value = isVisible
    }

    fun setBuffering(isBuffering: Boolean) {
        this._isBufferingData.value = isBuffering
    }

    fun setPlayStatus(playStatus: Boolean) {
        _isPlayData.value = playStatus
    }

    fun seekTo(position: Long) {
        _audioPositionTextData.value = formatTimeInMillisToString(position)
        _audioPositionData.value = position.toInt()
    }

    fun stop() {
        setPlayStatus(false)
        _audioPositionData.value = 0
        _audioPositionTextData.value = formatTimeInMillisToString(_audioPositionData.value?.toLong()
                ?: 0)
        _isVisibleData.value = false
    }

    fun setPlayingPercent(playingPercent: Int) {
        if (this._playingPercentData.value == 100) return
        this._playingPercentData.value = playingPercent
    }

    fun setChangePosition(currentPosition: Long, duration: Long) {
        if (currentPosition > duration) return
        _audioPositionTextData.value = formatTimeInMillisToString(currentPosition)
        _audioPositionData.value = currentPosition.toInt()

        val durationText = formatTimeInMillisToString(duration)
        if (!_audioDurationTextData.value.equals(durationText)) {
            _audioDurationTextData.value = durationText
            _audioDurationData.value = duration.toInt()
        }
    }


    companion object {

        private val TAG = PlayerViewModel::class.java.name
        private var instance: PlayerViewModel? = null

        @Synchronized
        fun getPlayerViewModelInstance(): PlayerViewModel {
            if (instance == null) {
                instance = PlayerViewModel()
            }
            return instance as PlayerViewModel
        }
    }

}