package com.example.labratour.presentation.model.api

import com.example.labratour.presentation.model.data.weather.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    // 1232f729b67d9c53b651cede543be0f9
    // https://api.openweathermap.org/data/2.5/weather?q=Rehovot&appid=1232f729b67d9c53b651cede543be0f9
    // https://api.openweathermap.org/data/2.5/forecast?lat=34.797&lon=31.887&appid=1232f729b67d9c53b651cede543be0f9

    @GET("forecast?")
    suspend fun getWeather(
        @Query("lat")lat: String,
        @Query("lon")long: String,
        @Query("appid")key: String
    ): Response<WeatherResponse>
}
