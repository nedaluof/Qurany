package com.nedaluof.qurany.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nedaluof.qurany.R
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Sura
import com.nedaluof.qurany.ui.adapters.MyRecitersAdapter
import com.nedaluof.qurany.ui.adapters.RecitersAdapter
import com.nedaluof.qurany.ui.adapters.SurasAdapter

object QuranyViewsBindings {

    @BindingAdapter("setReciters")
    @JvmStatic
    fun bindReciters(
        recycler: RecyclerView?,
        list: List<Reciter>?,
    ) {
        if (recycler != null) {
            val rAdapter = recycler.adapter as RecitersAdapter?
            if (!list.isNullOrEmpty() && rAdapter != null) {
                rAdapter.addReciters(list)
            }
        }
    }

    @BindingAdapter("myReciters")
    @JvmStatic
    fun bindMyReciters(
        recycler: RecyclerView?,
        list: List<Reciter>?,
    ) {
        if (recycler != null) {
            val rAdapter = recycler.adapter as MyRecitersAdapter?
            if (list != null && rAdapter != null) {
                rAdapter.addReciters(list)
            }
        }
    }

    @BindingAdapter("suras")
    @JvmStatic
    fun bindSuras(
        recycler: RecyclerView,
        list: List<Sura>?,
    ) {
        val surasAdapter: SurasAdapter = recycler.adapter as SurasAdapter
        if (list != null) {
            surasAdapter.addData(list)
        }
    }


    @JvmStatic
    @BindingAdapter("suraCount")
    fun suraCount(textView: TextView, count: String) {
        val context = textView.context
        val surasCountLabelOne = context.getString(R.string.sura_count_1)
        val surasCountLabelTwo = context.getString(R.string.sura_count_2)
        val surasCountLabelThree = context.getString(R.string.sura_count_3)
        val suraCount = count.toInt()
        var surasCount = ""
        surasCount = if (suraCount > 1) {
            "$surasCountLabelOne $count $surasCountLabelTwo"
        } else {
            surasCountLabelThree
        }
        textView.text = surasCount
    }
}
