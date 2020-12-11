package com.nedaluof.qurany.data;

import android.content.Context;

import com.nedaluof.qurany.data.prefs.PreferencesHelper;
import com.nedaluof.qurany.data.room.ReciterDao;
import com.nedaluof.qurany.data.room.ReciterSuraDAO;

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
    private ReciterSuraDAO reciterSuraDAO;

    @Inject
    public DataManager(PreferencesHelper preferencesHelper,
                       ReciterDao reciterDao,
                       ReciterSuraDAO reciterSuraDAO) {
        this.preferencesHelper = preferencesHelper;
        this.reciterDao = reciterDao;
        this.reciterSuraDAO = reciterSuraDAO;
    }


    public PreferencesHelper getPreferencesHelper() {
        return preferencesHelper;
    }


    public ReciterDao getReciterDao() {
        return reciterDao;
    }


    public boolean reciterHasKey(Context context, String key) {
        return preferencesHelper.hasKey(key);
    }
}
