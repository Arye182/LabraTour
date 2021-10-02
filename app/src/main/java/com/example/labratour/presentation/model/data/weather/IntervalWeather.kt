package com.example.labratour.presentation.model.data.weather

data class IntervalWeather(
    val clouds: Clouds,
    val date: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Int,
    val rain: Rain,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)