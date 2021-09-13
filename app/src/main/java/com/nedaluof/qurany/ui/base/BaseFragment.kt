package com.nedaluof.qurany.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.nedaluof.qurany.util.toastyError
import com.nedaluof.qurany.util.toastySuccess

/**
 * Created by NedaluOf on 9/11/2021.
 */
abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {

    lateinit var binding: DB
    abstract val layoutId: Int
    abstract val bindingVariable: Int
    abstract fun getViewModel(): BaseViewModel?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setVariable(bindingVariable, getViewModel())
            lifecycleOwner = viewLifecycleOwner
            executePendingBindings()
        }
    }

    fun toastyError(stringId: Int) {
        requireActivity().toastyError(stringId)
    }

    fun toastySuccess(stringId: Int) {
        requireActivity().toastySuccess(stringId)
    }

}