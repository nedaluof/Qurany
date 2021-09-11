package com.nedaluof.qurany.data.repoImpl

import android.content.Context
import com.nedaluof.qurany.data.localsource.prefs.PreferencesHelper
import com.nedaluof.qurany.data.localsource.room.ReciterDao
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Result
import com.nedaluof.qurany.data.remotesource.api.ApiService
import com.nedaluof.qurany.domain.repositories.RecitersRepository
import com.nedaluof.qurany.util.connectivityFlow
import com.nedaluof.qurany.util.getLanguage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

/**
 * Created by nedaluof on 12/11/2020.
 */
@ExperimentalCoroutinesApi
class RecitersRepositoryImpl(
    private val apiService: ApiService,
    private val preferences: PreferencesHelper,
    private val reciterDao: ReciterDao,
    private val context: Context
) : RecitersRepository {

    override suspend fun getReciters(): Result<List<Reciter>> {
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

    override suspend fun addReciterToDatabase(reciter: Reciter): Result<Boolean> {
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

    override suspend fun observeConnectivity() = context.connectivityFlow()
}
