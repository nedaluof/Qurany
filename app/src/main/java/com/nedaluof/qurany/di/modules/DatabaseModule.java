package com.nedaluof.qurany.di.modules;

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
public class DatabaseModule {
    Context context;

    public DatabaseModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    QuranyDatabase provideDatabase() {
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
