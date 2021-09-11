package com.nedaluof.qurany.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.nedaluof.qurany.BR
import com.nedaluof.qurany.R
import com.nedaluof.qurany.databinding.ActivitySplashBinding
import com.nedaluof.qurany.ui.base.BaseActivity
import com.nedaluof.qurany.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import yanzhikai.textpath.calculator.AroundCalculator
import yanzhikai.textpath.painter.FireworksPainter

/**
 * Created by nedaluof on 6/13/2020. {JAVA}
 * Created by nedaluof on 12/13/2020. {Kotlin}
 */
@ExperimentalCoroutinesApi
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override val layoutId = R.layout.activity_splash
    override val bindingVariable = BR.viewmodel

    override fun getViewModel(): ViewModel {
        val viewModel by viewModels<SplashViewModel>()
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.stpvQurany.setPathPainter(FireworksPainter())
        binding.stpvQurany.setCalculator(AroundCalculator())
        binding.stpvQurany.startAnimation(0F, 1F)
        binding.stpvQurany.setFillColor(true)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 2200)
    }

    override fun onBackPressed() { /*Kept empty*/
    }
}