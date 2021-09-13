package com.nedaluof.qurany.ui.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by NedaluOf on 9/12/2021.
 */
abstract class BaseRecyclerAdapter<T> :
    ListAdapter<T, BaseRecyclerAdapter.BaseViewHolder>(modelComparator<T>()) {

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    abstract class BaseViewHolder(
        binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        abstract fun onBind(position: Int)
    }


    companion object {
        private fun <T> modelComparator() = object : DiffUtil.ItemCallback<T>() {

            override fun areItemsTheSame(oldItem: T, newItem: T) =
                oldItem.hashCode() == newItem.hashCode()

            override fun areContentsTheSame(oldItem: T, newItem: T) =
                oldItem.toString() == newItem.toString()
        }
    }
}