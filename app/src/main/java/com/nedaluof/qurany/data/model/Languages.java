package com.nedaluof.qurany.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nedaluof on 6/13/2020.
 */
public class Languages {

    @SerializedName("language")
    @Expose
    private List<Language> language=null;

    public List<Language> getLanguage() {
        return language;
    }


    public static class Language {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("language")
        @Expose
        private String language;
        @SerializedName("json")
        @Expose
        private String json;
        @SerializedName("sura_name")
        @Expose
        private String suraName;

        public Language(String id, String language, String json, String suraName) {
            this.id = id;
            this.language = language;
            this.json = json;
            this.suraName = suraName;
        }

        public String getId() {
            return id;
        }

        public String getLanguage() {
            return language;
        }

        public String getJson() {
            return json;
        }

        public String getSuraName() {
            return suraName;
        }
    }
}
