package com.nedaluof.qurany.ui.myreciters;

import android.content.Context;
import android.util.Log;

import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.nedaluof.qurany.data.DataManager;
import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.ui.base.BasePresenter;
import com.nedaluof.qurany.util.RxUtil;

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
    private Context context;

    @Inject
    public MyRecitersPresenter(DataManager dataManager, Context context) {
        this.dataManager = dataManager;
        this.context = context;
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
        checkViewAttached();
        getMvpView().showProgress(true);
        RxUtil.dispose(disposable);
        disposable = dataManager.getReciterRepository().getReciters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    Log.d(TAG, "getRecitersFromDb: list size success " + list.size());
                    getMvpView().showMyReciters(list);
                    getMvpView().showProgress(false);
                }, throwable -> Log.d(TAG, "Error: " + throwable.getMessage()));
    }

    public void deleteFromMyReciters(Reciter reciter) {
        RxUtil.dispose(disposable);
        disposable = dataManager.getReciterRepository()
                .deleteReciter(reciter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    dataManager.getPreferencesHelper().removeFromPrefs(context, reciter.getId());
                    getMvpView().onReciterDeletedFromMyReciters();
                });

    }
}