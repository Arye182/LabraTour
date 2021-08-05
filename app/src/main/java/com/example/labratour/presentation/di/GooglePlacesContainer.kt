package com.example.labratour.presentation.di

import com.example.labratour.data.net.RestApi
import com.example.labratour.data.repositories.PlacesRepositoryImpl
import com.example.labratour.data.utils.JobExecutor
import com.example.labratour.domain.useCases.GetNearbyPlacesUseCase
import com.example.labratour.presentation.LabratourApplication
import com.example.labratour.presentation.utils.UIThread
import com.example.labratour.presentation.viewmodel.UserHomeViewModelFactory
import com.google.android.libraries.places.api.Places

class GooglePlacesContainer(labratourApplication: LabratourApplication) {

    val placesClient = Places.createClient(labratourApplication)
    val restApi = RestApi(labratourApplication)
    val placesRepository = PlacesRepositoryImpl(restApi)
    val getNearbyPlacesUseCase = GetNearbyPlacesUseCase(placesRepository, JobExecutor(), UIThread())
    val userHomeViewModelFactory = UserHomeViewModelFactory(placesClient, getNearbyPlacesUseCase)
}
