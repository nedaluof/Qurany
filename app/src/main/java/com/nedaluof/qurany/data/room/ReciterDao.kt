package com.nedaluof.qurany.data.room

import androidx.room.*
import com.nedaluof.qurany.data.model.Reciter
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by nedaluof on 12/11/2020.
 */
@Dao
interface ReciterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReciters(list: List<Reciter>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReciter(reciter: Reciter)

    @Query("select * from reciter order by name")
    fun getReciters(): Flowable<List<Reciter>>

    //to check table size
    @Query("select * from reciter")
    fun getRecitersForCheck(): List<Reciter>

    //to check records number
    @Query("SELECT COUNT(*) FROM reciter")
    fun getRecitersRecordsNumber(): Int

    @Query("Delete from reciter")
    fun deleteAllReciters(): Completable

    @Delete
    fun deleteReciter(reciter: Reciter?): Completable
}