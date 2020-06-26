package com.nedaluof.qurany.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nedaluof on 6/14/2020.
 */
public class Reciters implements Parcelable{
    @SerializedName("reciters")
    @Expose
    private List<Reciter> reciters;

    protected Reciters(Parcel in) {
        reciters = in.createTypedArrayList(Reciter.CREATOR);
    }

    public static final Creator<Reciters> CREATOR = new Creator<Reciters>() {
        @Override
        public Reciters createFromParcel(Parcel in) {
            return new Reciters(in);
        }

        @Override
        public Reciters[] newArray(int size) {
            return new Reciters[size];
        }
    };

    public List<Reciter> getReciters() {
        return reciters;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(reciters);
    }

    public static class Reciter implements Parcelable {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("Server")
        @Expose
        private String server;
        @SerializedName("rewaya")
        @Expose
        private String rewaya;
        @SerializedName("count")
        @Expose
        private String count;
        @SerializedName("letter")
        @Expose
        private String letter;
        @SerializedName("suras")
        @Expose
        private String suras;

        public Reciter(String id, String name, String server, String rewaya, String count, String letter, String suras) {
            this.id = id;
            this.name = name;
            this.server = server;
            this.rewaya = rewaya;
            this.count = count;
            this.letter = letter;
            this.suras = suras;
        }

        protected Reciter(Parcel in) {
            id = in.readString();
            name = in.readString();
            server = in.readString();
            rewaya = in.readString();
            count = in.readString();
            letter = in.readString();
            suras = in.readString();
        }

        public static final Creator<Reciter> CREATOR = new Creator<Reciter>() {
            @Override
            public Reciter createFromParcel(Parcel in) {
                return new Reciter(in);
            }

            @Override
            public Reciter[] newArray(int size) {
                return new Reciter[size];
            }
        };

        public String getId() {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeString(server);
            dest.writeString(rewaya);
            dest.writeString(count);
            dest.writeString(letter);
            dest.writeString(suras);
        }
    }
}
