package com.nedaluof.qurany.di.modules;

import android.content.Context;

import com.nedaluof.qurany.data.room.ReciterSuraDAO;
import com.nedaluof.qurany.data.room.ReciterSuraDatabase;
import com.nedaluof.qurany.data.room.ReciterSuraDatabase_Impl;
import com.nedaluof.qurany.di.ApplicationContext;
import com.nedaluof.qurany.di.DatabaseScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nedaluof on 6/26/2020.
 */
@Module
public class DatabaseModule {
    @ApplicationContext
    Context context;

    public DatabaseModule(@ApplicationContext Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    ReciterSuraDatabase provideDatabase() {
        return ReciterSuraDatabase.getInstance(context);
    }

    @Singleton
    @Provides
    ReciterSuraDAO provideDao(ReciterSuraDatabase database) {
        return database.getDao();
    }


}
