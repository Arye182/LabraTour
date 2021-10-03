package com.example.labratour.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.labratour.domain.useCases.GetNearbyPlacesAllTypesUseCase
import com.example.labratour.domain.useCases.GetNearbyPlacesByTypeUseCase
import com.example.labratour.domain.useCases.UpdateUserProfileByRateUseCase
import com.example.labratour.presentation.model.repositories.PlacesRepository
import com.example.labratour.presentation.model.repositories.SavedRankedPlacesRepository
import com.example.labratour.presentation.model.repositories.UserRepository
import com.google.android.libraries.places.api.net.PlacesClient

class UserHomeViewModelFactory(
    private val placesClient: PlacesClient,
    private val updateUseProfileByRateUseCase: UpdateUserProfileByRateUseCase,
    private val getNearbyPlacesUseCase: GetNearbyPlacesAllTypesUseCase,
    private val getNearbyPlacesByTypeUseCase: GetNearbyPlacesByTypeUseCase,
    private val userRepository: UserRepository,
    private val placeCacheRepository: PlacesRepository,
    private val savedRankedPlacesRepository: SavedRankedPlacesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserHomeViewModel::class.java)) {
            return UserHomeViewModel(placesClient, updateUseProfileByRateUseCase, getNearbyPlacesUseCase,getNearbyPlacesByTypeUseCase, userRepository, placeCacheRepository, savedRankedPlacesRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
