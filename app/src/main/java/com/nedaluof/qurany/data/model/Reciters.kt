package com.nedaluof.qurany.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by nedaluof on 12/2/2020.
 */
data class Reciters(
  @SerializedName("reciters")
  var reciters: List<Reciter>? = null
)
