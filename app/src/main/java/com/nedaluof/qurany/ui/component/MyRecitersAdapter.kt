package com.nedaluof.qurany.ui.component

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nedaluof.qurany.R
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.databinding.ItemReciterBinding

/**
 * Created by nedaluof on 12/12/2020.
 */
class MyRecitersAdapter(
  context: Context
) : RecyclerView.Adapter<MyRecitersAdapter.MyRecitersVH>() {
  private val recitersData = ArrayList<Reciter>()
  lateinit var listener: MyRecitersAdapterListener

  private val surasCountLabel = context.getString(R.string.sura_count)
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecitersVH {
    val binding = ItemReciterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MyRecitersVH(binding)
  }

  override fun onBindViewHolder(holder: MyRecitersVH, position: Int) {
    val reciter = recitersData[position]

    with(holder.binding) {
      tvReciterName.text = reciter.name
      tvReciterRewaya.text = reciter.rewaya
      val surasCount = "$surasCountLabel ${reciter.count}"
      tvReciterSuraCount.text = surasCount
      reciterDataLayout.setOnClickListener { listener.onReciterClicked(reciter) }
      imgFavorite.apply {
        setImageResource(R.drawable.ic_favorite_selected)
        setOnClickListener {
          listener.onDeleteFromMyReciter(reciter)
        }
      }
    }
  }

  override fun getItemCount(): Int = recitersData.size

  fun addReciters(reciters: ArrayList<Reciter>) {
    recitersData.apply {
      clear()
      addAll(reciters)
    }
    notifyDataSetChanged()
  }

  interface MyRecitersAdapterListener {
    fun onReciterClicked(reciter: Reciter)
    fun onDeleteFromMyReciter(reciter: Reciter)
  }

  class MyRecitersVH(val binding: ItemReciterBinding) : RecyclerView.ViewHolder(binding.root)
}
