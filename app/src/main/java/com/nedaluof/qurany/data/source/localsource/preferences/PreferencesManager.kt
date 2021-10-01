package com.nedaluof.qurany.data.source.localsource.preferences

/**
 * Created by NedaluOf on 9/18/2021.
 */
interface PreferencesManager {
    suspend fun saveToDataStore(key: String, value: String)
    suspend fun removeFromDataStore(key: String)
    suspend fun hasKeyInDataStore(key: String, result: (Boolean) -> Unit)
}
