package com.nedaluof.qurany.ui.splash

import android.content.Context
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
import yanzhikai.textpath.calculator.AroundCalculator
import yanzhikai.textpath.painter.FireworksPainter

/**
 * Created by nedaluof on 6/13/2020. {JAVA}
 * Created by nedaluof on 12/13/2020. {Kotlin}
 * Updated by nedaluof on 9/11/2021.
 */
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
    setupPathPainter()
    goToMainActivity()
  }

  private fun goToMainActivity() {
    Handler(Looper.getMainLooper()).postDelayed(
      {
        startActivity(MainActivity.getIntent(this@SplashActivity))
        finish()
      },
      2200
    )
  }

  private fun setupPathPainter() {
    with(binding.stpvQurany) {
      setPathPainter(FireworksPainter())
      setCalculator(AroundCalculator())
      startAnimation(0F, 1F)
      setFillColor(true)
    }
  }

  override fun onBackPressed() { /*Kept empty*/
  }

  companion object {
    fun getIntent(
      context: Context
    ) = Intent(context, SplashActivity::class.java)
  }
}