package com.nedaluof.quranyplayer.playlist

import com.nedaluof.quranyplayer.model.AudioData

class Playlist {

    private var list: MutableList<AudioData> = ArrayList()
    private var shuffleList: MutableList<AudioData> = ArrayList()
    var isShuffle = false
    var isRepeat = false
    var isRepeatAll = false

    fun getShuffleOrNormalList(): MutableList<AudioData> {
        return if (isShuffle) shuffleList else list
    }

    fun getCurrentPlaylistSize(): Int = getShuffleOrNormalList().size

    fun setList(list: MutableList<AudioData>): Playlist {
        clearList()
        this.list = list
        list.shuffle()
        this.shuffleList = ArrayList(list)
        return this
    }

    fun addItems(audioList: ArrayList<AudioData>) {
        this.list.addAll(audioList)
        audioList.shuffle()
        this.shuffleList.addAll(audioList)
    }

    fun addItem(audioData: AudioData) {
        this.list.add(audioData)
        this.shuffleList.add(audioData)
    }

    fun getItem(index: Int): AudioData? {
        if (index >= getCurrentPlaylistSize()) return null
        return getShuffleOrNormalList()[index]
    }

    private fun clearList() {
        this.list.clear()
        this.shuffleList.clear()
    }


    companion object {

        private val TAG = Playlist::class.java.name
    }
}
