package com.nedaluof.qurany.data.model;

/**
 * Created by nedaluof on 6/16/2020.
 */
public class Suras {
    private int id;
    private String name;
    private String rewaya;

    public Suras(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Suras(int id, String name, String rewaya) {
        this.id = id;
        this.name = name;
        this.rewaya = rewaya;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRewaya() {
        return rewaya;
    }
}
