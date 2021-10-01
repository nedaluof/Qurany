package com.nedaluof.qurany.data.repositoryImpl

import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Result
import com.nedaluof.qurany.data.repository.MyRecitersRepository
import com.nedaluof.qurany.data.source.localsource.preferences.PreferencesManager
import com.nedaluof.qurany.data.source.localsource.room.ReciterDao
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

/**
 * Created by nedaluof on 12/12/2020.
 */
class MyRecitersRepositoryImpl @Inject constructor(
    private val recitersDao: ReciterDao,
    private val preferences: PreferencesManager,
) : MyRecitersRepository {

    override fun getMyReciters() = recitersDao.getMyReciters()
        .distinctUntilChanged()

    override suspend fun deleteFromMyReciters(reciter: Reciter, result: (Result<Boolean>) -> Unit) {
        try {
            recitersDao.deleteReciter(reciter)
            // remove from preferences
            preferences.removeFromDataStore(reciter.id!!)
            // now inform  deletion successfully
            result(Result.success(true))
        } catch (exception: Exception) {
            result(Result.error(null, exception.message!!))
        }
    }
}
