package com.nedaluof.qurany.ui.myreciters

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nedaluof.qurany.R
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.databinding.FragmentMyRecitersBinding
import com.nedaluof.qurany.ui.adapters.MyRecitersAdapter
import com.nedaluof.qurany.ui.suras.SurasActivity
import com.nedaluof.qurany.util.toastyError
import com.nedaluof.qurany.util.toastySuccess
import com.tapadoo.alerter.Alerter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * Created by nedaluof on 7/5/2020. {JAVA}
 * Created by nedaluof on 12/12/2020. {Kotlin}
 */
@AndroidEntryPoint
class MyRecitersFragment : Fragment(R.layout.fragment_my_reciters) {

    private var _binding: FragmentMyRecitersBinding? = null
    private val binding: FragmentMyRecitersBinding
        get() = _binding!!

    private val viewModel: MyRecitersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMyRecitersBinding.bind(view)
        initComponents()
    }

    private fun initComponents() {
        initRecyclerView()
        observeMyRecitersViewModel()
        bindValues()
    }

    private fun initRecyclerView() {
        binding.recitersRecyclerView.adapter = MyRecitersAdapter().apply {
            listener = object : MyRecitersAdapter.MyRecitersAdapterListener {
                override fun onReciterClicked(reciter: Reciter) {
                    startActivity(
                            Intent(activity, SurasActivity::class.java)
                                    .putExtra(RECITER_KEY, reciter)
                    )
                }

                override fun onDeleteFromMyReciter(view: View, reciter: Reciter) {
                    val msg1 = resources.getString(R.string.alrt_delete_msg1)
                    val msg2 = resources.getString(R.string.alrt_delete_msg2)

                    Alerter.create(activity)
                            .setTitle(R.string.alrt_delete_title)
                            .setText(msg1 + reciter.name + msg2)
                            .addButton(resources.getString(R.string.alrt_delete_btn_ok), R.style.AlertButton) {
                                viewModel.deleteFromMyReciters(reciter)
                                // view.setImageResource(R.drawable.ic_favorite_selected)
                                Alerter.hide()
                            }
                            .addButton(resources.getString(R.string.alrt_delete_btn_cancel), R.style.AlertButton) { Alerter.hide() }
                            .enableSwipeToDismiss()
                            .show()
                }
            }
        }

        // Todo:need solution
        /* binding.recitersRecyclerView.apply {
             setHasFixedSize(true)
             adapter = ScaleInAnimationAdapter(myRecitersAdapter).apply {
                 setFirstOnly(false)
             }
         }*/
    }

    private fun observeMyRecitersViewModel() {
        with(viewModel) {
            getMyReciters()
            error.observe(viewLifecycleOwner) {
                Timber.d(it)
                activity?.toastyError(R.string.alrt_err_occur_msg)
            }

            resultOfDeleteReciter.observe(viewLifecycleOwner) { success ->
                if (success) {
                    activity?.toastySuccess(R.string.alrt_delete_success)
                } else {
                    activity?.toastyError(R.string.alrt_delete_fail)
                }
            }
        }
    }

    private fun bindValues() {
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
