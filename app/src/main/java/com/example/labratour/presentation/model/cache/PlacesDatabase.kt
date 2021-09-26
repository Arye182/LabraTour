package com.example.labratour.presentation.model.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.labratour.presentation.model.dao.PlaceDao
import com.example.labratour.presentation.model.data.PlaceGoogleModel
import javax.inject.Singleton

@Database(entities = [PlaceGoogleModel:: class], version = 1)
@Singleton
abstract class PlacesDatabase : RoomDatabase() {
    abstract fun placeDao(): PlaceDao
}