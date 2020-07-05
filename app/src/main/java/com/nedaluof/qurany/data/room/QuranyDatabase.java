package com.nedaluof.qurany.data.room;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.data.model.ReciterSuraEntity;

/**
 * Created by nedaluof on 6/26/2020.
 */

@Database(entities = {ReciterSuraEntity.class, Reciter.class}, version = 1, exportSchema = false)
public abstract class QuranyDatabase extends RoomDatabase {
    private static final String TAG = "QuranyDatabase";
    private static final String DATABASE_NAME = "qurany";
    private static QuranyDatabase quranyDatabase;

    public static QuranyDatabase getInstance(Context context) {
        if (quranyDatabase == null) {
            quranyDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    QuranyDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        Log.d(TAG, "getInstance: getting database");
        return quranyDatabase;
    }

    public abstract ReciterSuraDAO getReciterSuraDao();

    public abstract ReciterDao getRecitersDao();

}
