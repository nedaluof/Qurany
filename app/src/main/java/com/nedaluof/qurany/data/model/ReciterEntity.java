package com.nedaluof.qurany.data.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by nedaluof on 7/1/2020.
 */
//@Entity(tableName = "reciters")
public class ReciterEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String resiterId;
    private String name;
    private String server;
    private String rewaya;
    private String count;
    private String letter;
    private String suras;


    public ReciterEntity(int id, String resiterId, String name, String server, String rewaya, String count, String letter, String suras) {
        this.id = id;
        this.resiterId = resiterId;
        this.name = name;
        this.server = server;
        this.rewaya = rewaya;
        this.count = count;
        this.letter = letter;
        this.suras = suras;
    }

    @Ignore
    public ReciterEntity(String resiterId, String name, String server, String rewaya, String count, String letter, String suras) {
        this.resiterId = resiterId;
        this.name = name;
        this.server = server;
        this.rewaya = rewaya;
        this.count = count;
        this.letter = letter;
        this.suras = suras;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getServer() {
        return server;
    }

    public String getRewaya() {
        return rewaya;
    }

    public String getCount() {
        return count;
    }

    public String getLetter() {
        return letter;
    }

    public String getSuras() {
        return suras;
    }
}
