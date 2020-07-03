package com.nedaluof.qurany.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nedaluof.qurany.data.model.ReciterSuraEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by nedaluof on 6/26/2020.
 */
@Dao
public interface ReciterSuraDAO {

    /**
     * @return Flowable Stream of Entities
     */
    @Query("SELECT * FROM suras_table ORDER BY suraId")
    Flowable<List<ReciterSuraEntity>> getReciterSuras();


    /**
     * @param list insert whole suras to DB
     * @return Completable indicate process completion
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addReciterSuras(List<ReciterSuraEntity> list);

    /**
     * Delete All table content to provide
     * free table to next reciter
     */
    @Query("DELETE FROM suras_table")
    Completable deleteAllSuras();


    /*
     * Test usage only
     * */
    @Insert
    void insertToDb(ReciterSuraEntity suraEntity);

}
