package com.nedaluof.qurany.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nedaluof on 6/14/2020.
 */
public class Reciters {
    @SerializedName("reciters")
    @Expose
    private List<Reciter> reciters;

    public List<Reciter> getReciters() {
        return reciters;
    }
}
