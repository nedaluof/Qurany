package com.nedaluof.qurany.ui.reciter;

import android.util.Log;

import com.nedaluof.qurany.data.DataManager;
import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.data.model.ReciterEntity;
import com.nedaluof.qurany.data.model.Reciters;
import com.nedaluof.qurany.ui.base.BasePresenter;
import com.nedaluof.qurany.util.RxUtil;
import com.nedaluof.qurany.util.SurasUtil;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nedaluof on 6/14/2020.
 */
public class ReciterPresenter extends BasePresenter<ReciterView> {
    private static final String TAG = "ReciterPresenter";
    private Disposable disposable;
    private DataManager dataManager;

    @Inject
    public ReciterPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
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

    public void loadReciters() {
        String language = SurasUtil.getLanguage();
        checkViewAttached();
        getMvpView().showProgress(true);
        if (dataManager.reciterTableIsEmpty()) {
            RxUtil.dispose(disposable);
            dataManager.getApiHelper().getService().getReciters(language)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Reciters>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onNext(Reciters reciters) {

                            if (reciters == null) {
                                getMvpView().showError("null object from server");
                                getMvpView().showProgress(false);
                            }

                            for (int i = 0; i < reciters.getReciters().size(); i++) {
                                disposable = dataManager.getReciterRepository().insertReciter(new Reciter(
                                        reciters.getReciters().get(i).getId(),
                                        reciters.getReciters().get(i).getName(),
                                        reciters.getReciters().get(i).getServer(),
                                        reciters.getReciters().get(i).getRewaya(),
                                        reciters.getReciters().get(i).getCount(),
                                        reciters.getReciters().get(i).getLetter(),
                                        reciters.getReciters().get(i).getSuras()
                                )).subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(() -> Log.d(TAG, "inserted to db successfully "));
                            }

                            getMvpView().showProgress(false);
                            getReciterListFromDb();
                        }


                        @Override
                        public void onError(Throwable e) {
                            getMvpView().showProgress(false);
                            getMvpView().showError(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: Reciters Observable");
                        }
                    });
        } else {
            getReciterListFromDb();
        }

    }

    private void getReciterListFromDb() {
        Log.d(TAG, "getReciterListFromDb:");
        RxUtil.dispose(disposable);
        dataManager.getReciterRepository().getReciters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<List<Reciter>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                    }

                    @Override
                    public void onNext(List<Reciter> list) {
                        Log.d(TAG, "onNext: reciter size" + list.size());
                        getMvpView().showProgress(false);
                        getMvpView().showReciters(list);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void add() {

    }
}
