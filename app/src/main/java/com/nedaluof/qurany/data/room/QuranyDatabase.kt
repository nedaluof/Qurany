package com.nedaluof.qurany.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.ReciterSuraEntity

/**
 * Created by nedaluof on 12/2/2020.
 */
@Database(entities = [Reciter::class, ReciterSuraEntity::class], version = 1, exportSchema = false)
abstract class QuranyDatabase : RoomDatabase() {
    abstract fun getReciterSuraDao(): ReciterSuraDAO

    abstract fun getRecitersDao(): ReciterDao

    companion object {
        const val DB_NAME = "Qurany.db"
    }
}