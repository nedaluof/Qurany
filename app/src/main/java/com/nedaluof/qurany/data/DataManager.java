package com.nedaluof.qurany.data;

import android.content.Context;

import com.nedaluof.qurany.data.api.ApiHelper;
import com.nedaluof.qurany.data.prefs.PreferencesHelper;
import com.nedaluof.qurany.data.room.ReciterRepository;
import com.nedaluof.qurany.data.room.ReciterSuraRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nedaluof on 6/13/2020.
 */
@Singleton
public class DataManager {
    private static final String TAG = "DataManager";
    private ApiHelper apiHelper;
    private PreferencesHelper preferencesHelper;
    private ReciterSuraRepository reciterSuraRepository;
    private ReciterRepository reciterRepository;

    @Inject
    public DataManager(ApiHelper apiHelper, PreferencesHelper preferencesHelper, ReciterSuraRepository reciterSuraRepository, ReciterRepository reciterRepository) {
        this.apiHelper = apiHelper;
        this.preferencesHelper = preferencesHelper;
        this.reciterSuraRepository = reciterSuraRepository;
        this.reciterRepository = reciterRepository;
    }


    public ApiHelper getApiHelper() {
        return apiHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return preferencesHelper;
    }

    public ReciterSuraRepository getReciterSuraRepository() {
        return reciterSuraRepository;
    }

    public ReciterRepository getReciterRepository() {
        return reciterRepository;
    }

    boolean reciterTableIsEmpty = false;

    public boolean reciterTableIsEmpty() {
        return reciterRepository.reciterListCheck() == 0;
    }

    public boolean reciterHasKey(Context context, String key) {
        return preferencesHelper.hasKey(context, key);
    }
}
