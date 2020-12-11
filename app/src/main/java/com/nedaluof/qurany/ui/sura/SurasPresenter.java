package com.nedaluof.qurany.ui.sura;

import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.data.model.Sura;
import com.nedaluof.qurany.ui.base.BasePresenter;
import com.nedaluof.qurany.util.SurasUtil;
import com.nedaluof.qurany.util.Utility;

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

    public void loadRecitersSuras(Reciter reciterData) {
        checkViewAttached();
        getMvpView().showProgress(true);
        getMvpView().setReciterName(reciterData.getName().toUpperCase());
        getMvpView().showReciterSuras(getSuras(reciterData));
        getMvpView().showProgress(false);
    }

    private List<Sura> getSuras(Reciter reciterData) {
        // TODO: 9/27/2020 need to fill sura server link / Url here to model
        List<String> reciterSuras = Arrays.asList(reciterData.getSuras().split("\\s*,\\s*"));
        List<Sura> mainList = new ArrayList<>();
        for (int i = 0; i < reciterSuras.size(); i++) {
            String suraName;
            int item = Integer.parseInt(reciterSuras.get(i));
            String server;
            if (Utility.getLanguage().equals("_arabic")) {
                suraName = SurasUtil.arabicSurasName().get(item - 1).getName();
                server = reciterData.getServer() + "/" + SurasUtil.getSuraIndex(item - 1) + ".mp3";
                mainList.add(new Sura(item, suraName, "رواية : " + reciterData.getRewaya(), server));
            } else {
                suraName = SurasUtil.englishSurasName().get(item - 1).getName();
                server = reciterData.getServer() + "/" + SurasUtil.getSuraIndex(item - 1) + ".mp3";
                mainList.add(new Sura(item, suraName, reciterData.getRewaya(), server));
            }
        }
        return mainList;
    }


}
