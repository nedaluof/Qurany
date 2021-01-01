package com.nedaluof.qurany.ui.myreciters

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Status
import com.nedaluof.qurany.data.repos.MyRecitersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by nedaluof on 12/12/2020.
 */
class MyRecitersViewModel @ViewModelInject constructor(
  private val repository: MyRecitersRepository
) : ViewModel() {

  private val _reciters = MutableLiveData<List<Reciter>>()
  val reciters: LiveData<List<Reciter>>
    get() = _reciters

  private val _error = MutableLiveData<String>()
  val error: LiveData<String>
    get() = _error

  private val _loading = MutableLiveData(true)
  val loading: LiveData<Boolean>
    get() = _loading

  fun getMyReciters() {
    viewModelScope.launch(Dispatchers.IO) {
      repository.getMyReciters
        .catch { cause -> _error.postValue(cause.message!!) }
        .collect {
          _reciters.postValue(it)
          _loading.postValue(false)
        }
    }
  }

  private val _resultOfDeletion = MutableLiveData<Boolean>()
  val resultOfDeleteReciter: LiveData<Boolean>
    get() = _resultOfDeletion

  fun deleteFromMyReciters(reciter: Reciter) {
    viewModelScope.launch(Dispatchers.IO) {
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
