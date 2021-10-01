package com.nedaluof.qurany.data.source.localsource.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.preference.PreferenceManager
import javax.inject.Inject

/**
 * Created by nedaluof on 6/23/2020. {Java}
 * Created by nedaluof on 12/17/2020. {Kotlin}
 * Updated by nedaluof on 9/17/2020. {From [PreferenceManager] to [preferencesDataStore]}
 */

class PreferencesManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesManager {
    override suspend fun saveToDataStore(key: String, value: String) {
        dataStore.edit { preferences ->
            preferences[getPreferencesKey(key)] = value
        }
    }

    override suspend fun removeFromDataStore(key: String) {
        dataStore.edit { preferences ->
            preferences.remove(getPreferencesKey(key))
        }
    }

    override suspend fun hasKeyInDataStore(key: String, result: (Boolean) -> Unit) {
        dataStore.edit {
            result(it.contains(getPreferencesKey(key)))
        }
    }

    private fun getPreferencesKey(key: String) = stringPreferencesKey(key)
}
