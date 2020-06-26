package com.nedaluof.qurany.ui.sura;

import android.util.Log;

import com.nedaluof.qurany.data.model.Reciters;
import com.nedaluof.qurany.data.model.Suras;
import com.nedaluof.qurany.ui.base.BasePresenter;
import com.nedaluof.qurany.util.SurasUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by nedaluof on 6/16/2020.
 */
public class SurasPresenter extends BasePresenter<SurasView> {
    @Override
    public void attachView(SurasView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadRecitersSuras(Reciters.Reciter reciterData) {
        checkViewAttached();
        getMvpView().showProgress(true);
        getMvpView().setReciterName(reciterData.getName().toUpperCase());
        getMvpView().showReciterSuras(getSuras(reciterData));
        getMvpView().showProgress(false);
    }

    private List<Suras> getSuras(Reciters.Reciter reciterData) {
        List<String> reciterSuras = Arrays.asList(reciterData.getSuras().split("\\s*,\\s*"));
        List<Suras> mainList = new ArrayList<>();
        for (int i = 0; i < reciterSuras.size(); i++) {
            String suraName;
            int item = Integer.parseInt(reciterSuras.get(i));
            if (SurasUtil.getLanguage().equals("_arabic")) {
                suraName = SurasUtil.arabicSurasName().get(item - 1).getName();
                mainList.add(new Suras(item, suraName, "رواية : " + reciterData.getRewaya()));
            } else {
                suraName = SurasUtil.englishSurasName().get(item - 1).getName();
                mainList.add(new Suras(item, suraName, reciterData.getRewaya()));
            }
        }
        return mainList;
    }


}
