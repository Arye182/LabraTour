package com.example.labratour.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labratour.BuildConfig.API_KEY_WEATHER
import com.example.labratour.presentation.model.data.weather.IntervalWeather
import com.example.labratour.presentation.model.repositories.WeatherRepository
import com.example.labratour.presentation.utils.DispatcherProvider
import com.example.labratour.presentation.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    sealed class WeatherEvent {
        class Success(val forecast: List<IntervalWeather>) : WeatherEvent()
        class Failure(val errorText: String) : WeatherEvent()
        object Loading : WeatherEvent()
        object Empty : WeatherEvent()
    }

    private val _weather = MutableStateFlow<WeatherEvent>(WeatherEvent.Empty)
    val weather: StateFlow<WeatherEvent> = _weather

    fun forecast(
        lat: String,
        long: String
    ) {

        if (lat == "" || long == "") {
            _weather.value = WeatherEvent.Failure("Unable To Get GPS Coordinates, Please Scroll To Refresh!")
            return
        }

        viewModelScope.launch(dispatchers.io) {
            _weather.value = WeatherEvent.Loading
            when (val weatherResponse = repository.getWeather(lat, long, API_KEY_WEATHER, "metric")) {
                is Resource.Error -> {
                    _weather.value = WeatherEvent.Failure(weatherResponse.message!!)
                    Log.i("Places", "WeatherViewModel: error in resource" + weatherResponse.message)
                }
                is Resource.Success -> {
                    val forecast = weatherResponse.data!!.list
                    _weather.value = WeatherEvent.Success(forecast)
                    //Log.i("Places", "WeatherViewModel:" + forecast.toString())
                    // take only 6 entities from forecast - current and the rest 5 intervals 3 hour jumps
                }
            }
        }
    }
}
