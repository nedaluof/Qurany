package com.nedaluof.qurany.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nedaluof.qurany.R
import com.nedaluof.qurany.databinding.ActivityMainBinding
import com.nedaluof.qurany.ui.myreciters.MyRecitersFragment
import com.nedaluof.qurany.ui.reciters.RecitersFragment
import com.nedaluof.qurany.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by nedaluof on 6/2020. {Java}
 * Created by nedaluof on 12/13/2020. {Kotlin}
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private var doubleBackToExitPressedOnce = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.navigation.setItemEnabled(R.id.nav_reciters, true)
    binding.navigation.setItemSelected(R.id.nav_reciters, true)
    loadFragment(RecitersFragment())

    binding.navigation.setOnItemSelectedListener { i ->
      when (i) {
        R.id.nav_reciters -> {
          binding.navigation.setItemSelected(R.id.nav_reciters, true)
          loadFragment(RecitersFragment())
        }
        R.id.nav_my_reciters -> {
          binding.navigation.setItemSelected(R.id.nav_my_reciters, true)
          loadFragment(MyRecitersFragment())
        }
      }
    }
  }

  private fun loadFragment(fragment: Fragment) {
    supportFragmentManager
      .beginTransaction()
      .replace(binding.container.id, fragment)
      .commit()
  }

  override fun onBackPressed() {
    val fragment = supportFragmentManager.findFragmentById(binding.container.id)
    if (fragment is RecitersFragment) {
      if (doubleBackToExitPressedOnce) {
        doubleBackToExitPressedOnce = false
        toast(R.string.exit_app_msg)
        Handler(Looper.myLooper()!!).postDelayed(
          {
            doubleBackToExitPressedOnce = true
          },
          2000
        )
      } else {
        finish()
      }
    } else if (fragment is MyRecitersFragment) {
      loadFragment(RecitersFragment())
      binding.navigation.setItemSelected(R.id.nav_reciters, true)
    }
  }
}
