package com.nedaluof.qurany.data.room;

import com.nedaluof.qurany.util.Executor;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by nedaluof on 6/26/2020.
 */
public class ReciterSuraRepository {

    private ReciterSuraDAO reciterSuraDAO;
    private ReciterSuraDatabase reciterSuraDatabase;

    @Inject
    public ReciterSuraRepository(ReciterSuraDAO reciterSuraDAO, ReciterSuraDatabase reciterSuraDatabase) {
        this.reciterSuraDAO = reciterSuraDAO;
        this.reciterSuraDatabase = reciterSuraDatabase;
    }

    public void insertReciterSuras(List<ReciterSuraEntity> list) {
        Executor.IOThread(() -> reciterSuraDAO.addReciterSuras(list));
    }

    public void insertToDb(ReciterSuraEntity entity) {
        Executor.IOThread(() -> reciterSuraDAO.insertToDb(entity));
    }

    public void deleteAllSuras() {
        Executor.IOThread(() -> reciterSuraDAO.deleteAllSuras());
    }

    public List<ReciterSuraEntity> getReciterSuras() {
        return reciterSuraDAO.getReciterSuras();
    }
}
