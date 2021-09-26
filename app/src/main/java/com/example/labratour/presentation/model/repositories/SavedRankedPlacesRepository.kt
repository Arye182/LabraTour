package com.example.labratour.presentation.model.repositories

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.labratour.presentation.model.dao.SavedRankedPlaceDao
import com.example.labratour.presentation.model.dao.UserDao
import com.example.labratour.presentation.model.data.SavedRankedPlaceModel

class SavedRankedPlacesRepository(private val dao: SavedRankedPlaceDao) {

    // get liked places for a user!
//    fun getLikedPlaces(user_id_arg: String): List<String> {
//        return dao.getLikedPlaces(user_id_arg)
//    }

    // insert a place liked
    suspend fun insertSavedPlace(place: SavedRankedPlaceModel) {
        dao.insertSavedPlace(place)
    }

    suspend fun deleteAllSavedPlaces() {
        dao.deleteAllSavedPlaces()
    }
}