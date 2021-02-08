package com.nedaluof.qurany.ui.baseadapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

/**
 * Created by nedaluof on 2/3/2021.
 */
abstract class TestBaseAnimatedAdapter<D, T : RecyclerView.Adapter<*>, VH : BaseViewHolder>(
        adapter: T,
) : ScaleInAnimationAdapter(adapter) {

    private val itemsList = ArrayList<D>()

    fun setListOfData(data: ArrayList<D>) {
        this.itemsList.addAll(data)
    }

    override fun getItemCount() = itemsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return getViewHolder()
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        bindNow(holder, position)
    }

    abstract fun getViewHolder(): VH

    abstract fun bindNow(holder: RecyclerView.ViewHolder, position: Int)


}

open class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view)
