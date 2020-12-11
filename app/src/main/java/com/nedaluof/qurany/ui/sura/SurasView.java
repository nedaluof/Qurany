package com.nedaluof.qurany.ui.sura;

import com.nedaluof.qurany.data.model.Sura;
import com.nedaluof.qurany.ui.base.MvpView;

import java.util.List;

/**
 * Created by nedaluof on 6/16/2020.
 */
public interface SurasView extends MvpView {
    void showProgress(boolean show);

    void setReciterName(String reciterName);

    void showReciterSuras(List<Sura> suraList);

    void showError(String message);

    void onClickPlay(int suraId);

    void onDownloadClick(int suraId);
}
