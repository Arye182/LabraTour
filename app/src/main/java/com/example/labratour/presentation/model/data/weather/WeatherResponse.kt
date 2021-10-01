package com.example.labratour.presentation.model.data.weather

data class WeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<IntervalWeather>,
    val message: Int
)