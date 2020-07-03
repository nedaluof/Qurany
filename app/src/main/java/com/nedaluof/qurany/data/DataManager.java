package com.nedaluof.qurany.data;

import com.nedaluof.qurany.data.api.ApiHelper;
import com.nedaluof.qurany.data.prefs.PreferencesHelper;
import com.nedaluof.qurany.data.room.ReciterRepository;
import com.nedaluof.qurany.data.room.ReciterSuraRepository;
import com.nedaluof.qurany.util.RxUtil;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nedaluof on 6/13/2020.
 */
@Singleton
public class DataManager {
    private Disposable disposable;
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
        RxUtil.dispose(disposable);
        disposable = reciterRepository.getReciters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    if (list.size() == 0) {
                        reciterTableIsEmpty = true;
                    }
                });
        return reciterTableIsEmpty;
    }

    private boolean reciterSuraTableIsEmpty = false;

    public boolean reciterSuraTableIsEmpty() {
        RxUtil.dispose(disposable);
        disposable = reciterSuraRepository.getReciterSuras()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    if (list.size() == 0) {
                        reciterSuraTableIsEmpty = true;
                    }
                });
        return reciterSuraTableIsEmpty;
    }

}
