package com.nedaluof.qurany.domain.repositories

/**
 * Created by NedaluOf on 8/16/2021.
 */
interface SurasRepository {
    fun checkIfSuraExist(subPath: String): Boolean
}