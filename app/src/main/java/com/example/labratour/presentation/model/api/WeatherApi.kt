package com.example.labratour.presentation.model.api

import com.example.labratour.presentation.model.data.weather.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast?")
    suspend fun getWeather(
        @Query("lat")lat: String,
        @Query("lon")long: String,
        @Query("appid")key: String,
        @Query("units")units: String
    ): Response<WeatherResponse>
}
