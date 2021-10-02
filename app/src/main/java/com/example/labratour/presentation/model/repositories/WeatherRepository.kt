package com.example.labratour.presentation.model.repositories

import com.example.labratour.presentation.model.data.weather.WeatherResponse
import com.example.labratour.presentation.utils.Resource

interface WeatherRepository {

    suspend fun getWeather(lat: String, long: String, key: String, units: String): Resource<WeatherResponse>
}
