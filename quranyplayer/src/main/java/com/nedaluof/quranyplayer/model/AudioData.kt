package com.nedaluof.quranyplayer.model

import android.os.Parcelable

abstract class AudioData(
        var audioId: Int = 0,
        var title: String? = "",
        var artist: String? = "",
        var source: String? = "",//also is Download Path
        var audioType: Int = 0,
) : Parcelable