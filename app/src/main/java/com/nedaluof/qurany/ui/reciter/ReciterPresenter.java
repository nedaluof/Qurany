package com.nedaluof.qurany.ui.reciter;

import android.util.Log;

import com.nedaluof.qurany.data.DataManager;
import com.nedaluof.qurany.data.model.Reciters;
import com.nedaluof.qurany.ui.base.BasePresenter;
import com.nedaluof.qurany.util.RxUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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

    public void loadReciters(String language) {
        checkViewAttached();
        getMvpView().showProgress(true);
        RxUtil.dispose(disposable);
        Observable<Reciters> observable = dataManager.getApiHelper().getService().getReciters(language);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Reciters>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Reciters reciters) {

                        if (reciters == null) {
                            getMvpView().showError("null object");
                            getMvpView().showProgress(false);
                        }

                        List<Reciters.Reciter> model = new ArrayList<>();
                        for (int i = 0; i < reciters.getReciters().size(); i++) {
                            model.add(new Reciters.Reciter(
                                    reciters.getReciters().get(i).getId(),
                                    reciters.getReciters().get(i).getName(),
                                    reciters.getReciters().get(i).getServer(),
                                    reciters.getReciters().get(i).getRewaya(),
                                    reciters.getReciters().get(i).getCount(),
                                    reciters.getReciters().get(i).getLetter(),
                                    reciters.getReciters().get(i).getSuras()
                            ));
                        }

                        getMvpView().showProgress(false);
                        getMvpView().showReciters(model);

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
    }
}
