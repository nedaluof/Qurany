package com.nedaluof.qurany.data.localsource.prefs

import android.content.Context
import androidx.preference.PreferenceManager
import java.lang.ref.WeakReference

/**
 * Created by nedaluof on 6/23/2020. {Java}
 * Created by nedaluof on 12/17/2020. {Kotlin}
 */
class PreferencesHelper(
        private val context: Context,
) {

    fun saveToPrefs(key: String, value: Any) {
        val contextWeakReference = WeakReference(context)
        if (contextWeakReference.get() != null) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(contextWeakReference.get())
            val editor = prefs.edit()
            when (value) {
                is Int -> editor.putInt(key, value)
                is String -> editor.putString(key, value.toString())
                is Boolean -> editor.putBoolean(key, value)
                is Long -> editor.putLong(key, value)
                is Float -> editor.putFloat(key, value)
                is Double -> editor.putLong(key, java.lang.Double.doubleToRawLongBits(value))
            }
            editor.apply()
        }
    }

    fun removeFromPrefs(key: String) {
        val contextWeakReference = WeakReference(context)
        if (contextWeakReference.get() != null) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(contextWeakReference.get())
            prefs.edit().apply {
                remove(key)
                apply()
            }
        }
    }

    fun hasKey(key: String): Boolean {
        val contextWeakReference = WeakReference(context)
        if (contextWeakReference.get() != null) {
            val prefs = PreferenceManager.getDefaultSharedPreferences(contextWeakReference.get())
            return prefs.contains(key)
        }
        return false
    }
}
