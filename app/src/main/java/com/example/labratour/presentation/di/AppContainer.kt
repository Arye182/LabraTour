package com.example.labratour.presentation.di

import androidx.room.Room
import com.example.labratour.presentation.LabratourApplication
import com.example.labratour.presentation.model.cache.UsersDatabase
import com.example.labratour.presentation.model.dao.UserDao
import com.example.labratour.presentation.model.repositories.UserRepository
import javax.inject.Singleton

class AppContainer(labratourApplication: LabratourApplication) {
    // database for users (cache)
    @Singleton
    val usersDatabase: UsersDatabase = Room.databaseBuilder(labratourApplication, UsersDatabase::class.java, "users_database").build()
    @Singleton
    val userDao: UserDao = usersDatabase.userDao()
    @Singleton
    val userCacheRepository = UserRepository(userDao)

    // containers
    @Singleton
    val firebaseContainer = FirebaseContainer(labratourApplication, userCacheRepository)
    @Singleton
    val locationContainer = LocationContainer(labratourApplication)
    @Singleton
    val googlePlacesContainer = GooglePlacesContainer(labratourApplication, userCacheRepository)
    @Singleton
    val weatherContainer = WeatherContainer(labratourApplication)
    @Singleton
    val currencyContainer = CurrencyContainer(labratourApplication)
}
