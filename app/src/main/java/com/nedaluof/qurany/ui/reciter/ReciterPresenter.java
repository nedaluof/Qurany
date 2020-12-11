package com.nedaluof.qurany.ui.reciter;

import android.content.Context;
import android.util.Log;

import com.nedaluof.qurany.data.DataManager;
import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.ui.base.BasePresenter;
import com.nedaluof.qurany.util.RxUtil;
import com.nedaluof.qurany.util.Utility;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nedaluof on 6/14/2020.
 */
public class ReciterPresenter extends BasePresenter<ReciterView> {
    private static final String TAG = "ReciterPresenter";
    private Disposable disposable;
    private final DataManager dataManager;
    private final Context context;

    @Inject
    public ReciterPresenter(DataManager dataManager, @ApplicationContext Context context) {
        this.dataManager = dataManager;
        this.context = context;
    }


    @Override
    public void attachView(ReciterView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void loadNoInternetConnectionView() {
        getMvpView().onNoInternetConnectionProvided();
    }

    public void loadReciters() {
        checkViewAttached();
        getMvpView().showProgress(true);
        RxUtil.dispose(disposable);
        disposable = dataManager.getApiHelper().getService().getReciters(Utility.getLanguage())
                .subscribeOn(Schedulers.io())
                .filter(reciters -> {
                    for (Reciter reciter : reciters.getReciters()) {
                        if (dataManager.reciterHasKey(context, reciter.getId())) {
                            reciter.setInMyReciters(true);
                        } else {
                            reciter.setInMyReciters(false);
                        }
                    }
                    return true;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reciters -> {
                            getMvpView().showReciters(reciters.getReciters());
                            getMvpView().showProgress(false);
                        }
                        , throwable ->

                        {
                            getMvpView().showProgress(false);
                            getMvpView().onError(throwable.getMessage());
                        }, () ->

                        {
                            Log.d(TAG, "loadReciters : onComplete Reciters Presenter");
                        });
    }

    public void addReciterToMyReciters(@NotNull Reciter reciter) {
        if (!dataManager.reciterHasKey(context, reciter.getName())) {
            //adding reciter to database
            RxUtil.dispose(disposable);
            disposable = dataManager.getReciterRepository().insertReciter(reciter)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Log.d(TAG, "insert to MyReciters : onComplete: ");
                        //inform user that reciter added to My Reciters List
                        //adding reciter to preferences
                        dataManager.getPreferencesHelper()
                                .saveToPrefs(context, reciter.getId(), reciter.getId());
                        getMvpView().onReciterAddedToMyRecitersSuccess();
                    });
        }
    }
}
