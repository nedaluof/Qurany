package com.nedaluof.qurany.ui.myreciters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nedaluof.qurany.R
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.databinding.FragmentMyRecitersBinding
import com.nedaluof.qurany.ui.component.MyRecitersAdapter
import com.nedaluof.qurany.ui.suras.SurasActivity
import com.tapadoo.alerter.Alerter
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import timber.log.Timber

/**
 * Created by nedaluof on 7/5/2020. {JAVA}
 * Created by nedaluof on 12/12/2020. {Kotlin}
 */
@AndroidEntryPoint
class MyRecitersFragment : Fragment() {

  private var _binding: FragmentMyRecitersBinding? = null
  private val binding: FragmentMyRecitersBinding
    get() = _binding!!

  lateinit var myRecitersAdapter: MyRecitersAdapter
  private val viewModel: MyRecitersViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentMyRecitersBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initRecyclerView()
    observeMyRecitersViewModel()
  }

  private fun observeMyRecitersViewModel() {
    with(viewModel) {
      getMyReciters()

      reciters.observe(viewLifecycleOwner) {
        if (it.isNotEmpty()) {
          myRecitersAdapter.addReciters(it as ArrayList<Reciter>)

          if (binding.reciterListLayout.visibility == View.GONE) {
            binding.reciterListLayout.visibility = View.VISIBLE
            binding.noRecitersLayout.visibility = View.GONE
          }
        } else {
          binding.reciterListLayout.visibility = View.GONE
          binding.noRecitersLayout.visibility = View.VISIBLE
        }
      }

      loading.observe(viewLifecycleOwner) { show ->
        if (show) {
          binding.proReciters.visibility = View.VISIBLE
        } else {
          binding.proReciters.visibility = View.GONE
        }
      }

      error.observe(viewLifecycleOwner) {
        Timber.d(it)
        Alerter.create(activity)
          .setTitle(R.string.alrt_err_occur_title)
          .setText(R.string.alrt_err_occur_msg)
          .hideIcon()
          .setBackgroundColorRes(R.color.red)
          .show()
      }

      resultOfDeleteReciter.observe(viewLifecycleOwner) { success ->
        if (success) {
          Alerter.create(activity)
            .setTitle(R.string.alrt_delete_success)
            .enableSwipeToDismiss()
            .show()
        } else {
          Alerter.create(activity)
            .setTitle(R.string.alrt_delete_fail)
            .enableSwipeToDismiss()
            .show()
        }
      }
    }
  }

  private fun initRecyclerView() {
    myRecitersAdapter = MyRecitersAdapter(requireContext()).apply {
      listener = object : MyRecitersAdapter.MyRecitersAdapterListener {
        override fun onReciterClicked(reciter: Reciter) {
          startActivity(
            Intent(activity, SurasActivity::class.java)
              .putExtra(RECITER_KEY, reciter)
          )
        }

        override fun onDeleteFromMyReciter(reciter: Reciter) {
          val msg1 = resources.getString(R.string.alrt_delete_msg1)
          val msg2 = resources.getString(R.string.alrt_delete_msg2)
          Alerter.create(activity)
            .setTitle(R.string.alrt_delete_title)
            .setText(msg1 + reciter.name + msg2)
            .addButton(resources.getString(R.string.alrt_delete_btn_ok), R.style.AlertButton) {
              viewModel.deleteFromMyReciters(reciter)
              Alerter.hide()
            }
            .addButton(resources.getString(R.string.alrt_delete_btn_cancel), R.style.AlertButton) { Alerter.hide() }
            .enableSwipeToDismiss()
            .show()
        }
      }
    }
    binding.recitersRecyclerView.apply {
      setHasFixedSize(true)
      adapter = ScaleInAnimationAdapter(myRecitersAdapter).apply {
        setFirstOnly(false)
      }
    }
  }

  override fun onDestroyView() {
    _binding = null
    super.onDestroyView()
  }

  companion object{
    const val RECITER_KEY = "RECITER_KEY"
  }
}
