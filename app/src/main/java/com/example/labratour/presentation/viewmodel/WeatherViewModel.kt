package com.example.labratour.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labratour.presentation.model.repositories.WeatherRepository
import com.example.labratour.presentation.utils.DispatcherProvider
import com.example.labratour.presentation.utils.Keys.API_KEY_WEATHER
import com.example.labratour.presentation.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.round

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel(){

    sealed class WeatherEvent {
        class Success(val resultText: String) : WeatherEvent()
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
            when (val weatherResponse = repository.getWeather(lat, long, API_KEY_WEATHER)) {
                is Resource.Error -> {
                    _weather.value = WeatherEvent.Failure(weatherResponse.message!!)
                    Log.i("Places", "WeatherViewModel: error in resource" + weatherResponse.message)
                }
                is Resource.Success -> {
                    val forecast = weatherResponse.data!!.city
                    Log.i("Places", "WeatherViewModel:" + forecast.toString())
//                    //val rate = getRateForCurrency(toCurrency, rates)
//                    if (forecast == null) {
//                        _conversion.value = CurrencyEvent.Failure("Unexpected error")
//                        Log.i("Places", "CurrencyFragment: Unexpected error rate is null")
//
//                    } else {
//                        var convertedCurrency = round(fromAmount * 100) / 100
//                        convertedCurrency *= rate as Float
//                        _conversion.value = CurrencyEvent.Success(
//                            "$fromAmount $fromCurrency = $convertedCurrency $toCurrency"
//                        )
//                        Log.i("Places", "CurrencyFragment " + "$fromAmount $fromCurrency = $convertedCurrency $toCurrency")
//
//                    }
                }
            }
        }
    }
}
