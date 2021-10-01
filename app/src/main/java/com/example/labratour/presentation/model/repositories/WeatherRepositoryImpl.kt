package com.example.labratour.presentation.model.repositories

import android.util.Log
import com.example.labratour.presentation.model.api.WeatherApi
import com.example.labratour.presentation.model.data.weather.WeatherResponse
import com.example.labratour.presentation.utils.Keys.API_KEY_WEATHER
import com.example.labratour.presentation.utils.Resource
import java.lang.Exception

class WeatherRepositoryImpl(private val api: WeatherApi) : WeatherRepository {
    override suspend fun getWeather(lat: String, long: String, key: String): Resource<WeatherResponse> {
        return try {
            val response = api.getWeather(lat, long, key)
            Log.i("Places", response.code().toString())
            Log.i("Places", response.isSuccessful.toString())
            Log.i("Places", response.toString())
            val result = response.body()
            if (response.isSuccessful && result != null) {
                if (response.code().toString() == "403") {
                    Log.i("Places", "403")
                    Resource.Error(response.message())
                } else {
                    Log.i("Places", "Weather Repository " + response.message())
                    Resource.Success(result)
                }
            } else {
                Log.i("Places", "Weather Repository Request Not Success " + response.message().toString())
                Resource.Error("Access Restricted - Your current Subscription Plan does not support HTTPS Encryption.")
            }
        } catch (e: Exception) {
            Log.i("Places", "Weather Repository " + e.message)
            Resource.Error(e.message ?: "An Error Currency Api Occured")
        }
    }
}
