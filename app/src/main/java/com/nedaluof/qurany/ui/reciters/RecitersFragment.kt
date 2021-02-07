package com.nedaluof.qurany.ui.reciters

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nedaluof.qurany.R
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.databinding.FragmentRecitersBinding
import com.nedaluof.qurany.ui.component.RecitersAdapter
import com.nedaluof.qurany.ui.component.RecitersAdapter.ReciterAdapterListener
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
class RecitersFragment : Fragment(R.layout.fragment_reciters) {

    private var _binding: FragmentRecitersBinding? = null
    private val binding: FragmentRecitersBinding
        get() = _binding!!

    private val reciterAdapter = RecitersAdapter()

    private val viewModel: RecitersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecitersBinding.bind(view)
        //to not re-handle set adapter to recycler view in each reconnection
        initRecyclerViewAdapter()
        //to not re-handle set OnQueryTextListener in each reconnection
        initSearchOfReciters()
        //Todo need efficient solution
        viewModel.observeConnectivity(requireActivity())
        viewModel.connected.observe(viewLifecycleOwner) { available ->
            if (available) {
                initComponents()
            }
        }
    }

    private fun initComponents() {
        observeViewModel()
        initBindingWithValues()
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
                    viewModel.addReciterToMyReciters(reciter)
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
        with(viewModel) {
            getReciters()
            //Todo show text view rather than toast
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

    private fun initBindingWithValues() {
        binding.run {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val RECITER_KEY = "RECITER_KEY"
    }

}
