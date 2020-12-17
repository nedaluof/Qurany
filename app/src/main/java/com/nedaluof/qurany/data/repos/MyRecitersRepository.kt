package com.nedaluof.qurany.data.repos

import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Result
import com.nedaluof.qurany.data.prefs.PreferencesHelper
import com.nedaluof.qurany.data.room.ReciterDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

/**
 * Created by nedaluof on 12/12/2020.
 */
class MyRecitersRepository @Inject constructor(
        private val recitersDao: ReciterDao,
        private val preferences: PreferencesHelper
) {


    val getMyReciters: Flow<List<Reciter>>
        get() = recitersDao.getMyReciters()
                .distinctUntilChanged()

    suspend fun deleteFromMyReciters(reciter: Reciter): Result<Boolean> {
        return try {
            recitersDao.deleteReciter(reciter)
            //remove from preferences
            preferences.removeFromPrefs(reciter.id!!)
            //now inform  deletion successfully
            Result.success(true)
        } catch (exception: Exception) {
            Result.error(null, exception.message!!)
        }
    }
}