package com.nedaluof.qurany.ui.base;

/**
 * Created by nedaluof on 6/13/2020.
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
