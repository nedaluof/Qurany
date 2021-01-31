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
@ExperimentalCoroutinesApi
class RecitersViewModel @ViewModelInject constructor(
        private val repository: RecitersRepository,
) : ViewModel() {

    private val _reciters = MutableLiveData<List<Reciter>>()
    val reciters: LiveData<List<Reciter>>
        get() = _reciters

    private val _error = MutableLiveData<Pair<String, Boolean>>()
    val error: LiveData<Pair<String, Boolean>>
        get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    fun getReciters(language: String) {
        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getReciters(language)
            when (result.status) {
                Status.SUCCESS -> {
                    _reciters.postValue(result.data!!)
                    _loading.postValue(false)
                }
                Status.ERROR -> {
                    _error.postValue(Pair(result.message!!, false))
                    _loading.postValue(false)
                }
            }
        }
    }

    //Todo: Future use
    private val _loadingAdd = MutableLiveData<Boolean>()
    val loadingAdd: LiveData<Boolean>
        get() = _loadingAdd

    private val _resultAdd = MutableLiveData<Boolean>()
    val resultOfAddReciter: LiveData<Boolean>
        get() = _resultAdd

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

    private val _connected = MutableLiveData<Boolean>()
    val connected: LiveData<Boolean>
        get() = _connected

    fun observeConnectivity(context: Context) {
        viewModelScope.launch {
            context.connectivityFlow().collect { connectionState ->
                when (connectionState) {
                    ConnectionState.CONNECTED -> _connected.value = true
                    ConnectionState.NOT_CONNECTED -> _connected.value = false
                }

            }
        }
    }
}
