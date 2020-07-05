package com.nedaluof.qurany.data.room;

import com.nedaluof.qurany.data.model.ReciterSuraEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by nedaluof on 6/26/2020.
 */
public class ReciterSuraRepository {

    private ReciterSuraDAO reciterSuraDAO;

    @Inject
    public ReciterSuraRepository(ReciterSuraDAO reciterSuraDAO) {
        this.reciterSuraDAO = reciterSuraDAO;
    }

    public Completable insertReciterSuras(List<ReciterSuraEntity> list) {
        return reciterSuraDAO.addReciterSuras(list);
    }

    public Completable deleteAllSuras() {
        return reciterSuraDAO.deleteAllSuras();
    }

    public Flowable<List<ReciterSuraEntity>> getReciterSuras() {
        return reciterSuraDAO.getReciterSuras();
    }


}
