package com.nedaluof.qurany.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nedaluof.qurany.data.model.Reciter

/**
 * Created by nedaluof on 12/2/2020.
 */
@Database(entities = [Reciter::class], version = 1, exportSchema = false)
abstract class QuranyDatabase : RoomDatabase() {

    abstract fun getRecitersDao(): ReciterDao

    companion object {
        const val DB_NAME = "Qurany.db"
    }
}