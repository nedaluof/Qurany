package com.nedaluof.quranyplayer.media

import com.nedaluof.quranyplayer.exo.OnExoPlayerManagerCallback
import com.nedaluof.quranyplayer.model.AudioData
import com.nedaluof.quranyplayer.playlist.PlaylistManager
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MediaAdapterTest {

    lateinit var mMediaAdapter: MediaAdapter
    lateinit var playlistManager: PlaylistManager
    private val mListener = mock(PlaylistManager.OnAudioUpdateListener::class.java)
    private val onExoPlayerManagerCallback = mock(OnExoPlayerManagerCallback::class.java)
    private val mediaControllerCallback = mock(OnMediaAdapterCallback::class.java)
    private val song = mock(AudioData::class.java)
    private val songList = arrayListOf<AudioData>(song)


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mMediaAdapter = MediaAdapter(onExoPlayerManagerCallback, mediaControllerCallback)
        playlistManager = PlaylistManager(mListener)
    }

    @Test
    fun testAddSongToPlaylist() {
        val song = mock(AudioData::class.java)
        playlistManager.addToPlaylist(song)

        val result = playlistManager.getCurrentAudioList().contains(song)
        assertTrue(
            "Received result ${playlistManager.getCurrentAudioList().contains(song)}" +
                    " & mocked [true] must be matches on each other!", result
        )
        assertEquals(1, playlistManager.getCurrentAudioList().size)
    }


    @Test
    fun testAddSongListToPlaylist() {
        playlistManager.addToPlaylist(songList)

        val result = playlistManager.getCurrentAudioList().containsAll(songList)
        assertTrue(
            "Received result ${playlistManager.getCurrentAudioList().containsAll(songList)}" +
                    " & mocked [true] must be matches on each other!", result
        )
        assertEquals(1, playlistManager.getCurrentAudioList().size)
    }


    @Test
    fun testPlaySongs(){
        mMediaAdapter.play(songList , song)
        val currentSongList = mMediaAdapter.getCurrentAudioList()
        assertNotNull(currentSongList)
        assertTrue(currentSongList?.size != 0)
    }

}