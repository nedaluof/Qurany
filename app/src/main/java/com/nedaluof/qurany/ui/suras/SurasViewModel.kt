package com.nedaluof.qurany.ui.suras

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Sura
import com.nedaluof.qurany.util.SuraUtil
import com.nedaluof.qurany.util.Utility
import java.util.*

/**
 * Created by nedaluof on 12/16/2020.
 */
class SurasViewModel : ViewModel() {
    //Responsibility: provide reciter name
    private val _reciterName = MutableLiveData<String>()
    val reciterName: LiveData<String>
        get() = _reciterName


    //Responsibility: provide reciter Suras to the recycler view
    private val _reciterSuras = MutableLiveData<List<Sura>>()
    val reciterSuras: LiveData<List<Sura>>
        get() = _reciterSuras

    fun loadSurasToUI(reciterData: Reciter) {
        _reciterName.value = reciterData.name
        _reciterSuras.value = getMappedReciterSuras(reciterData)
    }


    private fun getMappedReciterSuras(reciterData: Reciter): List<Sura> {
        val currentReciterSuras = listOf(*reciterData.suras!!.split(regex = "\\s*,\\s*".toRegex())
                .toTypedArray())
        val mappedReciterSuras = ArrayList<Sura>()
        for (i in currentReciterSuras.indices) {
            var suraName: String
            val item = currentReciterSuras[i].toInt()
            var server: String
            if (Utility.getLanguage() == "_arabic") {
                suraName = SuraUtil.getArabicSuraName()[item - 1].name
                server = reciterData.server + "/" + SuraUtil.getSuraIndex(item - 1) + ".mp3"
                mappedReciterSuras.add(Sura(item, suraName, "رواية : " + reciterData.rewaya, server))
            } else {
                suraName = SuraUtil.getEnglishSuraName()[item - 1].name
                server = reciterData.server + "/" + SuraUtil.getSuraIndex(item - 1) + ".mp3"
                mappedReciterSuras.add(Sura(item, suraName, reciterData.rewaya!!, server))
            }
        }
        return mappedReciterSuras
    }


}