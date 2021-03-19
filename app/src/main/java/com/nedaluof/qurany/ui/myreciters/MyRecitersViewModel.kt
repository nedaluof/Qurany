package com.nedaluof.qurany.ui.myreciters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Status
import com.nedaluof.qurany.data.repos.MyRecitersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by nedaluof on 12/12/2020.
 */
@HiltViewModel
class MyRecitersViewModel @Inject constructor(
        private val repository: MyRecitersRepository,
) : ViewModel() {

    val reciters = MutableLiveData<List<Reciter>>()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    val loading = MutableLiveData(true)

    val noReciters = MutableLiveData<Boolean>()

    fun getMyReciters() {
        viewModelScope.launch {
            repository.getMyReciters
                    .catch { cause -> _error.postValue(cause.message!!) }
                    .collect {
                        if (it.isNotEmpty()) {
                            loading.value = false
                            reciters.value = it
                        } else {
                            reciters.value = emptyList()
                            loading.value = false
                            noReciters.postValue(true)
                        }
                    }
        }
    }

    private val _resultOfDeletion = MutableLiveData<Boolean>()
    val resultOfDeleteReciter: LiveData<Boolean>
        get() = _resultOfDeletion

    fun deleteFromMyReciters(reciter: Reciter) {
        viewModelScope.launch {
            val result = repository.deleteFromMyReciters(reciter)
            when (result.status) {
                Status.SUCCESS -> {
                    _resultOfDeletion.postValue(true)
                }
                Status.ERROR -> {
                    _resultOfDeletion.postValue(false)
                }
            }
        }
    }
}
