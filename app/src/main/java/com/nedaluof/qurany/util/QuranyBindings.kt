package com.nedaluof.qurany.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Sura
import com.nedaluof.qurany.ui.component.MyRecitersAdapter
import com.nedaluof.qurany.ui.component.RecitersAdapter
import com.nedaluof.qurany.ui.component.SurasAdapter

class QuranyBindings {

    companion object {
        @BindingAdapter("reciters")
        @JvmStatic
        fun bindReciters(
                recycler: RecyclerView,
                list: List<Reciter>?,
        ) {
            val rAdapter: RecitersAdapter = recycler.adapter as RecitersAdapter
            if (!list.isNullOrEmpty())
                rAdapter.addReciters(list)
        }

        @BindingAdapter("myReciters")
        @JvmStatic
        fun bindMyReciters(
                recycler: RecyclerView,
                list: List<Reciter>?,
        ) {
            val rAdapter: MyRecitersAdapter = recycler.adapter as MyRecitersAdapter
            if (list != null) {
                rAdapter.addReciters(list)
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
    }
}