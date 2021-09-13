package com.nedaluof.qurany.ui.reciters

import androidx.lifecycle.viewModelScope
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Status
import com.nedaluof.qurany.domain.repositories.RecitersRepository
import com.nedaluof.qurany.ui.base.BaseViewModel
import com.nedaluof.qurany.util.ConnectivityStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by nedaluof on 12/11/2020.
 */
@HiltViewModel
class RecitersViewModel @Inject constructor(
    private val repository: RecitersRepository,
) : BaseViewModel() {


    /**reciters list view control**/
    //reciters list from API
    val reciters = MutableStateFlow<List<Reciter>>(emptyList())

    //reciters list loading
    val loading = MutableStateFlow(false)

    //reciters list error from API
    private val _error = MutableStateFlow(Pair("", false))
    val error: StateFlow<Pair<String, Boolean>> = _error

    //connectivity status
    val connected = MutableStateFlow(true)

    /**add reciter**/
    // Todo: Future use
    private val _loadingAdd = MutableStateFlow(false)
    val loadingAdd: StateFlow<Boolean> = _loadingAdd

    //result of adding reciter to the My Reciters List
    private val _resultAdd = MutableStateFlow(false)
    val resultOfAddReciter: StateFlow<Boolean> = _resultAdd

    private fun getReciters() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getReciters()
            when (result.status) {
                Status.SUCCESS -> {
                    reciters.value = result.data!!
                    loading.value = false
                }
                Status.ERROR -> {
                    _error.value = Pair(result.message!!, false)
                    loading.value = false
                }
            }
        }
    }

    fun addReciterToMyReciters(reciter: Reciter) {
        _loadingAdd.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.addReciterToDatabase(reciter)
            when (result.status) {
                Status.SUCCESS -> {
                    _resultAdd.value = true
                    _loadingAdd.value = false
                }
                Status.ERROR -> {
                    _resultAdd.value = false
                    _loadingAdd.value = false
                }
            }
        }
    }


    private fun observeConnectivity() {
        viewModelScope.launch {
            repository.observeConnectivity().collect { connectionState ->
                when (connectionState) {
                    ConnectivityStatus.CONNECTED -> connected.value = true
                    ConnectivityStatus.NOT_CONNECTED -> connected.value = false
                }
            }
        }
    }

    init {
        observeConnectivity()
        getReciters()
    }
}
