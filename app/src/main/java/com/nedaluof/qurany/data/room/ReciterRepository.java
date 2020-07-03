package com.nedaluof.qurany.data.room;

import com.nedaluof.qurany.data.model.Reciter;
import com.nedaluof.qurany.data.model.ReciterEntity;
import com.nedaluof.qurany.data.model.Reciters;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by nedaluof on 7/1/2020.
 */
public class ReciterRepository {
    private ReciterDao reciterDao;

    @Inject
    public ReciterRepository(ReciterDao reciterDao) {
        this.reciterDao = reciterDao;
    }

    public Completable insertReciter(Reciter reciter) {
        return reciterDao.insertReciter(reciter);
    }

    public Completable insertReciters(List<Reciter> list) {
        return reciterDao.insertReciters(list);
    }

    public Flowable<List<Reciter>> getReciters() {
        return reciterDao.getReciters();
    }

    public Completable deleteAllReciters() {
        return reciterDao.deleteAllReciters();
    }

}
