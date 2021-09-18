package com.nedaluof.qurany.data.repoImpl

import android.content.Context
import com.nedaluof.qurany.data.localsource.prefs.PreferencesManager
import com.nedaluof.qurany.data.localsource.room.ReciterDao
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Result
import com.nedaluof.qurany.data.remotesource.api.ApiService
import com.nedaluof.qurany.domain.repositories.RecitersRepository
import com.nedaluof.qurany.util.connectivityFlow
import com.nedaluof.qurany.util.getLanguage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by nedaluof on 12/11/2020.
 */
@ExperimentalCoroutinesApi
class RecitersRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val preferences: PreferencesManager,
    private val reciterDao: ReciterDao,
    @ApplicationContext
    private val context: Context
) : RecitersRepository {

    override suspend fun loadReciters(result: (Result<List<Reciter>>) -> Unit) {
        try {
            val response = apiService.getReciters(getLanguage())
            if (response.isSuccessful) {
                response.body()?.reciters?.map { reciter ->
                    preferences.hasKeyInDataStore(reciter.id!!) { result ->
                        reciter.inMyReciters = result
                    }
                }
                result(Result.success(response.body()?.reciters!!))
            } else {
                result(Result.error(null, response.message()))
            }
        } catch (exception: Exception) {
            result(Result.error(null, exception.message!!))
        }
    }

    override suspend fun addReciterToDatabase(reciter: Reciter, result: (Result<Boolean>) -> Unit) {
        try {
            reciterDao.insertReciter(reciter)
            //preferences.saveToPrefs(reciter.id!!, reciter.id!!)
            preferences.saveToDataStore(reciter.id!!, reciter.id!!)
            // inform user that reciter added to My Reciters List
            result(Result.success(true))
        } catch (exception: Exception) {
            Timber.d("addReciterToDatabase: Error : ${exception.message} ")
            result(Result.error(null, exception.message!!))
        }
    }

    override suspend fun observeConnectivity() = context.connectivityFlow()
}
