package com.nedaluof.qurany.ui.suras

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.listener.*
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.databinding.ActivitySurasBinding
import com.nedaluof.qurany.ui.component.SurasAdapter
import com.nedaluof.qurany.ui.player.PlayerActivity
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

/**
 * Created by nedaluof on 12/5/2020.
 */
class SurasActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurasBinding
    private lateinit var surasAdapter: SurasAdapter
    private lateinit var reciterData: Reciter
    // private var sheetBehavior: BottomSheetBehavior<*>? = null

    //audio quranyplayer
    /*private var player: SimpleExoPlayer? = null
    private lateinit var mediaSource: MediaSource
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0
    private var suraId = 0
    private var downloadId: Long = 0*/


    private val viewModel: SurasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //sheetBehavior = BottomSheetBehavior.from<View>(binding.playerBottomSheet.bottomSheet)

        reciterData = intent.getParcelableExtra("reciterData")!!

        surasAdapter = SurasAdapter().apply {
            clickListener = SurasAdapter.SurasAdapterListener { sura, suras ->
                startActivity(Intent(this@SurasActivity, PlayerActivity::class.java).apply {
                    putExtra("reciterSura", sura)
                    putExtra("reciterSuras", suras)
                })
            }
        }
        binding.surasRecyclerView.apply {
            setHasFixedSize(true)
            adapter = ScaleInAnimationAdapter(surasAdapter).apply {
                setFirstOnly(false)
            }
        }

        with(viewModel) {
            loadSurasToUI(reciterData)
            reciterName.observe(this@SurasActivity) { name ->
                binding.reciterNameTitleBar.text = name
            }
            reciterSuras.observe(this@SurasActivity) { reciterSuras ->
                surasAdapter.addData(reciterSuras)
            }
        }

        /* binding.playerBottomSheet.imgBtnClose.setOnClickListener {
             player?.release()
             (sheetBehavior as BottomSheetBehavior<*>).state = BottomSheetBehavior.STATE_COLLAPSED
             Handler(Looper.myLooper()!!).postDelayed({ binding.playerBottomSheet.bottomSheet.visibility = View.GONE }, 750)
         }*/

        //Utility.createMainFolder()
        //registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun initRecyclerView() {
        /* binding.reciterSurasRecyclerView.setHasFixedSize(true)
         surasAdapter = SurasAdapter().apply {
             clickListener = SurasAdapter.SurasAdapterListener { sura, suras ->
                 startActivity(Intent(this@SurasActivity, PlayerActivity::class.java).apply {
                     putExtra("reciterSura", sura)
                     putExtra("reciterSuras", suras)
                 })
             }
         }
         binding.reciterSurasRecyclerView.adapter = surasAdapter*/
        /*binding.reciterSurasRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    sheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)
                } else if (dy < 0) {
                    sheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
        })*/
    }

    /*private val onComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadId == id) {
                Alerter.create(this@SurasActivity)
                        .setTitle(R.string.alrt_download_completed_title)
                        .setText(R.string.alrt_download_completed_msg)
                        .setBackgroundColorRes(R.color.green_200)
                        .enableSwipeToDismiss()
                        .show()
                Log.d(TAG, "onReceive: download id match and the download completed")
            } else {
                Log.d(TAG, "onReceive: download id not match")
            }
        }
    }*/

    /* override fun showProgress(show: Boolean) {
         if (show) {
             binding.proRecitersSura.visibility = View.VISIBLE
         } else {
             binding.proRecitersSura.visibility = View.GONE
         }
     }*/

    /*override fun setReciterName(reciterName: String?) {
        binding.reciterNameTitleBar.text = reciterName
    }*/

    /*override fun showReciterSuras(suraList: MutableList<Sura>?) {
        surasAdapter.addData(suraList!!)
    }*/

    /*override fun showError(message: String?) {
        // TODO: 2020 ar-en language in strings
        Toast.makeText(this, "Error :$message", Toast.LENGTH_SHORT).show()
    }*/

    /* override fun onClickPlay(suraId: Int) {
         if (NetworkUtild.isNetworkOk(this)) {
             val direct = File(Environment.getExternalStorageDirectory()
                     .toString() + "/Qurany/" + reciterData.name, SurasUtil.getSuraName(suraId) + ".mp3")
             if (!(player?.isPlaying!!)) {
                 if (!Utility.checkIfFileInPathExist(reciterData.name + "/" + SurasUtil.getSuraName(suraId) + ".mp3")) {
                     this.suraId = suraId
                     binding.playerBottomSheet.bottomSheet.visibility = View.VISIBLE
                     sheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
                     binding.playerBottomSheet.reciterSuraName.text = SurasUtil.getPlayerTitle(suraId, reciterData.name)
                     initializePlayer(suraId)
                     Log.d(TAG, "onClickPlay: current , listened online")
                 } else {
                     this.suraId = suraId
                     binding.playerBottomSheet.bottomSheet.visibility = View.VISIBLE
                     sheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
                     binding.playerBottomSheet.reciterSuraName.text = SurasUtil.getPlayerTitle(suraId, reciterData.name)
                     initializePlayerLocalSura(direct.path)
                     Log.d(TAG, "onClickPlay: current listened local")
                 }
             } else if (this.suraId != suraId) {
                 player?.release()
                 if (!Utility.checkIfFileInPathExist(reciterData.name + "/" + SurasUtil.getSuraName(suraId) + ".mp3")) {
                     this.suraId = suraId
                     binding.playerBottomSheet.bottomSheet.visibility = View.VISIBLE
                     sheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
                     binding.playerBottomSheet.reciterSuraName.text = SurasUtil.getPlayerTitle(suraId, reciterData.name)
                     initializePlayer(suraId)
                     Log.d(TAG, "onClickPlay: new sura id , listened online")
                 } else {
                     this.suraId = suraId
                     binding.playerBottomSheet.bottomSheet.visibility = View.VISIBLE
                     sheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
                     binding.playerBottomSheet.reciterSuraName.text = SurasUtil.getPlayerTitle(suraId, reciterData.name)
                     initializePlayerLocalSura(direct.path)
                     Log.d(TAG, "onClickPlay: new sura id , listened local")
                 }
             }
         } else {
             handleNoInternetIfFileExist(suraId)
         }
     }*/

    /*override fun onDownloadClick(suraId: Int) {
        Dexter.withContext(this@SurasActivity)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse) {
                        startDownload(suraId)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        if (response.isPermanentlyDenied) {
                            Utility.showSettingsDialog(this@SurasActivity)
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permissionRequest: PermissionRequest, permissionToken: PermissionToken) {
                        permissionToken.continuePermissionRequest()
                    }
                }).withErrorListener {
                    Toast.makeText(this, getString(R
                            .string.dexter_permission_error), Toast.LENGTH_SHORT).show()
                }
                .check()
    }*/


    /*private fun handleNoInternetIfFileExist(suraId: Int) {
        val direct = File(Environment.getExternalStorageDirectory()
                .toString() + "/Qurany/" + reciterData.name, SurasUtil.getSuraName(suraId) + ".mp3")
        if (Utility.checkIfFileInPathExist(reciterData.name + "/" + SurasUtil.getSuraName(suraId) + ".mp3")) {
            this.suraId = suraId
            binding.playerBottomSheet.bottomSheet.visibility = View.VISIBLE
            sheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
            binding.playerBottomSheet.reciterSuraName.text = SurasUtil.getPlayerTitle(suraId, reciterData.name)
            initializePlayerLocalSura(direct.path)
            Log.d(TAG, "onClickPlay: current listened local and no internet")
        } else {
            showNoInternetAlert()
        }
    }*/

    /*private fun initializePlayer(suraId: Int) {
        val server = reciterData.server + "/" + SurasUtil.getSuraIndex(suraId) + ".mp3"
        mediaSource = buildMediaSource(Uri.parse(server))
        player = SimpleExoPlayer.Builder(this).build()
        binding.playerBottomSheet.playerController.player = player
        player?.playWhenReady = playWhenReady
        player?.seekTo(currentWindow, playbackPosition)
        player?.prepare(mediaSource, false, false)
        player?.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    sheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
                    Handler(Looper.myLooper()!!).postDelayed({
                        binding.playerBottomSheet.bottomSheet.visibility = View.GONE
                        player?.release()
                    }, 1000)
                } else if (playWhenReady && playbackState == Player.STATE_READY) {
                    // media actually playing
                } else if (playWhenReady) {
                    // might be idle (plays after prepare()),
                    // buffering (plays when data available)
                    // or ended (plays when seek away from end)
                } else {
                    // quranyplayer paused in any state
                    sheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)
                }
            }
        })
    }*/

    /*private fun initializePlayerLocalSura(path: String) {
        mediaSource = buildMediaSource(Uri.parse(path))
        player = SimpleExoPlayer.Builder(this).build()
        binding.playerBottomSheet.playerController.player = player
        player?.playWhenReady = playWhenReady
        player?.seekTo(currentWindow, playbackPosition)
        player?.prepare(mediaSource, false, false)
        player?.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    sheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.playerBottomSheet.bottomSheet.visibility = View.GONE
                        player?.release()
                    }, 1000)
                }
            }
        })
    }*/

    /*private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(this, "Qurany_Player")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri)
    }*/

    /*private fun startDownload(suraId: Int) {
        if (NetworkUtild.isNetworkOk(this)) {
            if (!Utility.checkIfFileInPathExist(reciterData.name + "/" + SurasUtil.getSuraName(suraId) + ".mp3")) {
                Alerter.create(this)
                        .setTitle(R.string.alrt_download_start_title)
                        .enableProgress(true)
                        .setProgressColorRes(R.color.grey)
                        .setBackgroundColorRes(R.color.green_200)
                        .show()
                val uRl = reciterData.server + "/" + SurasUtil.getSuraIndex(suraId) + ".mp3"
                val downloadManager = this.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                val request = DownloadManager.Request(Uri.parse(uRl))
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or
                        DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle(getString(R.string.download_manager_title) + SurasUtil.getSuraName(suraId))
                        .setDescription(SurasUtil.getSuraName(suraId) + "| " + reciterData.name)
                request.setDestinationInExternalPublicDir("/Qurany/" + reciterData.name, SurasUtil.getSuraName(suraId) + ".mp3")
                downloadId = downloadManager.enqueue(request)
            } else {
                Alerter.create(this)
                        .setTitle(R.string.alrt_sura_exist_title)
                        .setText(R.string.alrt_sura_exist_message)
                        .setBackgroundColorRes(R.color.green_200)
                        .enableSwipeToDismiss()
                        .show()
            }
        } else {
            showNoInternetAlert()
        }
    }*/


    /*private fun showNoInternetAlert() {
        Alerter.create(this)
                .setTitle(R.string.alrt_no_internet_title)
                .setText(R.string.alrt_no_internet_msg)
                .enableSwipeToDismiss()
                .hideIcon()
                .setBackgroundColorRes(R.color.red)
                .show()
    }*/

    /*private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player?.playWhenReady!!
            playbackPosition = player?.currentPosition!!
            currentWindow = player?.currentWindowIndex!!
            player?.release()
            player = null
        }
    }*/


    /*override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer(suraId)
        }
    }*/

    /*override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer(suraId)
        }
    }*/

    /*override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }*/

    /*override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }*/

    /*override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(onComplete)
    }*/
}