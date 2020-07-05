package com.nedaluof.qurany.ui.reciter;

import android.util.Log;

import com.nedaluof.qurany.data.DataManager;
import com.nedaluof.qurany.data.model.Reciters;
import com.nedaluof.qurany.ui.base.BasePresenter;
import com.nedaluof.qurany.util.RxUtil;
import com.nedaluof.qurany.util.Utility;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nedaluof on 6/14/2020.
 */
public class ReciterPresenter extends BasePresenter<ReciterView> {
    private static final String TAG = "ReciterPresenter";
    private Disposable disposable;
    private DataManager dataManager;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

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
        checkViewAttached();
        getMvpView().showProgress(true);
        RxUtil.dispose(disposable);
        dataManager.getApiHelper().getService().getReciters(Utility.getLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Reciters>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Reciters reciters) {
                        getMvpView().showProgress(false);
                        getMvpView().showReciters(reciters.getReciters());
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showProgress(false);
                        getMvpView().showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "loadReciters : onComplete Reciters Presenter");
                    }
                });
    }

    /*public void loadReciters() {
        checkViewAttached();
        getMvpView().showProgress(true);
        //Todo network check
       if (!dataManager.reciterTableIsEmpty()) {
            deleteAllRecitersInDb();
            dataManager.getApiHelper().getService().getReciters(SurasUtil.getLanguage())
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
                            } else {
                                compositeDisposable.add(dataManager.getReciterRepository()
                                        .insertReciters(reciters.getReciters())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(() -> Log.d(TAG, "All inserted")));
                            }
                        }


                        @Override
                        public void onError(Throwable e) {
                            getMvpView().showProgress(false);
                            getMvpView().showError(e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: Reciters Observable");
                            getRecitersFromDb();
                        }
                    });
        } else {
            getRecitersFromDb();
        }

       //getRecitersFromDb();

    }*/

    void getRecitersFromDb() {
        disposable = dataManager.getReciterRepository().getReciters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    Log.d(TAG, "geetRecitersFromDb: liist size success " + list.size());
                    getMvpView().showProgress(false);
                    getMvpView().showReciters(list);
                }, throwable -> Log.d(TAG, "Error: " + throwable.getMessage()));
    }

    void deleteAllRecitersInDb() {
        disposable = dataManager.getReciterRepository().deleteAllReciters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> Log.d(TAG, "deleteAllRecitersInDb : success "));
    }
}
