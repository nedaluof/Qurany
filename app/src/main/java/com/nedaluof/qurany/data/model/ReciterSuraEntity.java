package com.nedaluof.qurany.data.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by nedaluof on 6/26/2020.
 */
@Entity(tableName = "suras_table")
public class ReciterSuraEntity {

    //content id not suras
    @PrimaryKey(autoGenerate = true)
    private int id;
    //sura Data
    private int suraId;
    private String suraName;
    private String suraRewaya;
    //reciter data
    private String reciterName;
    private String reciterServer;

    //used to fill data by room itself
    public ReciterSuraEntity(int id, int suraId, String suraName, String suraRewaya, String reciterName, String reciterServer) {
        this.id = id;
        this.suraId = suraId;
        this.suraName = suraName;
        this.suraRewaya = suraRewaya;
        this.reciterName = reciterName;
        this.reciterServer = reciterServer;
    }

    //used to fill data manually
    @Ignore
    public ReciterSuraEntity(int suraId, String suraName, String suraRewaya, String reciterName, String reciterServer) {
        this.suraId = suraId;
        this.suraName = suraName;
        this.suraRewaya = suraRewaya;
        this.reciterName = reciterName;
        this.reciterServer = reciterServer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSuraId() {
        return suraId;
    }

    public void setSuraId(int suraId) {
        this.suraId = suraId;
    }

    public String getSuraName() {
        return suraName;
    }

    public void setSuraName(String suraName) {
        this.suraName = suraName;
    }

    public String getSuraRewaya() {
        return suraRewaya;
    }

    public void setSuraRewaya(String suraRewaya) {
        this.suraRewaya = suraRewaya;
    }

    public String getReciterName() {
        return reciterName;
    }

    public void setReciterName(String reciterName) {
        this.reciterName = reciterName;
    }

    public String getReciterServer() {
        return reciterServer;
    }

    public void setReciterServer(String reciterServer) {
        this.reciterServer = reciterServer;
    }
}
