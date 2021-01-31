package com.nedaluof.qurany.ui.baseadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.databinding.ItemReciterBinding
import java.util.*

/**
 * Created by nedaluof on 1/26/2021.
 */
class TestAdapter : BaseAdapter<Reciter, TestAdapter.TestVH>() {


    class TestVH(val b: ItemReciterBinding) : BaseViewHolder(b.root)

    override fun onCreateBaseViewHolder(parent: ViewGroup, viewType: Int): TestVH {
        return TestVH(ItemReciterBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindBaseViewHolder(holder: TestVH, position: Int) {
        holder.b.run {
            val reciter = getItem(position)
            tvReciterSuraCount.text = reciter.count
            tvReciterRewaya.text = "${UUID.randomUUID()}"
            tvReciterName.text = reciter.name
        }
    }
}