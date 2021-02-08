package com.nedaluof.qurany.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nedaluof.qurany.data.model.Sura
import com.nedaluof.qurany.databinding.ItemSuraBinding

/**
 * Created by nedaluof on 12/17/2020.
 */

class SurasAdapter : RecyclerView.Adapter<SurasAdapter.SurasVH>() {

    // coming suras data list
    private val suras = ArrayList<Sura>()

    //listener must be initialized out the adapter in the client side
    lateinit var listener: SurasAdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurasVH {
        val binding = ItemSuraBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        )
        return SurasVH(binding)
    }

    override fun onBindViewHolder(holder: SurasVH, position: Int) {
        holder.bind(suras[position])
    }

    override fun getItemCount(): Int = suras.size

    fun addData(surasList: List<Sura>) {
        suras.addAll(surasList)
        notifyDataSetChanged()
    }


    inner class SurasVH(private val binding: ItemSuraBinding) : RecyclerView.ViewHolder(binding
            .root) {
        fun bind(suraData: Sura) {
            binding.run {
                sura = suraData
                callback = listener
                executePendingBindings()
            }
        }
    }

    interface SurasAdapterListener {
        fun onClickPlaySura(sura: Sura)
        fun onClickDownloadSura(sura: Sura)
    }

}
