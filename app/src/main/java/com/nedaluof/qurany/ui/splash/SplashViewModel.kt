package com.nedaluof.qurany.ui.splash

import androidx.lifecycle.ViewModel
import com.nedaluof.qurany.data.repository.SettingsRepository
import com.nedaluof.qurany.data.repository.SplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Created by NedaluOf on 9/11/2021.
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
  settingsRepository: SettingsRepository,
  repository: SplashRepository
) : ViewModel() {
  val versionName = MutableStateFlow("")

  //night mode
  private val _isNightModeEnabled = MutableStateFlow<Boolean?>(null)
  val isNightModeEnabled = _isNightModeEnabled.asStateFlow()

  init {
    versionName.value = repository.getAppVersionName()
    _isNightModeEnabled.value = settingsRepository.isNightModeEnabled()
  }
}