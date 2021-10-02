package com.example.labratour.presentation.di

import com.example.labratour.presentation.LabratourApplication
import com.example.labratour.presentation.model.api.WeatherApi
import com.example.labratour.presentation.model.repositories.WeatherRepositoryImpl
import com.example.labratour.presentation.utils.Dispatchers
import com.example.labratour.presentation.viewmodel.WeatherViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

class WeatherContainer(labratourApplication: LabratourApplication) {

    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    @Singleton
    val weatherApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    @Singleton
    val repo: WeatherRepositoryImpl = WeatherRepositoryImpl(weatherApi)

    @Singleton
    val weatherViewModelFactory = WeatherViewModelFactory(repo, Dispatchers)
}
