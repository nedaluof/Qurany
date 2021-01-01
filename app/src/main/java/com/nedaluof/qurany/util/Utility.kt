package com.nedaluof.qurany.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
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


    fun Context.checkIfSuraExist(subPath: String) =
            File(this.getExternalFilesDir(null).toString()
                    + subPath).exists()

    fun Context.getSuraPath(subPath: String): String =
            File(this.getExternalFilesDir(null).toString()
                    + subPath).absolutePath


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
