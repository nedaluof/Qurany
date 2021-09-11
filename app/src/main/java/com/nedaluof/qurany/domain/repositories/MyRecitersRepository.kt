package com.nedaluof.qurany.domain.repositories

import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Result
import kotlinx.coroutines.flow.Flow

/**
 * Created by NedaluOf on 8/16/2021.
 */
interface MyRecitersRepository {
    fun getMyReciters(): Flow<List<Reciter>>

    suspend fun deleteFromMyReciters(reciter: Reciter): Result<Boolean>
}