package com.example.weather.ui.components

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.Resource
import com.example.weather.data.models.Forecast
import com.example.weather.data.source.repository.WeatherRepository
import com.example.weather.utils.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {
    private val weatherListLiveDataPrivate = MutableLiveData<List<Forecast>?>()
    val weatherListLiveData: LiveData<List<Forecast>?> get() = weatherListLiveDataPrivate

    private val loadingLiveDataPrivate = MutableLiveData<Boolean>()
    val loadingLiveData: LiveData<Boolean> get() = loadingLiveDataPrivate

    private val noSearchFoundPrivate = MutableLiveData<Unit>()
    val noSearchFound: LiveData<Unit> get() = noSearchFoundPrivate

    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate

    fun getForecastByLocation(location: String) {
        val formatQuery = location.replace(" ", "")

        if (formatQuery.length > 2) {
            viewModelScope.launch {
                loadingLiveDataPrivate.postValue(true)
                viewModelScope.launch {
                    when (val result = weatherRepository.getForecastByLocation(formatQuery)) {
                        is Resource.Success -> {
                            loadingLiveDataPrivate.value = false
                            weatherListLiveDataPrivate.value = result.data
                        }
                        is Resource.Error -> {
                            loadingLiveDataPrivate.value = false
                            noSearchFoundPrivate.postValue(Unit)
                            showToastMessage(result.errorMsg!!)
                        }
                    }
                }
            }
        } else {
            noSearchFoundPrivate.postValue(Unit)
            showToastMessage("You need to fill in 3 or more words, please!!!")
        }
    }

    private fun showToastMessage(message: String) {
        showToastPrivate.value = SingleEvent(message)
    }
}