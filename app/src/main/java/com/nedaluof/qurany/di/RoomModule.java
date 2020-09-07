package com.nedaluof.qurany.di;

import android.content.Context;

import com.nedaluof.qurany.data.room.QuranyDatabase;
import com.nedaluof.qurany.data.room.ReciterDao;
import com.nedaluof.qurany.data.room.ReciterSuraDAO;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nedaluof on 6/26/2020.
 */
@Module
public class RoomModule {

    @Singleton
    @Provides
    QuranyDatabase provideDatabase(Context context) {
        return QuranyDatabase.getInstance(context);
    }

    @Singleton
    @Provides
    ReciterSuraDAO provideReciterSuraDao(QuranyDatabase database) {
        return database.getReciterSuraDao();
    }

    @Singleton
    @Provides
    ReciterDao provideReciterDao(QuranyDatabase database) {
        return database.getRecitersDao();
    }
}
