package com.nedaluof.qurany.data;

import com.nedaluof.qurany.data.prefs.PreferencesHelper;
import com.nedaluof.qurany.data.room.ReciterDao;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nedaluof on 6/13/2020.
 */
@Singleton
public class DataManager {
    private static final String TAG = "DataManager";
    private PreferencesHelper preferencesHelper;
    private ReciterDao reciterDao;

    @Inject
    public DataManager(PreferencesHelper preferencesHelper,
                       ReciterDao reciterDao) {
        this.preferencesHelper = preferencesHelper;
        this.reciterDao = reciterDao;
    }


    public PreferencesHelper getPreferencesHelper() {
        return preferencesHelper;
    }


    public ReciterDao getReciterDao() {
        return reciterDao;
    }
}
