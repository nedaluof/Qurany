package com.nedaluof.qurany.ui.suras

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Sura
import com.nedaluof.qurany.util.SuraUtil
import com.nedaluof.qurany.util.Utility
import com.nedaluof.qurany.util.Utility.checkIfSuraExist
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*

/**
 * Created by nedaluof on 12/16/2020.
 */
class SurasViewModel @ViewModelInject constructor(
        @ApplicationContext
        private val context: Context,
) : ViewModel() {
    // Responsibility: provide reciter name
    private val _reciterName = MutableLiveData<String>()
    val reciterName: LiveData<String>
        get() = _reciterName

    // Responsibility: provide reciter Suras to the recycler view
    private val _reciterSuras = MutableLiveData<List<Sura>>()
    val reciterSuras: LiveData<List<Sura>>
        get() = _reciterSuras

    // Responsibility: provide reciter Suras to the recycler view
    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean>
        get() = _loading

    fun loadSurasToUI(reciterData: Reciter) {
        _reciterName.value = reciterData.name
        _reciterSuras.value = getMappedReciterSuras(reciterData)
        _loading.value = false
    }

    /**
     * Todo issue:
     *  only show language as user device and handle operations of save and play
     *  in english language
     *
     *  */
    private fun getMappedReciterSuras(reciterData: Reciter): List<Sura> {
        val currentReciterSuras = listOf(
                *reciterData.suras!!.split(regex = "\\s*,\\s*".toRegex())
                        .toTypedArray()
        )
        val mappedReciterSuras = ArrayList<Sura>()
        for (i in currentReciterSuras.indices) {
            val currentSura = currentReciterSuras[i].toInt()
            if (Utility.getLanguage() == "_arabic") {
                val suraName = SuraUtil.getArabicSuraName()[currentSura - 1].suraName
                val suraUrl = reciterData.server + "/" + SuraUtil.getSuraIndex(currentSura) + ".mp3"
                val subPath = "/Qurany/${reciterData.name}/${SuraUtil.getSuraName(currentSura)}.mp3"
                mappedReciterSuras.add(
                        Sura(
                                currentSura,
                                suraName,
                                "رواية : " + reciterData.rewaya,
                                suraUrl,
                                reciterData.name!!,
                                SuraUtil.getPlayerTitle(
                                        currentSura,
                                        reciterData
                                                .name!!
                                ),
                                subPath
                        )
                )
            } else {
                val suraName = SuraUtil.getEnglishSuraName()[currentSura - 1].suraName
                val suraUrl = reciterData.server + "/" + SuraUtil.getSuraIndex(currentSura) + ".mp3"
                val subPath = "/Qurany/${reciterData.name}/${SuraUtil.getSuraName(currentSura)}.mp3"
                mappedReciterSuras.add(
                        Sura(
                                currentSura, suraName, reciterData.rewaya!!, suraUrl,
                                reciterData.name!!, SuraUtil.getPlayerTitle(currentSura, reciterData.name!!), subPath
                        )
                )
            }
        }
        return mappedReciterSuras
    }

    private val _suraExist = MutableLiveData<Boolean>()
    val isSuraExist: LiveData<Boolean>
        get() = _suraExist

    fun checkSuraExist(sura: Sura) {
        _suraExist.value = context.checkIfSuraExist(sura.suraSubPath)
    }


}
