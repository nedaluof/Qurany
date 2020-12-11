package com.nedaluof.qurany.data.model

import com.nedaluof.quranyplayer.model.AudioData
import kotlinx.android.parcel.Parcelize

/**
 * Created by nedaluof on 12/2/2020.
 */
@Parcelize
data class Sura(
        var id: Int = 0,
        var name: String = "",
        var rewaya: String = "",
        var suraUrl: String = ""
) : AudioData(id, name, "", suraUrl, AUDIO_TYPE) {
    constructor(id: Int = 0, name: String = "") : this(id, name, "", "")

    companion object {
        private const val AUDIO_TYPE = 3
    }
}
