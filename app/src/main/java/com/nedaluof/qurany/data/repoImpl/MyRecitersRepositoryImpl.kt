package com.nedaluof.qurany.data.repoImpl

import com.nedaluof.qurany.data.localsource.prefs.PreferencesManager
import com.nedaluof.qurany.data.localsource.room.ReciterDao
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Result
import com.nedaluof.qurany.domain.repositories.MyRecitersRepository
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

    override suspend fun deleteFromMyReciters(reciter: Reciter): Result<Boolean> {
        return try {
            recitersDao.deleteReciter(reciter)
            // remove from preferences
            //preferences.removeFromPrefs(reciter.id!!)
            preferences.removeFromDataStore(reciter.id!!)
            // now inform  deletion successfully
            Result.success(true)
        } catch (exception: Exception) {
            Result.error(null, exception.message!!)
        }
    }
}
