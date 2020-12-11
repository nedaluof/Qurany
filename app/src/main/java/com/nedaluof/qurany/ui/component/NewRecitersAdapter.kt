package com.nedaluof.qurany.ui.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.databinding.ItemReciterBinding

/**
 * Created by nedaluof on 12/11/2020.
 */
class NewRecitersAdapter : RecyclerView.Adapter<NewRecitersAdapter.RecitersVH>() {

    private val recitersData = ArrayList<Reciter>()
    lateinit var listener: ReciterAdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecitersVH {
        val binding = ItemReciterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecitersVH(binding)
    }

    override fun onBindViewHolder(holder: RecitersVH, position: Int) {
        val reciter = recitersData[position]
        with(holder.binding) {
            tvReciterName.text = reciter.name
            tvReciterRewaya.text = reciter.rewaya
            tvReciterSuraCount.text = reciter.count
            reciterDataLayout.setOnClickListener { listener.onReciterClicked(reciter) }

            if (reciter.inMyReciters) {
                imgAddFavorite.visibility = View.GONE
            } else {
                imgAddFavorite.visibility = View.VISIBLE
            }

            imgAddFavorite.setOnClickListener {
                listener.onAddToFavoriteClicked(reciter)
                //need good solution
                imgAddFavorite.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = recitersData.size

    fun addReciters(reciters: ArrayList<Reciter>) {
        recitersData.clear()
        recitersData.addAll(reciters)
        notifyDataSetChanged()
    }

    interface ReciterAdapterListener {
        fun onReciterClicked(reciter: Reciter)
        fun onAddToFavoriteClicked(reciter: Reciter)
    }


    class RecitersVH(val binding: ItemReciterBinding) : RecyclerView.ViewHolder(binding.root)
}