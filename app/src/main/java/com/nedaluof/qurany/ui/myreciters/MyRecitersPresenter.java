package com.nedaluof.qurany.ui.myreciters;

import android.util.Log;

import com.nedaluof.qurany.data.DataManager;
import com.nedaluof.qurany.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nedaluof on 7/6/2020.
 */
public class MyRecitersPresenter extends BasePresenter<MyRecitersView> {
    private static final String TAG = "MyRecitersPresenter";
    private Disposable disposable;
    private DataManager dataManager;

    @Inject
    public MyRecitersPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MyRecitersView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void loadMyReciters() {
        disposable = dataManager.getReciterRepository().getReciters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    Log.d(TAG, "getRecitersFromDb: list size success " + list.size());
                    getMvpView().showProgress(false);
                    getMvpView().showMyReciters(list);
                }, throwable -> Log.d(TAG, "Error: " + throwable.getMessage()));
    }
}
