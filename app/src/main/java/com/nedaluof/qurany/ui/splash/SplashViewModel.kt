package com.nedaluof.qurany.ui.splash

import androidx.lifecycle.ViewModel
import com.nedaluof.qurany.data.repository.SplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * Created by NedaluOf on 9/11/2021.
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: SplashRepository
) : ViewModel() {
    val versionName = MutableStateFlow("")

    init {
        versionName.value = repository.getAppVersionName()
    }
}
