package com.nedaluof.qurany.ui.reciters

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nedaluof.qurany.data.model.Reciter
import com.nedaluof.qurany.data.model.Status
import com.nedaluof.qurany.data.repos.RecitersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by nedaluof on 12/11/2020.
 */
class RecitersViewModel @ViewModelInject constructor(
  private val repository: RecitersRepository
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
        /*liveData(Dispatchers.IO) {
            Log.d(TAG, "getReciters:liveData i here")
            emit(Resource.loading(null))
            try {
                emit(Resource.success(repository.getReciters(language)))
            } catch (exception: Exception) {
                emit(Resource.error(null, exception.message ?: "Error Occurred!"))
            }
        }.map { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    resource.data?.reciters?.map { reciter ->
                        if (dataManager.reciterHasKey(context, reciter.id)) {
                            reciter.inMyReciters = true
                        }
                    }
                    _reciters.postValue(resource.data?.reciters)
                    _loading.postValue(false)
                }
                Status.ERROR -> _error.postValue(Pair(resource.message!!, false))
                Status.LOADING -> _loading.postValue(true)
            }
        }*/
  }

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

  companion object {
    private const val TAG = "RecitersViewModel"
  }
}
