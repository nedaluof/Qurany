package com.nedaluof.qurany.ui.component

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.nedaluof.qurany.R
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.databinding.ItemReciterBinding
import com.nedaluof.qurany.util.Utility


/**
 * Created by nedaluof on 12/11/2020.
 */
class RecitersAdapter(
        context: Context
) : RecyclerView.Adapter<RecitersAdapter.RecitersVH>(),
        Filterable {

    lateinit var listener: ReciterAdapterListener
    private val recitersData = ArrayList<Reciter>()
    private val filterRecitersData = ArrayList<Reciter>()
    private val surasCountLabel = context.getString(R.string.sura_count)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecitersVH {
        val binding = ItemReciterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecitersVH(binding)
    }

    override fun onBindViewHolder(holder: RecitersVH, position: Int) {
        val reciter = recitersData[position]
        with(holder.binding) {
            tvReciterName.text = reciter.name
            tvReciterRewaya.text = reciter.rewaya
            val surasCount = "$surasCountLabel ${reciter.count}"
            tvReciterSuraCount.text = surasCount
            reciterDataLayout.setOnClickListener { listener.onReciterClicked(reciter) }

            if (reciter.inMyReciters) {
                imgFavorite.visibility = View.GONE
            } else {
                imgFavorite.visibility = View.VISIBLE
            }

            imgFavorite.setOnClickListener {
                listener.onAddToFavoriteClicked(reciter)
                //need good solution
                //todo update this and progress from ReciterFragment
                imgFavorite.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = recitersData.size

    interface ReciterAdapterListener {
        fun onReciterClicked(reciter: Reciter)
        fun onAddToFavoriteClicked(reciter: Reciter)
    }

    fun addReciters(reciters: ArrayList<Reciter>) {
        recitersData.apply {
            clear()
            addAll(reciters)
        }

        notifyDataSetChanged()

        filterRecitersData.apply {
            clear()
            addAll(reciters)
        }
    }

    override fun getFilter(): Filter = searchFilter


    private val searchFilter = object : Filter() {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filterResults = FilterResults()
            filterResults.values = if (constraint == null || constraint.isEmpty()) {
                filterRecitersData
            } else {
                if (Utility.getLanguage() == "_arabic") {
                    val pattern = constraint.toString().trim { it <= ' ' }
                    filterRecitersData.filter { reciter ->
                        reciter.name?.contains(pattern)!!
                    }
                } else {
                    filterRecitersData.filter { reciter ->
                        reciter.name?.firstName()?.contains(constraint.toString().trim { it <= ' ' })!! ||
                                reciter.name?.secondName()?.contains(constraint.toString())!!
                    }
                }
            }
            return filterResults
        }

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

    class RecitersVH(val binding: ItemReciterBinding) : RecyclerView.ViewHolder(binding.root)
}