package com.example.labratour.presentation.di

import androidx.room.Room
import com.example.labratour.data.net.RestApi
import com.example.labratour.data.repositories.PlacesRepositoryImpl
import com.example.labratour.data.utils.JobExecutor
import com.example.labratour.domain.useCases.GetNearbyPlacesUseCase
import com.example.labratour.presentation.LabratourApplication
import com.example.labratour.presentation.model.cache.PlacesDatabase
import com.example.labratour.presentation.model.cache.SavedRankedPlacesDatabase
import com.example.labratour.presentation.model.dao.PlaceDao
import com.example.labratour.presentation.model.dao.SavedRankedPlaceDao
import com.example.labratour.presentation.model.repositories.PlacesRepository
import com.example.labratour.presentation.model.repositories.SavedRankedPlacesRepository
import com.example.labratour.presentation.model.repositories.UserRepository
import com.example.labratour.presentation.utils.UIThread
import com.example.labratour.presentation.viewmodel.UserHomeViewModelFactory
import com.google.android.libraries.places.api.Places

class GooglePlacesContainer(labratourApplication: LabratourApplication, userCacheRepository: UserRepository) {

    // remote
    val placesClient = Places.createClient(labratourApplication)
    val restApi = RestApi(labratourApplication)
    val placesRepository = PlacesRepositoryImpl(restApi)
    val getNearbyPlacesUseCase = GetNearbyPlacesUseCase(placesRepository, JobExecutor(), UIThread())

    // cache
    // just places
    val placeDatabase: PlacesDatabase = Room.databaseBuilder(labratourApplication, PlacesDatabase::class.java, "places_database").build()
    val placesDao: PlaceDao = placeDatabase.placeDao()
    val placeCacheRepository = PlacesRepository(placesDao)

    // saved places and ranked
    val savedRankedPlacesDatabase: SavedRankedPlacesDatabase = Room.databaseBuilder(labratourApplication, SavedRankedPlacesDatabase::class.java, "saved_ranked_places_database").build()
    val savedRankedPlaceDao: SavedRankedPlaceDao = savedRankedPlacesDatabase.savedRankedPlaceDao()
    val savedRankedPlacesRepository = SavedRankedPlacesRepository(savedRankedPlaceDao)

    // view model
    val userHomeViewModelFactory = UserHomeViewModelFactory(placesClient, getNearbyPlacesUseCase, userCacheRepository, placeCacheRepository, savedRankedPlacesRepository)
}
