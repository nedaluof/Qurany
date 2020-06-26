package com.nedaluof.qurany.data.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Created by nedaluof on 6/26/2020.
 */
@Dao
public interface ReciterSuraDAO {

    /**
     * @return ArrayList of ReciterSuraEntity
     */
    @Query("SELECT * FROM suras_table ORDER BY suraId")
    List<ReciterSuraEntity> getReciterSuras();

    /**
     * @param list insert whole suras to DB
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addReciterSuras(List<ReciterSuraEntity> list);

    /**
     * Delete All table content to provide
     * free table to next reciter
     */
    @Query("DELETE FROM suras_table")
    void deleteAllSuras();


    /*
     * Test usage only
     * */
    @Insert
    void insertToDb(ReciterSuraEntity suraEntity);

}
