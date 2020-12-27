package com.nedaluof.qurany.util

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.nedaluof.qurany.R
import java.io.File
import java.util.*

/**
 * Created by nedaluof on 6/25/2020. {Java}
 * Created by nedaluof on 12/18/2020. {Kotlin}
 */
object Utility {

    /**
     * @return user device language
     */
    fun getLanguage() =
            if (Locale.getDefault().displayLanguage == "العربية") "_arabic" else "_english"


    /**
     * Create the main folder which will hold
     * downloaded Sura if not exist
     */
    fun createMainFolder() {
        val direct = File(Environment.getExternalStorageDirectory()
                .toString() + "/Qurany")
        if (!direct.exists()) {
            direct.mkdirs()
        }
    }

    /**
     * @param subPath contain Reciter Name and Sura Name
     * {Ahmad/AlFatiha.mp3}
     * @return true if exist , else not exist
     */
    fun checkIfFileInPathExist(subPath: String): Boolean {
        val direct = File(Environment.getExternalStorageDirectory()
                .toString() + "/Qurany/" + subPath)
        return direct.exists()
    }

    fun showSettingsDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.resources.getString(R.string.dialoge_permission_title))
        builder.setMessage(context.resources.getString(R.string.dialoge_permission_message))
        builder.setPositiveButton(context.resources.getString(R.string
                .dialoge_permission_gosettings)) { dialog, _ ->
            dialog.cancel()
            openSettings(context)
        }
        builder.setNegativeButton(context.resources.getString(R.string.dialoge_permission_cancel)) { dialog: DialogInterface, which: Int -> dialog.cancel() }
        builder.show()
    }

    /**
     *  [context] identify package to navigate the user to app settings
     */
    private fun openSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

    fun Context.getLogoAsBitmap(): Bitmap {
        val width = 200
        val height = 200
        val drawable = ContextCompat.getDrawable(this, R.drawable.ic_qurany)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable?.setBounds(0, 0, width, height)
        drawable?.draw(canvas)
        return bitmap
    }

}