package com.nedaluof.qurany.data.repositoryImpl

import com.nedaluof.qurany.data.datasource.localsource.preferences.PreferencesManager
import com.nedaluof.qurany.data.repository.SettingsRepository
import javax.inject.Inject

/**
 * Created by NedaluOf on 12/9/2022.
 */
class SettingsRepositoryImpl @Inject constructor(
  private val preferencesManager: PreferencesManager
) : SettingsRepository {

  override fun isNightModeEnabled(): Boolean {
    return preferencesManager.getFromPreferences(NIGHT_MODE_KEY) as? Boolean ?: false
  }

  override fun isCurrentLanguageEnglish(): Boolean {
    val result = preferencesManager.getFromPreferences<String>(LANGUAGE_KEY)
    return if (result != null) {
      result == "en"
    } else {
      true
    }
  }

  override fun updateNightMode(isEnabled: Boolean) {
    preferencesManager.addToPreferences(NIGHT_MODE_KEY, isEnabled)
  }

  override fun updateCurrentLanguage() {
    val newLocale = if (isCurrentLanguageEnglish()) "ar" else "en"
    preferencesManager.removeFromPreferences(LANGUAGE_KEY)
    preferencesManager.addToPreferences(LANGUAGE_KEY, newLocale)
  }

  companion object {
    private const val NIGHT_MODE_KEY = "NIGHT_MODE_KEY"
    private const val LANGUAGE_KEY = "LANGUAGE_KEY"
  }
}