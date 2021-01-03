package com.nedaluof.qurany.ui.component

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nedaluof.qurany.data.model.Sura
import com.nedaluof.qurany.databinding.ItemSuraBinding

/**
 * Created by nedaluof on 12/17/2020.
 */

class SurasAdapter : RecyclerView.Adapter<SurasAdapter.SurasVH>() {

  private val suras = ArrayList<Sura>()
  lateinit var clickListener: SurasAdapterListener

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurasVH {
    val binding = ItemSuraBinding.inflate(
      LayoutInflater.from(parent.context), parent, false
    )
    return SurasVH(binding)
  }

  override fun onBindViewHolder(holder: SurasVH, position: Int) {
    val sura = suras[position]
    with(holder.binding) {
      tvSuraName.text = sura.suraName
      tvSuraRewaya.text = sura.rewaya
      tvSuraNumber.text = sura.id.toString()
      imgPlaySura.setOnClickListener { clickListener.onClickPlaySura(sura, suras) }
      imgDownloadSura.setOnClickListener { clickListener.onClickDownloadSura(sura) }
    }
  }

  override fun getItemCount(): Int = suras.size

  interface SurasAdapterListener {
    fun onClickPlaySura(sura: Sura, surasList: ArrayList<Sura>)
    fun onClickDownloadSura(sura: Sura)
  }

  fun addData(surasList: List<Sura>) {
    suras.addAll(surasList)
    notifyDataSetChanged()
  }

  class SurasVH(val binding: ItemSuraBinding) : RecyclerView.ViewHolder(binding.root)
}
