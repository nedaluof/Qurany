package com.nedaluof.qurany.di.components;

import com.nedaluof.qurany.data.DataManager;
import com.nedaluof.qurany.data.model.Reciters;
import com.nedaluof.qurany.ui.reciter.ReciterActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nedaluof on 6/23/2020.
 */
@Singleton
@Component
public interface AppComponent {
    DataManager getDataManager();

    void inject(ReciterActivity activity);
}
