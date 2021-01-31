package com.nedaluof.qurany.data.model

import android.os.Parcelable
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.nedaluof.qurany.R
import kotlinx.parcelize.Parcelize

/**
 * Created by nedaluof on 12/2/2020.
 */
@Parcelize
data class Sura(
        var id: Int = 0,
        var suraName: String = "",
        var rewaya: String = "",
        var suraUrl: String = "",
        var reciterName: String = "",
        var playerTitle: String = "",
        var suraSubPath: String = "",
        var playingType: Int = 0,
) : Parcelable {
    companion object {
        // Data Binding for text view for sura number
        @JvmStatic
        @BindingAdapter("suraNumber")
        fun suraNumber(textView: TextView, number: Int) {
            val context = textView.context
            val suraNumberLabel = context.getString(R.string.sura_number)
            val suraNum = "$suraNumberLabel $number"
            textView.text = suraNum
        }
    }
}
