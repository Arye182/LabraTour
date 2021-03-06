package com.example.labratour.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.labratour.presentation.model.data.LocationLiveData

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private val locationData = LocationLiveData(application)
    fun getLocationData() = locationData
    var prevLong: Double = 0.0
    var prevLat: Double = 0.0
    var started: Boolean = false
}
