package com.nedaluof.qurany.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by nedaluof on 12/2/2020.
 */
@Parcelize
data class Sura(
  var id: Int = 0,
  var suraName: String = "",
  var rewaya: String = "",
  var suraUrl: String = "",
  var reciterName: String = "",
  var playerTitle: String = "",
  var suraSubPath: String = "",
  var playingType: Int = 0,
) : Parcelable
