package com.nedaluof.qurany.di.components;

import android.content.Context;

import com.nedaluof.qurany.TestRoomActivity;
import com.nedaluof.qurany.QuranyApplication;
import com.nedaluof.qurany.data.DataManager;
import com.nedaluof.qurany.data.room.ReciterDao;
import com.nedaluof.qurany.data.room.ReciterSuraDAO;
import com.nedaluof.qurany.data.room.QuranyDatabase;
import com.nedaluof.qurany.di.modules.ApplicationModule;
import com.nedaluof.qurany.di.modules.DatabaseModule;
import com.nedaluof.qurany.ui.myreciters.MyRecitersFragment;
import com.nedaluof.qurany.ui.reciter.RecitersFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nedaluof on 6/23/2020.
 */
@Singleton
@Component(modules = {ApplicationModule.class, DatabaseModule.class})
public interface AppComponent {
    void inject(QuranyApplication quranyApplication);

    void inject(TestRoomActivity testRoomActivity);

    void inject(RecitersFragment recitersFragment);

    void inject(MyRecitersFragment myRecitersFragment);

    Context getContext();

    DataManager getDataManager();

    QuranyDatabase getDataBase();

    ReciterSuraDAO getReciterSuraDAO();

    ReciterDao getReciterDao();

}
