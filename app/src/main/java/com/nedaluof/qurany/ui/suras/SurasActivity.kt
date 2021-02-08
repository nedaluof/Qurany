package com.nedaluof.qurany.ui.suras

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.nedaluof.qurany.R
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Sura
import com.nedaluof.qurany.databinding.ActivitySurasBinding
import com.nedaluof.qurany.service.QuranyDownloadService
import com.nedaluof.qurany.service.QuranyPlayerService
import com.nedaluof.qurany.ui.adapters.SurasAdapter
import com.nedaluof.qurany.util.isNetworkOk
import com.nedaluof.qurany.util.toastyError
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by nedaluof on 12/5/2020.
 */
@AndroidEntryPoint
class SurasActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySurasBinding
    private lateinit var surasAdapter: SurasAdapter
    private lateinit var reciterData: Reciter
    private var sheetBehavior: BottomSheetBehavior<*>? = null

    private val viewModel: SurasViewModel by viewModels()

    // Player
    private lateinit var exoPlayer: SimpleExoPlayer
    private var service: QuranyPlayerService? = null
    private val myIntent: Intent by lazy {
        Intent(this, QuranyPlayerService::class.java)
    }
    private var bound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        reciterData = intent?.getParcelableExtra(RECITER_KEY)!!
        initComponents()
        viewModel.loadSurasToUI(reciterData)

        binding.run {
            viewmodel = viewModel
            lifecycleOwner = this@SurasActivity
            executePendingBindings()
        }
        /* loading.observe(this@SurasActivity) { show ->
            if (!show) {
              binding.progress.visibility = View.GONE
            }
          }
        reciterName.observe(this@SurasActivity) { name ->
            binding.reciterNameTitleBar.text = name
          }
          reciterSuras.observe(this@SurasActivity) { reciterSuras ->
            surasAdapter.addData(reciterSuras)
          }*/


        bindService(myIntent, serviceConnection, Context.BIND_AUTO_CREATE)
        binding.playerBottomSheet.imgBtnClose.setOnClickListener {
            (sheetBehavior as BottomSheetBehavior<*>).state = BottomSheetBehavior.STATE_COLLAPSED
            Handler(Looper.myLooper()!!).postDelayed(
                    {
                        binding.playerBottomSheet.bottomSheet.visibility = View.GONE
                    },
                    750
            )
            stopService()
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            val binder = iBinder as QuranyPlayerService.PlayerBinder
            service = binder.playerService
            bound = true
            initializePlayer()
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            bound = false
        }
    }

    private fun initializePlayer() {
        if (bound) {
            exoPlayer = service?.getPlayerInstance()!!
            binding.playerBottomSheet.playerController.player = exoPlayer
            if (service?.playerIsRunning()!!) {
                Handler(Looper.getMainLooper()).postDelayed(
                        {
                            binding.playerBottomSheet.bottomSheet.visibility = View.VISIBLE
                            sheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
                            binding.playerBottomSheet.reciterSuraName.text = service?.getCurrentSuraRunning()
                                    ?.playerTitle
                        },
                        700
                )
            }
            playerListener()
        }
    }

    private fun initComponents() {
        sheetBehavior = BottomSheetBehavior.from<View>(binding.playerBottomSheet.bottomSheet)
        surasAdapter = SurasAdapter().apply {
            listener = object : SurasAdapter.SurasAdapterListener {
                override fun onClickPlaySura(sura: Sura) {
                    onClickPlay(sura)
                }

                override fun onClickDownloadSura(sura: Sura) {
                    downloadSura(sura)
                }
            }
        }
        binding.surasRecyclerView.apply {
            setHasFixedSize(true)
            /*adapter = ScaleInAnimationAdapter(surasAdapter).apply {
              setFirstOnly(false)
            }*/
            adapter = surasAdapter
        }
        binding.surasRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    sheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)
                } else if (dy < 0) {
                    sheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
        })
    }

    private fun playerListener() {
        exoPlayer.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    sheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
                    Handler(Looper.myLooper()!!).postDelayed(
                            {
                                binding.playerBottomSheet.bottomSheet.visibility = View.GONE
                            },
                            1500
                    )
                    // stopService()
                }
            }
        })
    }

    private fun onClickPlay(sura: Sura) {
        with(viewModel) {
            checkSuraExist(sura)
            isSuraExist.observe(this@SurasActivity) { exist ->
                if (exist) playLocally(sura) else playOnline(sura)
            }
        }
    }

    private fun playOnline(sura: Sura) {
        myIntent.apply {
            sura.playingType = PLAYING_ONLINE
            putExtra(QuranyPlayerService.SURA_KEY, sura)
            putExtra(QuranyPlayerService.RECITER_KEY, reciterData)
        }
        if (this.isNetworkOk()) {
            if (!this::exoPlayer.isInitialized || !exoPlayer.isPlaying) {
                bindService(myIntent, serviceConnection, Context.BIND_AUTO_CREATE)
                binding.playerBottomSheet.bottomSheet.visibility = View.VISIBLE
                sheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
                binding.playerBottomSheet.reciterSuraName.text = sura.playerTitle // SuraUtil
                // this startForegroundService if SDK >= 26 or startService(myIntent) for SDK < 26
                Util.startForegroundService(this, myIntent)
                initializePlayer()
            } else {
                service?.stopSelf()
                unbindService(serviceConnection)
                bound = false
                bindService(myIntent, serviceConnection, Context.BIND_ADJUST_WITH_ACTIVITY)
                binding.playerBottomSheet.bottomSheet.visibility = View.VISIBLE
                sheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
                binding.playerBottomSheet.reciterSuraName.text = sura.playerTitle
                // this startForegroundService if SDK >= 26 or startService(myIntent) for SDK < 26
                Util.startForegroundService(this, myIntent)
                initializePlayer()
            }
        } else {
            toastyError(R.string.alrt_no_internet_msg)
        }
    }

    private fun playLocally(sura: Sura) {
        myIntent.apply {
            sura.playingType = PLAYING_LOCALLY
            putExtra(QuranyPlayerService.SURA_KEY, sura)
            putExtra(QuranyPlayerService.RECITER_KEY, reciterData)
        }
        if (!this::exoPlayer.isInitialized || !exoPlayer.isPlaying) {
            bindService(myIntent, serviceConnection, Context.BIND_AUTO_CREATE)
            binding.playerBottomSheet.bottomSheet.visibility = View.VISIBLE
            sheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
            binding.playerBottomSheet.reciterSuraName.text = sura.playerTitle // SuraUtil
            // this startForegroundService if SDK >= 26 or startService(myIntent) for SDK < 26
            Util.startForegroundService(this, myIntent)
        } else {
            service?.stopSelf()
            unbindService(serviceConnection)
            bound = false
            bindService(myIntent, serviceConnection, Context.BIND_ADJUST_WITH_ACTIVITY)
            binding.playerBottomSheet.bottomSheet.visibility = View.VISIBLE
            sheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
            binding.playerBottomSheet.reciterSuraName.text = sura.playerTitle
            // this startForegroundService if SDK >= 26 or startService(myIntent) for SDK < 26
            Util.startForegroundService(this, myIntent)
        }
    }

    fun downloadSura(sura: Sura) {
        startDownload(sura)
    }

    private fun startDownload(sura: Sura) {
        startService(/*download intent*/
                Intent(this, QuranyDownloadService::class.java)
                        .putExtra("sura", sura)
        )
    }

    private fun stopService() {
        service?.stop()
        unbindService(serviceConnection)
        bound = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (bound) {
            //Todo: if user close the Activity need efficient solution
            stopService()
        }
    }


    companion object {
        const val RECITER_KEY = "RECITER_KEY"
        const val PLAYING_ONLINE = 1
        const val PLAYING_LOCALLY = 2
    }
}
