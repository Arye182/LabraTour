package com.example.labratour.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.labratour.presentation.model.repositories.WeatherRepository
import com.example.labratour.presentation.utils.DispatcherProvider

class WeatherViewModelFactory(
    private val repository: WeatherRepository,
    private val dispatchers: DispatcherProvider
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(repository, dispatchers) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
