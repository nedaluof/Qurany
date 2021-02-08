package com.nedaluof.qurany.ui.baseadapter

/**
 * Created by nedaluof on 1/26/2021.
 */

/*abstract class BaseAdapter<T, VH : BaseViewHolder> : RecyclerView.Adapter<VH>() {

    private val itemsList = ArrayList<T>()

    protected fun getItem(position: Int) = itemsList[position]

    private var listener: BaseOnClickListener<T>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return onCreateBaseViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (itemsList.size > -1) {
            if (listener != null) {
                onBindBaseViewHolder(holder, position)
                holder.itemView.setOnClickListener { listener?.onItemClick(itemsList[position]) }
            } else {
                throw IllegalStateException("You must set the listener")
            }
        } else {
            throw IllegalStateException("You must initialize the list of the data")
        }
    }


    abstract fun onCreateBaseViewHolder(parent: ViewGroup, viewType: Int): VH

    abstract fun onBindBaseViewHolder(holder: VH, position: Int)

    override fun getItemCount() = itemsList.size

    fun setBaseOnClickListener(listener: BaseOnClickListener<T>) {
        this.listener = listener
    }

    fun setListOfData(data: ArrayList<T>) {
        this.itemsList.addAll(data)
    }


}

open class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view)


fun interface BaseOnClickListener<T> {
    fun onItemClick(t: T)
}*/
