package com.nedaluof.qurany.ui.myreciters;

import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.ui.base.MvpView;

import java.util.List;

/**
 * Created by nedaluof on 7/6/2020.
 */
public interface MyRecitersView extends MvpView {

    void showProgress(boolean show);

    void showMyReciters(List<Reciter> list);

    void onError(String message);

    //to go to suras activity
    void onClickGetReciterData(Reciter reciterData);

    //to delete from my reciters (from db)
    void onClickDeleteFromMyReciters(Reciter reciterData);
}
