package com.nedaluof.qurany.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.nedaluof.qurany.BuildConfig
import com.nedaluof.qurany.R
import com.nedaluof.qurany.databinding.ActivitySplashBinding
import com.nedaluof.qurany.ui.main.MainActivity
import yanzhikai.textpath.calculator.AroundCalculator
import yanzhikai.textpath.painter.FireworksPainter

/**
 * Created by nedaluof on 6/13/2020. {JAVA}
 * Created by nedaluof on 12/13/2020. {Kotlin}
 */
class SplashActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivitySplashBinding.inflate(layoutInflater)
    setContentView(binding.root)
    binding.stpvQurany.setPathPainter(FireworksPainter())
    binding.stpvQurany.setCalculator(AroundCalculator())
    binding.stpvQurany.startAnimation(0F, 1F)
    binding.stpvQurany.setFillColor(true)
    val versionLabel = getString(R.string.app_version)
    val appVersion = "$versionLabel ${BuildConfig.VERSION_NAME}"
    binding.tvVersion.text = appVersion
    Handler(Looper.myLooper()!!).postDelayed(
      {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
      },
      2200
    )
  }

  override fun onBackPressed() { /*Kept empty*/ }
}
