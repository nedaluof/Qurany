package com.nedaluof.qurany.ui.component

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

/**
 * Created by nedaluof on 2/3/2021.
 */
abstract class TestBaseAnimatedAdapter<T : RecyclerView.Adapter<*>, VH : RecyclerView.ViewHolder>(
        adapter: T,
) : ScaleInAnimationAdapter(adapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        bindNow(holder, position)
    }

    abstract fun getViewHolder(): VH

    abstract fun bindNow(holder: RecyclerView.ViewHolder, position: Int)
}
