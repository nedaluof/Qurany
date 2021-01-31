package com.nedaluof.qurany.data.model

import android.os.Parcelable
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.nedaluof.qurany.R
import kotlinx.parcelize.Parcelize

/**
 * Created by nedaluof on 12/2/2020.
 */
@Parcelize
@Entity(tableName = "reciter")
data class Reciter(
        @SerializedName("id")
        var id: String? = null,
        @SerializedName("name")
        var name: String? = null,

        @SerializedName("Server")
        var server: String? = null,

        @SerializedName("rewaya")
        var rewaya: String? = null,

        @SerializedName("count")
        var count: String? = null,

        @SerializedName("letter")
        var letter: String? = null,

        @SerializedName("suras")
        var suras: String? = null,
        var inMyReciters: Boolean = false,
        @PrimaryKey(autoGenerate = true)
        var reciter_id: Int = 0,

        var isPlayingNow: Boolean = false,
) : Parcelable {

    companion object {
        // Data Binding for text view for sura count
        @JvmStatic
        @BindingAdapter("suraCount")
        fun suraCount(textView: TextView, count: String) {
            val context = textView.context
            val surasCountLabel = context.getString(R.string.sura_count)
            val surasCount = "$surasCountLabel $count"
            textView.text = surasCount
        }
    }

}
