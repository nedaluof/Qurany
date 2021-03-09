package com.nedaluof.qurany.ui.reciters

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Status
import com.nedaluof.qurany.data.repos.RecitersRepository
import com.nedaluof.qurany.util.ConnectionState
import com.nedaluof.qurany.util.connectivityFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by nedaluof on 12/11/2020.
 */
class RecitersViewModel @ViewModelInject constructor(
        private val repository: RecitersRepository,
) : ViewModel() {

    val reciters = MutableLiveData<List<Reciter>>()

    private val _error = MutableLiveData<Pair<String, Boolean>>()
    val error: LiveData<Pair<String, Boolean>>
        get() = _error

    val loading = MutableLiveData<Boolean>()

    fun getReciters() {
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

    //Todo: Future use
    private val _loadingAdd = MutableLiveData<Boolean>()
    val loadingAdd: LiveData<Boolean> = _loadingAdd

    private val _resultAdd = MutableLiveData<Boolean>()
    val resultOfAddReciter: LiveData<Boolean> = _resultAdd

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

    val connected = MutableLiveData(true)

    @ExperimentalCoroutinesApi
    fun observeConnectivity(context: Context) {
        viewModelScope.launch {
            context.connectivityFlow().collect { connectionState ->
                when (connectionState) {
                    ConnectionState.CONNECTED -> connected.value = true
                    ConnectionState.NOT_CONNECTED -> connected.value = false
                }
            }
        }
    }
}
