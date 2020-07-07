package com.nedaluof.qurany.ui.reciter;

import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.ui.base.MvpView;

import java.util.List;

/**
 * Created by nedaluof on 6/14/2020.
 */
public interface ReciterView extends MvpView {
    void showProgress(boolean show);

    void showReciters(List<Reciter> data);

    void onError(String message);

    void onClickGetReciterData(Reciter reciterData);

    void onClickAddToMyReciters(Reciter reciterData);

    void onReciterAddedToMyRecitersSuccess();

    void onReciterAlreadyAddedToMyReciters();

    //to delete from my reciters (from db)
    void onClickDeleteFromMyReciters(Reciter reciterData);
}
