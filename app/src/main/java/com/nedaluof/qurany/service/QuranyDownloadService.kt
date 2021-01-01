package com.nedaluof.qurany.service

import android.app.DownloadManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.IBinder
import android.util.Log
import com.nedaluof.qurany.R
import com.nedaluof.qurany.data.model.Sura
import com.nedaluof.qurany.util.*
import com.nedaluof.qurany.util.Utility.checkIfSuraExist


/**
 * Created by nedaluof on 12/29/2020.
 */
class QuranyDownloadService : Service() {

    //unique id for the being sura downloaded
    var downloadId: Long = 0
    lateinit var sura: Sura
    lateinit var subPath: String
    override fun onBind(intent: Intent?): IBinder? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sura = intent?.getParcelableExtra("sura")!!
        registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        startDownload()
        return START_NOT_STICKY
    }

    private fun startDownload() {
        if (this::sura.isInitialized) {
            subPath = "/Qurany/${sura.reciterName}/${SuraUtil.getSuraName(sura.id)}.mp3"
            if (!this.checkIfSuraExist(subPath)) {
                if (NetworkUtil.isNetworkOk(this)) {
                    toastySuccess(R.string.alrt_download_start_title)
                    val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                    val request = DownloadManager.Request(Uri.parse(sura.suraUrl))
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or
                            DownloadManager.Request.NETWORK_MOBILE)
                            .setAllowedOverRoaming(false)
                            .setTitle(SuraUtil.getSuraName(sura.id))
                            .setDescription(SuraUtil.getSuraName(sura.id) + "| " + sura.reciterName)
                            .setDestinationInExternalFilesDir(this, null, subPath)
                    downloadId = downloadManager.enqueue(request)
                } else {
                    toastyError(R.string.alrt_no_internet_msg)
                    stopSelf()
                }
            } else {
                toastyInfo(R.string.alrt_sura_exist_message)
                stopSelf()
            }
        } else {
            Log.d(TAG, "startDownload: sura not initialized")
            stopSelf()
        }
    }

    private val onComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadId == id) {
                toastySuccess(R.string.alrt_download_completed_msg)
                scan()
            } else {
                Log.d(TAG, "onReceive: download id not match")
            }
        }
    }

    fun scan() {
        MediaScannerConnection.scanFile(
                this,
                arrayOf(this.getExternalFilesDir(null).toString() + subPath), arrayOf("audio/mp3")) { path, uri ->
            Log.i(TAG, "Scanned $path:")
            Log.i(TAG, "-> uri=$uri")
        }
    }

    override fun onDestroy() {
        unregisterReceiver(onComplete)
        super.onDestroy()
    }

    companion object {
        private val TAG = QuranyDownloadService::class.java.name
    }
}