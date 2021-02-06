package com.nedaluof.qurany.data.repos

import com.nedaluof.qurany.data.api.ApiService
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Result
import com.nedaluof.qurany.data.prefs.PreferencesHelper
import com.nedaluof.qurany.data.room.ReciterDao
import com.nedaluof.qurany.util.getLanguage
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by nedaluof on 12/11/2020.
 */
class RecitersRepository @Inject constructor(
        private val apiService: ApiService,
        private val preferences: PreferencesHelper,
        private val reciterDao: ReciterDao,
) {

    suspend fun getReciters(): Result<List<Reciter>> {
        return try {
            val response = apiService.getReciters(getLanguage())
            if (response.isSuccessful) {
                response.body()?.reciters?.map { reciter ->
                    if (preferences.hasKey(reciter.id!!)) {
                        reciter.inMyReciters = true
                    }
                }
                Result.success(response.body()?.reciters!!)
            } else {
                Result.error(null, response.message())
            }
        } catch (exception: Exception) {
            Result.error(null, exception.message!!)
        }
    }

    suspend fun addReciterToDatabase(reciter: Reciter): Result<Boolean> {
        return try {
            reciterDao.insertReciter(reciter)
            preferences.saveToPrefs(reciter.id!!, reciter.id!!)
            // inform user that reciter added to My Reciters List
            Result.success(true)
        } catch (exception: Exception) {
            Timber.d("addReciterToDatabase: Error : ${exception.message} ")
            Result.error(null, exception.message!!)
        }
    }
}
