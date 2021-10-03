package com.example.labratour.presentation.di

import androidx.room.Room
import com.example.labratour.data.net.NearbyPlacesAllTypes
import com.example.labratour.data.repositories.AtributesRepositoryImpl
import com.example.labratour.data.repositories.NearbyPlacesRepositoryImpl
import com.example.labratour.data.repositories.PlacesRepositoryImpl
import com.example.labratour.data.repositories.RatingsRepositoryImpl
import com.example.labratour.data.utils.JobExecutor
import com.example.labratour.domain.useCases.GetNearbyPlacesAllTypesUseCase
import com.example.labratour.domain.useCases.GetNearbyPlacesByTypeUseCase
import com.example.labratour.domain.useCases.UpdateUserProfileByRateUseCase
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Singleton

class GooglePlacesContainer(labratourApplication: LabratourApplication, userCacheRepository: UserRepository) {

    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseDatabase = FirebaseDatabase.getInstance()

    // remote near by use case
    @Singleton
    val placesClient = Places.createClient(labratourApplication)
    @Singleton
    val restApi = NearbyPlacesAllTypes(labratourApplication)
    @Singleton
    val placesRepository = NearbyPlacesRepositoryImpl(restApi)
    @Singleton
    val getNearbyPlacesUseCase =
        GetNearbyPlacesAllTypesUseCase(
            placesRepository,
            JobExecutor(),
            UIThread()
        )

    // category places usecase
    @Singleton
    val getNearbyPlacesByTypeUseCase = GetNearbyPlacesByTypeUseCase(placesRepository, JobExecutor(), UIThread())

    // rank place usecase
    @Singleton
    val rankPlacesRepository = PlacesRepositoryImpl(restApi)
    @Singleton
    val atrRepository = AtributesRepositoryImpl(firebaseAuth, firebaseDatabase)
    @Singleton
    val rankRepository = RatingsRepositoryImpl(rankPlacesRepository, atrRepository)
    @Singleton
    val updateUseProfileByRateUseCase = UpdateUserProfileByRateUseCase(JobExecutor(), UIThread(), rankRepository)

    // cache
    // just places
    @Singleton
    val placeDatabase: PlacesDatabase = Room.databaseBuilder(labratourApplication, PlacesDatabase::class.java, "places_database").build()
    @Singleton
    val placesDao: PlaceDao = placeDatabase.placeDao()
    @Singleton
    val placeCacheRepository = PlacesRepository(placesDao)

    // saved places and ranked
    @Singleton
    val savedRankedPlacesDatabase: SavedRankedPlacesDatabase = Room.databaseBuilder(labratourApplication, SavedRankedPlacesDatabase::class.java, "saved_ranked_places_database").build()
    @Singleton
    val savedRankedPlaceDao: SavedRankedPlaceDao = savedRankedPlacesDatabase.savedRankedPlaceDao()
    @Singleton
    val savedRankedPlacesRepository = SavedRankedPlacesRepository(savedRankedPlaceDao)

    // view model
    @Singleton
    val userHomeViewModelFactory = UserHomeViewModelFactory(placesClient, updateUseProfileByRateUseCase, getNearbyPlacesUseCase, getNearbyPlacesByTypeUseCase, userCacheRepository, placeCacheRepository, savedRankedPlacesRepository)
}
