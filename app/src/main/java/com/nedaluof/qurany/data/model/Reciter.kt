package com.nedaluof.qurany.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by nedaluof on 12/2/2020.
 */
@Parcelize
@Entity(tableName = "reciter")
data class Reciter(
        @SerializedName("id") @Expose
        var id: String? = null,
        @SerializedName("name")
        @Expose
        var name: String? = null,

        @SerializedName("Server")
        @Expose
        var server: String? = null,

        @SerializedName("rewaya")
        @Expose
        var rewaya: String? = null,

        @SerializedName("count")
        @Expose
        var count: String? = null,

        @SerializedName("letter")
        @Expose
        var letter: String? = null,

        @SerializedName("suras")
        @Expose
        var suras: String? = null,
        var inMyReciters: Boolean = false,
        @PrimaryKey(autoGenerate = true)
        var reciter_id: Int = 0
) : Parcelable
