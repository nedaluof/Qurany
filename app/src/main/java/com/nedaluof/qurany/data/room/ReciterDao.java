package com.nedaluof.qurany.data.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.nedaluof.qurany.data.model.Reciter;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by nedaluof on 7/1/2020.
 */
@Dao
public interface ReciterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertReciters(List<Reciter> list);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertReciter(Reciter reciter);

    @Query("select * from reciter order by name")
    Flowable<List<Reciter>> getReciters();

    //to check table size
    @Query("select * from reciter")
    List<Reciter> getRecitersForCheck();

    //to check records number
    @Query("SELECT COUNT(*) FROM reciter")
    int getRecitersRecordsNumber();

    @Query("Delete from reciter")
    Completable deleteAllReciters();

    @Delete
    Completable deleteReciter(Reciter reciter);
}
