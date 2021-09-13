package com.nedaluof.qurany.ui.suras

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nedaluof.qurany.data.model.Sura
import com.nedaluof.qurany.databinding.ItemSuraBinding
import com.nedaluof.qurany.ui.base.BaseRecyclerAdapter

/**
 * Created by nedaluof on 12/17/2020.
 */

class SurasAdapter(
    val onPlaySuraClicked: (Sura) -> Unit,
    val onDownloadSuraClicked: (Sura) -> Unit,
) : BaseRecyclerAdapter<Sura>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SurasVH(
        ItemSuraBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )


    inner class SurasVH(
        private val binding: ItemSuraBinding,
    ) : BaseViewHolder(binding) {
        override fun onBind(position: Int) {
            val suraData = getItem(position)!!
            binding.run {
                sura = suraData
                executePendingBindings()
                playSuraBtn.setOnClickListener { onPlaySuraClicked(suraData) }
                downloadSuraBtn.setOnClickListener { onDownloadSuraClicked(suraData) }
            }
        }
    }
}
