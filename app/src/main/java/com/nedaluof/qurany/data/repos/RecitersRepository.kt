package com.nedaluof.qurany.data.repos

import android.util.Log
import com.nedaluof.qurany.data.api.ApiService
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Resource
import com.nedaluof.qurany.data.prefs.PreferencesHelper
import com.nedaluof.qurany.data.room.ReciterDao
import javax.inject.Inject

/**
 * Created by nedaluof on 12/11/2020.
 */
class RecitersRepository @Inject constructor(
        private val apiService: ApiService,
        private val preferences: PreferencesHelper,
        private val reciterDao: ReciterDao
) {
    suspend fun getReciters(language: String): Resource<List<Reciter>> {
        return try {
            val response = apiService.getReciters(language)
            if (response.isSuccessful) {
                response.body()?.reciters?.map { reciter ->
                    if (preferences.hasKey(reciter.id)) {
                        reciter.inMyReciters = true
                    }
                }
                Resource.success(response.body()?.reciters!!)
            } else {
                Resource.error(null, response.message())
            }
        } catch (exception: Exception) {
            Resource.error(null, exception.message!!)
        }
    }

    suspend fun addReciterToDatabase(reciter: Reciter): Resource<Boolean> {
        return try {
            reciterDao.insertReciter(reciter)
            preferences.saveToPrefs(reciter.id, reciter.id)
            //inform user that reciter added to My Reciters List
            Resource.success(true)
        } catch (exception: Exception) {
            Log.d(TAG, "addReciterToDatabase: Error : ${exception.message} ")
            Resource.error(null, exception.message!!)
        }
    }

    companion object {
        private const val TAG = "RecitersRepository"
    }
}