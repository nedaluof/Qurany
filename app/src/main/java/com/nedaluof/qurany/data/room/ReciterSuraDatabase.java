package com.nedaluof.qurany.data.room;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import javax.inject.Inject;

/**
 * Created by nedaluof on 6/26/2020.
 */

@Database(entities = {ReciterSuraEntity.class}, version = 1, exportSchema = false)
public abstract class ReciterSuraDatabase extends RoomDatabase {
    private static final String TAG = "ReciterSuraDatabase";
    private static final String DATABASE_NAME = "qurany";
    private static ReciterSuraDatabase reciterSuraDatabase;

    public static ReciterSuraDatabase getInstance(Context context) {
        if (reciterSuraDatabase == null) {
            reciterSuraDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    ReciterSuraDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        Log.d(TAG, "getInstance: getting database");
        return reciterSuraDatabase;
    }

    public abstract ReciterSuraDAO getDao();

}
