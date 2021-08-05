package com.example.labratour.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.labratour.domain.useCases.GetNearbyPlacesUseCase
import com.google.android.libraries.places.api.net.PlacesClient

class UserHomeViewModelFactory(private val placesClient: PlacesClient, private val getNearbyPlacesUseCase: GetNearbyPlacesUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserHomeViewModel::class.java)) {
            return UserHomeViewModel(placesClient, getNearbyPlacesUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
