package com.nedaluof.qurany.ui.reciter;

import com.nedaluof.qurany.data.model.Reciters;
import com.nedaluof.qurany.ui.base.MvpView;

import java.util.List;

/**
 * Created by nedaluof on 6/14/2020.
 */
public interface ReciterView extends MvpView {
    void showProgress(boolean show);

    void showReciters(List<Reciters.Reciter> data);

    void showError(String message);

    void onClickGetReciterData(Reciters.Reciter reciterData);
}
