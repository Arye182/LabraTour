package com.example.labratour.presentation.di

import androidx.room.Room
import com.example.labratour.presentation.LabratourApplication
import com.example.labratour.presentation.model.cache.UsersDatabase
import com.example.labratour.presentation.model.dao.UserDao
import com.example.labratour.presentation.model.repositories.UserRepository

class AppContainer(labratourApplication: LabratourApplication) {
    // database for users
    val usersDatabase: UsersDatabase = Room.databaseBuilder(labratourApplication, UsersDatabase::class.java, "users_database").build()
    val userDao: UserDao = usersDatabase.userDao()
    val userCacheRepository = UserRepository(userDao)
    //
    val firebaseContainer = FirebaseContainer(labratourApplication, userCacheRepository)
    val locationContainer = LocationContainer(labratourApplication)
    val googlePlacesContainer = GooglePlacesContainer(labratourApplication, userCacheRepository)
}
