package com.nedaluof.qurany.ui.reciters

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.nedaluof.qurany.BR
import com.nedaluof.qurany.R
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.databinding.FragmentRecitersBinding
import com.nedaluof.qurany.ui.adapters.RecitersAdapter
import com.nedaluof.qurany.ui.adapters.RecitersAdapter.ReciterAdapterListener
import com.nedaluof.qurany.ui.base.BaseFragment
import com.nedaluof.qurany.ui.suras.SurasActivity
import com.nedaluof.qurany.util.toastyError
import com.nedaluof.qurany.util.toastySuccess
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by nedaluof on 12/11/2020.
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecitersFragment : BaseFragment<FragmentRecitersBinding>() {

    override val layoutId = R.layout.fragment_reciters
    override val bindingVariable = BR.viewmodel
    private val recitersViewModel by viewModels<RecitersViewModel>()
    override fun getViewModel() = recitersViewModel
    private val reciterAdapter = RecitersAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // to not re-handle set adapter to recycler view in each reconnection
        initRecyclerViewAdapter()
        // to not re-handle set OnQueryTextListener in each reconnection
        initSearchOfReciters()
        //observe viewModel
        observeViewModel()
    }


    private fun initRecyclerViewAdapter() {
        binding.recitersRecyclerView.adapter = reciterAdapter.apply {
            listener = object : ReciterAdapterListener {
                override fun onReciterClicked(reciter: Reciter) {
                    startActivity(
                        Intent(context, SurasActivity::class.java)
                            .putExtra(RECITER_KEY, reciter)
                    )
                }

                override fun onAddToFavoriteClicked(view: View, reciter: Reciter) {
                    view.visibility = View.GONE
                    recitersViewModel.addReciterToMyReciters(reciter)
                }
            }
        }
    }

    private fun initSearchOfReciters() {
        binding.recitersSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                reciterAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                reciterAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun observeViewModel() {
        with(recitersViewModel) {
            error.observe(viewLifecycleOwner) { (_, _) ->
                activity?.toastyError(R.string.alrt_err_occur_msg)
            }
            resultOfAddReciter.observe(viewLifecycleOwner) {
                if (it) {
                    activity?.toastySuccess(R.string.alrt_add_success_msg)
                }
            }
        }
    }

    companion object {
        const val RECITER_KEY = "RECITER_KEY"
    }
}
