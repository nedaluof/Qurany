package com.nedaluof.qurany.di.components;

import android.content.Context;

import com.nedaluof.qurany.MainActivity;
import com.nedaluof.qurany.QuranyApplication;
import com.nedaluof.qurany.data.DataManager;
import com.nedaluof.qurany.data.room.ReciterSuraDAO;
import com.nedaluof.qurany.data.room.ReciterSuraDatabase;
import com.nedaluof.qurany.di.ApplicationContext;
import com.nedaluof.qurany.di.modules.ApplicationModule;
import com.nedaluof.qurany.di.modules.DatabaseModule;
import com.nedaluof.qurany.ui.reciter.ReciterActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nedaluof on 6/23/2020.
 */
@Singleton
@Component(modules = {DatabaseModule.class, ApplicationModule.class})
public interface AppComponent {
    DataManager getDataManager();

    @ApplicationContext
    Context getContext();

    void inject(ReciterActivity activity);

    void inject(QuranyApplication quranyApplication);
    void inject(MainActivity mainActivity);

    ReciterSuraDatabase getDataBase();

    ReciterSuraDAO getReciterSuraDAO();
}
