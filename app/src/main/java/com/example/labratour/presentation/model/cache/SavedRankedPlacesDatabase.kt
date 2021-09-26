package com.example.labratour.presentation.model.cache

import androidx.room.Database
import androidx.room.Ignore
import androidx.room.RoomDatabase
import com.example.labratour.presentation.model.dao.SavedRankedPlaceDao
import com.example.labratour.presentation.model.data.SavedRankedPlaceModel
import javax.inject.Singleton

@Database(entities = [SavedRankedPlaceModel:: class], version = 1)
@Singleton
abstract class SavedRankedPlacesDatabase : RoomDatabase() {
    abstract fun savedRankedPlaceDao(): SavedRankedPlaceDao
}
