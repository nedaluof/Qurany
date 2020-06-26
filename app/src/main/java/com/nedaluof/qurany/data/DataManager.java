package com.nedaluof.qurany.data;

import com.nedaluof.qurany.data.api.ApiHelper;
import com.nedaluof.qurany.data.prefs.PreferencesHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by nedaluof on 6/13/2020.
 */
@Singleton
public class DataManager {

    private ApiHelper apiHelper;
    private PreferencesHelper preferencesHelper;

    @Inject
    public DataManager(ApiHelper apiHelper, PreferencesHelper preferencesHelper) {
        this.apiHelper = apiHelper;
        this.preferencesHelper = preferencesHelper;
    }

    public ApiHelper getApiHelper() {
        return apiHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return preferencesHelper;
    }
}
