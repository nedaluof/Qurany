package com.nedaluof.qurany.ui.reciters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Status
import com.nedaluof.qurany.domain.repositories.RecitersRepository
import com.nedaluof.qurany.ui.base.BaseViewModel
import com.nedaluof.qurany.util.ConnectivityStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    val reciters = MutableLiveData<List<Reciter>>()

    //reciters list loading
    val loading = MutableLiveData<Boolean>()

    //reciters list error from API
    private val _error = MutableLiveData<Pair<String, Boolean>>()
    val error: LiveData<Pair<String, Boolean>> = _error

    //connectivity status
    val connected = MutableLiveData(true)

    /**add reciter**/
    // Todo: Future use
    private val _loadingAdd = MutableLiveData<Boolean>()
    val loadingAdd: LiveData<Boolean> = _loadingAdd

    //result of adding reciter to the My Reciters List
    private val _resultAdd = MutableLiveData<Boolean>()
    val resultOfAddReciter: LiveData<Boolean> = _resultAdd

    private fun getReciters() {
        loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getReciters()
            when (result.status) {
                Status.SUCCESS -> {
                    reciters.postValue(result.data!!)
                    loading.postValue(false)
                }
                Status.ERROR -> {
                    _error.postValue(Pair(result.message!!, false))
                    loading.postValue(false)
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
                    _resultAdd.postValue(true)
                    _loadingAdd.postValue(false)
                }
                Status.ERROR -> {
                    _resultAdd.postValue(false)
                    _loadingAdd.postValue(false)
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
