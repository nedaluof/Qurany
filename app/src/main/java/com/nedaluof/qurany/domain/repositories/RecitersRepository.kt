package com.nedaluof.qurany.domain.repositories

import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Result
import com.nedaluof.qurany.util.ConnectivityStatus
import kotlinx.coroutines.flow.Flow

/**
 * Created by NedaluOf on 8/16/2021.
 */
interface RecitersRepository {
    suspend fun getReciters(): Result<List<Reciter>>
    suspend fun addReciterToDatabase(reciter: Reciter): Result<Boolean>
    suspend fun observeConnectivity(): Flow<ConnectivityStatus>
}