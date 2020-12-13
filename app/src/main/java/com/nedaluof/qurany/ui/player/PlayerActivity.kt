package com.nedaluof.qurany.ui.player

import com.nedaluof.quranyplayer.BasePlayerActivity


class PlayerActivity : BasePlayerActivity() /*: BasePlayerActivity() {

    val Alfatiha = Sura(
            22,
            "ggg",
            "asem",
            "http://server12.mp3quran.net/maher/001.mp3"
    )

    private var mSong: Song? = null
    private var mSongList: MutableList<AudioData>? = null

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.extras?.apply {
            if (containsKey(SONG_LIST_KEY)) {
                mSongList = getParcelableArrayList(SONG_LIST_KEY)
            }

            if (containsKey(AudioData::class.java.name)) {
                mSong = getParcelable<AudioData>(AudioData::class.java.name) as Song
                mSong?.let {
                    mSongList?.let { it1 -> play(it1, it) }
                    loadInitialData(it)
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.nedaluof.qurany.R.layout.activity_player)

        //onNewIntent(intent)
        play(mutableListOf(Alfatiha) , Alfatiha)
        //play(Alfatiha)
        with(playerViewModel) {

            audioDurationData.observe(this@PlayerActivity) {
                song_player_progress_seek_bar.max = it
            }

            audioPositionTextData.observe(this@PlayerActivity) { t -> song_player_passed_time_text_view.text = t }

            audioPositionData.observe(this@PlayerActivity) {
                song_player_progress_seek_bar.progress = it
            }

            isRepeatData.observe(this@PlayerActivity) {
                song_player_repeat_image_view.setImageResource(
                        if (it) R.drawable.ic_repeat_one_color_primary_vector
                        else R.drawable.ic_repeat_one_black_vector
                )
            }

            isShuffleData.observe(this@PlayerActivity) {
                song_player_shuffle_image_view.setImageResource(
                        if (it) R.drawable.ic_shuffle_color_primary_vector
                        else R.drawable.ic_shuffle_black_vector
                )
            }

            isPlayData.observe(this@PlayerActivity) {
                song_player_toggle_image_view.setImageResource(if (it) R.drawable.ic_pause_vector else com.nedaluof.qurany.R.drawable.ic_play_vector)
            }

            playerData.observe(this@PlayerActivity) {
                loadInitialData(it)
            }


            song_player_skip_next_image_view.setOnClickListener {
                next()
            }

            song_player_skip_back_image_view.setOnClickListener {
                previous()
            }

            song_player_toggle_image_view.setOnClickListener {
                toggle()
            }

            song_player_shuffle_image_view.setOnClickListener {
                shuffle()
            }

            song_player_repeat_image_view.setOnClickListener {
                repeat()
            }

            song_player_progress_seek_bar.setOnSeekBarChangeListener(object :
                    SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    Log.i(TAG, "onProgressChanged: p0: $p0 p1: $p1, p2: $p2")
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                    Log.i(TAG, "onStartTrackingTouch: p0: $p0")
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                    Log.i(TAG, "onStopTrackingTouch: p0: $p0")
                    seekTo(song_player_progress_seek_bar.progress.toLong())
                }

            })
        }

        song_player_container.setOnTouchListener(object :
                OnSwipeTouchListener(this@PlayerActivity) {
            override fun onSwipeRight() {
                if (mSongList?.size ?: 0 > 1) previous()

            }

            override fun onSwipeLeft() {
                if (mSongList?.size ?: 0 > 1) next()
            }
        })
    }

    private fun loadInitialData(audioData: AudioData) {
        song_player_title_text_view.text = audioData.title
        song_player_singer_name_text_view.text = audioData.artist
        //song_player_total_time_text_view.text = formatTimeInMillisToString(audioData.length?.toLong() ?: 0L)
    }

    companion object {

        private val TAG = PlayerActivity::class.java.name
        const val SONG_LIST_KEY = "SONG_LIST_KEY"

        fun start(context: Context, song: Song, songList: ArrayList<Song>) {
            val intent = Intent(context, PlayerActivity::class.java).apply {
                putExtra(AudioData::class.java.name, song)
                putExtra(SONG_LIST_KEY, songList)
            }
            context.startActivity(intent)
        }
    }
}
**/