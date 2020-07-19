package com.nedaluof.qurany.data.room;

import com.nedaluof.qurany.data.model.Reciter;

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

    public Completable deleteReciter(Reciter reciter) {
        return reciterDao.deleteReciter(reciter);
    }

    public void deleteReciterTest(Reciter reciter) {
        reciterDao.deleteReciterTest(reciter);
    }

    public int reciterListCheck() {
        return reciterDao.getRecitersForCheck().size();
    }

    public int recitersCountinTable() {
        return reciterDao.getRecitersRecordsNumber();
    }

}
