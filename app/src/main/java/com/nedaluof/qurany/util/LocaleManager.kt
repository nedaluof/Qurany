package com.nedaluof.qurany.util

import android.content.Context
import android.os.Build
import com.nedaluof.qurany.data.repository.SettingsRepository
import java.util.*
import javax.inject.Inject

/**
 * Created by NedaluOf on 12/16/2022.
 */
class LocaleManager @Inject constructor(
  private val settingsRepository: SettingsRepository
) {

  fun configureAppLocale(context: Context): Context {
    return configureAppResources(context)
  }

  private fun configureAppResources(context: Context): Context {
    val locale = Locale(getLanguage())
    Locale.setDefault(locale)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      return updateResources(context, locale)
    }
    return updateResourcesLegacy(context, locale)
  }

  private fun updateResources(context: Context, locale: Locale): Context {
    val configuration = context.resources.configuration.apply {
      setLocale(locale)
      setLayoutDirection(locale)
    }
    return context.createConfigurationContext(configuration)
  }

  @Suppress("DEPRECATION")
  private fun updateResourcesLegacy(context: Context, locale: Locale): Context {
    val resources = context.resources
    with(resources.configuration) {
      this.locale = locale
      setLayoutDirection(locale)
      resources.updateConfiguration(this, resources.displayMetrics)
    }
    return context
  }

  private fun getLanguage() = if (settingsRepository.isCurrentLanguageEnglish()) "en" else "ar"
}