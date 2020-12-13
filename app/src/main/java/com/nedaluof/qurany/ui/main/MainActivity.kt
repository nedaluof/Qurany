package com.nedaluof.qurany.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nedaluof.qurany.R
import com.nedaluof.qurany.databinding.ActivityMainBinding
import com.nedaluof.qurany.ui.myreciters.NewMyRecitersFragment
import com.nedaluof.qurany.ui.reciter.RecitersFragment
import com.nedaluof.qurany.util.toast
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by nedaluof on 6/2020. {Java}
 * Created by nedaluof on 12/13/2020. {Kotlin}
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigation.setItemEnabled(R.id.nav_recitera, true)
        binding.navigation.setItemSelected(R.id.nav_recitera, true)
        loadFragment(RecitersFragment())

        binding.navigation.setOnItemSelectedListener { i ->
            when (i) {
                R.id.nav_recitera -> {
                    binding.navigation.setItemSelected(R.id.nav_recitera, true)
                    loadFragment(RecitersFragment())
                }
                R.id.nav_my_reciters -> {
                    binding.navigation.setItemSelected(R.id.nav_my_reciters, true)
                    loadFragment(NewMyRecitersFragment())
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
                Handler(Looper.myLooper()!!).postDelayed({
                    doubleBackToExitPressedOnce = true
                }, 2000)
            } else {
                finish()
            }
        } else if (fragment is NewMyRecitersFragment) {
            loadFragment(RecitersFragment())
            binding.navigation.setItemSelected(R.id.nav_recitera, true)
        }
    }
}