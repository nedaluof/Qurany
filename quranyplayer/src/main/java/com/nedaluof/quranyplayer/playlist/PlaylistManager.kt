package com.nedaluof.quranyplayer.playlist
import com.nedaluof.quranyplayer.model.AudioData
import java.util.*
import kotlin.math.max

/**
 * This class is used to manage the playlist
 * (normal list, onShuffle list, repetition, ...)
 **/
class PlaylistManager(private val mListener: OnAudioUpdateListener) {

    private var playlist: Playlist? = null
    private var mCurrentIndex: Int = 0


    init {
        playlist = Playlist()
        mCurrentIndex = 0
    }

    fun getCurrentAudio(): AudioData? {
        return playlist?.getItem(mCurrentIndex)
    }

    fun getCurrentAudioList(): ArrayList<AudioData> {
        return playlist?.getShuffleOrNormalList() as java.util.ArrayList<AudioData>
    }

    private fun setCurrentPlaylistIndex(index: Int) {
        if (index >= 0 && index < playlist?.getShuffleOrNormalList()?.size ?: 0) {
            mCurrentIndex = index
            //mListener.onUpdatePlaylistIndex(mCurrentIndex)
        }
        updateAudio()
    }

    fun hasNext(): Boolean = mCurrentIndex < (playlist?.getCurrentPlaylistSize()?.minus(1) ?: 0)

    fun skipPosition(amount: Int): Boolean {
        var index = mCurrentIndex + amount
        val currentPlayListSize = playlist?.getCurrentPlaylistSize() ?: 0

        if (currentPlayListSize == 0 || index >= currentPlayListSize) return false
        if (index < 0) {
            // skip backwards before the first audio will keep you on the first audio
            index = if (isRepeatAll()) currentPlayListSize else 0
        } else {
            // skip forwards when in last audio will cycle back to start of the playlist
            if (currentPlayListSize != 0) index %= currentPlayListSize
        }
        return if (mCurrentIndex == index) {
            setCurrentPlaylistIndex(mCurrentIndex)
            false
        } else {
            mCurrentIndex = index
            setCurrentPlaylistIndex(mCurrentIndex)
            true
        }
    }

    fun setCurrentPlaylist(newPlaylist: MutableList<AudioData>, audioData: AudioData? = null) {
        playlist = Playlist().setList(newPlaylist)
        var index = 0
        audioData.let {
            index = getAudioIndexOnPlaylist(playlist?.getShuffleOrNormalList() as Iterable<AudioData>, it!!)
        }
        mCurrentIndex = max(index, 0)
        setCurrentPlaylistIndex(index)
    }


    private fun updateAudio() {
        val currentAudio = getCurrentAudio()
        if (currentAudio == null) {
            mListener.onAudioRetrieveError()
            return
        }
        mListener.onAudioChanged(currentAudio)
    }

    fun addToPlaylist(audioList: ArrayList<AudioData>) {
        playlist?.addItems(audioList)
    }

    fun addToPlaylist(audioData: AudioData) {
        playlist?.addItem(audioData)
    }

    fun setRepeat(isRepeat: Boolean) {
        playlist?.isRepeat = isRepeat
    }

    fun isRepeat(): Boolean {
        return playlist?.isRepeat ?: false
    }

    fun isRepeatAll(): Boolean = playlist?.isRepeatAll ?: false

    fun repeat(): Boolean {
        if (playlist?.isRepeat == true) {
            setCurrentPlaylistIndex(mCurrentIndex)
            return true
        }
        return false
    }

    fun setShuffle(isShuffle: Boolean) {
        playlist?.isShuffle = isShuffle
    }

    fun setRepeatAll(isRepeatAll: Boolean) {
        playlist?.isRepeatAll = isRepeatAll
    }

    private fun getAudioIndexOnPlaylist(list: Iterable<AudioData>, audioData: AudioData): Int {
        for ((index, item) in list.withIndex()) {
            if (audioData.audioId == item.audioId) {
                return index
            }
        }
        return -1
    }

    fun getRandomIndex(list: List<AudioData>) = Random().nextInt(list.size)


    /**
     * Determine if two playlists contain identical audio id's in order.
     *
     * @param list1 containing [AudioData]'s
     * @param list2 containing [AudioData]'s
     * @return boolean indicating whether the playlist's match
     */
    fun equals(list1: List<AudioData>?, list2: List<AudioData>?): Boolean {
        if (list1 === list2) {
            return true
        }
        if (list1 == null || list2 == null) {
            return false
        }
        if (list1.size != list2.size) {
            return false
        }
        for (i in list1.indices) {
            if (list1[i].audioId != list2[i].audioId) {
                return false
            }
        }
        return true
    }

    /**
     * To make an interaction between [PlaylistManager] & [MediaController]
     *
     * to update the state of playing Audio
     * */
    interface OnAudioUpdateListener {

        fun onAudioChanged(audioData: AudioData)

        fun onAudioRetrieveError()

    }


}
