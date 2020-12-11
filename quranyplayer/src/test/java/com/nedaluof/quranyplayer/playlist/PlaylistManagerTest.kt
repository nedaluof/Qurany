package com.nedaluof.quranyplayer.playlist

import com.nedaluof.quranyplayer.model.AudioData
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class PlaylistManagerTest {


    lateinit var playlistManager: PlaylistManager
    private val mListener = mock(PlaylistManager.OnAudioUpdateListener::class.java)
    private var playlist = Playlist()
    private var mCurrentIndex: Int = 0

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        playlistManager = PlaylistManager(mListener)
    }

    @Test
    fun isRepeatTest() {
        playlist.isRepeat = true
        assertNotNull(playlist.isRepeat)
        assertTrue(playlist.isRepeat)
    }

    @Test
    fun isRepeatAllTest() {
        playlist.isRepeatAll = true
        assertNotNull(playlist.isRepeatAll)
        assertTrue(playlist.isRepeatAll)
    }


    @Test
    fun hasNext_firstCallTest() {
        val hasNext = playlistManager.hasNext()
        assertNotNull(hasNext)
        assertFalse(hasNext)
    }

    @Test
    fun hasNext_whenHasOneSongTest() {
        val aSong = mock(AudioData::class.java)
        playlistManager.addToPlaylist(aSong)

        val hasNext = playlistManager.hasNext()
        assertNotNull(hasNext)
        assertFalse(hasNext)
    }



    @Test
    fun getCurrentSongList() {
        val song = Mockito.mock(AudioData::class.java)
        playlistManager.addToPlaylist(song)

        assertEquals(1, playlistManager.getCurrentAudioList().size)
        val result = playlistManager.getCurrentAudioList().contains(song)
        assertTrue(
            "Received result ${playlistManager.getCurrentAudioList().contains(song)}" +
                    " & mocked [true] must be matches on each other!", result
        )
    }
}