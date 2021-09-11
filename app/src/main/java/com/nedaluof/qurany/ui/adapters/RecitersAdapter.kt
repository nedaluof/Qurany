package com.nedaluof.qurany.ui.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.databinding.ItemReciterBinding
import com.nedaluof.qurany.util.getLanguage

/**
 * Created by nedaluof on 12/11/2020.
 */
class RecitersAdapter : RecyclerView.Adapter<RecitersAdapter.RecitersVH>(), Filterable {

    lateinit var listener: ReciterAdapterListener
    private val recitersData = ArrayList<Reciter>()
    private val filteredList = ArrayList<Reciter>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RecitersVH(
            ItemReciterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: RecitersVH, position: Int) {
        holder.onBind(recitersData[position])
    }

    override fun getItemCount() = recitersData.size

    fun addReciters(reciters: List<Reciter>) {
        recitersData.apply {
            clear()
            addAll(reciters)
        }
        filteredList.apply {
            clear()
            addAll(reciters)
        }
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter = searchFilter

    private val searchFilter = object : Filter() {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            filterResults.values = if (constraint == null || constraint.isEmpty()) {
                filteredList
            } else {
                if (getLanguage() == "_arabic") {
                    val pattern = constraint.toString().trim { it <= ' ' }
                    filteredList.filter { reciter ->
                        reciter.name?.contains(pattern)!!
                    }
                } else {
                    filteredList.filter { reciter ->
                        reciter.name?.firstName()?.contains(constraint.toString().trim { it <= ' ' })!! ||
                                reciter.name?.secondName()?.contains(constraint.toString())!!
                    }
                }
            }
            return filterResults
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(p0: CharSequence?, results: FilterResults?) {
            with(recitersData) {
                clear()
                addAll(results?.values as List<Reciter>)
            }
            notifyDataSetChanged()
        }
    }

    fun String.firstName(): String = this.substring(0, this.indexOf(' '))
    fun String.secondName(): String = this.substring(this.indexOf(' ') + 1)

    inner class RecitersVH(
            private val binding: ItemReciterBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(comingReciter: Reciter) {
            binding.run {
                reciter = comingReciter
                callback = listener
                executePendingBindings()
            }
        }
    }

    interface ReciterAdapterListener {
        fun onReciterClicked(reciter: Reciter)
        fun onAddToFavoriteClicked(view: View, reciter: Reciter)
    }
}
