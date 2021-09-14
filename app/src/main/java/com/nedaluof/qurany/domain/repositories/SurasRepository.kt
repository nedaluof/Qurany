package com.nedaluof.qurany.domain.repositories

import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Sura

/**
 * Created by NedaluOf on 8/16/2021.
 */
interface SurasRepository {
    fun checkIfSuraExist(subPath: String): Boolean
    fun getMappedReciterSuras(reciterData: Reciter): List<Sura>
}