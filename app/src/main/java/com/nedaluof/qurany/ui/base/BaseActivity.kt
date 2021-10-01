package com.nedaluof.qurany.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

/**
 * Created by NedaluOf on 9/11/2021.
 */
abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity() {
    lateinit var binding: DB
    abstract val layoutId: Int
    abstract val bindingVariable: Int
    abstract fun getViewModel(): ViewModel?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.apply {
            setVariable(bindingVariable, getViewModel())
            lifecycleOwner = this@BaseActivity
            executePendingBindings()
        }
    }
}
