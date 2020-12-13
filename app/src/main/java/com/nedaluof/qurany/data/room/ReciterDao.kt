package com.nedaluof.qurany.data.room

import androidx.room.*
import com.nedaluof.qurany.data.model.Reciter
import kotlinx.coroutines.flow.Flow

/**
 * Created by nedaluof on 12/11/2020.
 */
@Dao
interface ReciterDao {
    /*For Future use Todo (Caching) */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReciters(list: List<Reciter>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReciter(reciter: Reciter)

    /* Used in MyReciters */
    @Query("select * from reciter order by name")
    fun getMyReciters(): Flow<List<Reciter>>

    //to check records number
    @Query("SELECT COUNT(*) FROM reciter")
    fun getRecitersRecordsNumber(): Int

    /* For Future use Todo (setting delete All Reciters in Mt Reciters) */
    @Query("Delete from reciter")
    suspend fun deleteAllReciters()

    /* Used in MyReciters */
    @Delete
    suspend fun deleteReciter(reciter: Reciter?)
}