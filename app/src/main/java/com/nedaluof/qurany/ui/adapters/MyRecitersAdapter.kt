package com.nedaluof.qurany.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.nedaluof.qurany.R
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.databinding.ItemMyReciterBinding


/**
 * Created by nedaluof on 12/12/2020.
 */
class MyRecitersAdapter
    : RecyclerView.Adapter<MyRecitersAdapter.MyRecitersVH>() {
    private val recitersData = ArrayList<Reciter>()
    lateinit var listener: MyRecitersAdapterListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecitersVH {
        val binding = ItemMyReciterBinding.inflate(LayoutInflater.from(parent.context), parent,
                false)
        return MyRecitersVH(binding)
    }

    override fun onBindViewHolder(holder: MyRecitersVH, position: Int) {
        holder.apply {
            itemView.startAnimation(AnimationUtils.loadAnimation(
                    itemView.context, R.anim.item_scale_animation
            ))
            bind(recitersData[position])
        }
    }

    override fun getItemCount(): Int = recitersData.size

    fun addReciters(reciters: List<Reciter>) {
        recitersData.apply {
            clear()
            addAll(reciters)
        }
        notifyDataSetChanged()
    }

    inner class MyRecitersVH(
            val binding: ItemMyReciterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reciterData: Reciter) {
            binding.run {
                reciter = reciterData
                callback = listener
            }
        }
    }

    interface MyRecitersAdapterListener {
        fun onReciterClicked(reciter: Reciter)
        fun onDeleteFromMyReciter(view: View, reciter: Reciter)
    }

}
