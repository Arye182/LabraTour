package com.example.labratour.presentation.model.repositories

import com.example.labratour.presentation.model.dao.SavedRankedPlaceDao
import com.example.labratour.presentation.model.data.SavedRankedPlaceModel

class SavedRankedPlacesRepository(private val dao: SavedRankedPlaceDao) {

    // get liked places for a user!
    fun getLikedPlaces(user_id: String): List<String> {
        return dao.getLikedPlaces(user_id)
    }

    // insert a place liked
    suspend fun insertSavedPlace(place: SavedRankedPlaceModel) {
        dao.insertSavedPlace(place)
    }

    // get top category
    // @Query("SELECT place_type, COUNT(place_type) AS 'value_occurrence' FROM saved_places GROUP BY place_type ORDER BY 'value_occurrence' DESC LIMIT 1")
    suspend fun getTopCategoryLiked(user_id: String) :String{
        return dao.getTopCategoryLiked(user_id)
    }

    suspend fun deleteAllSavedPlaces(user_id: String) {
        dao.deleteAllSavedPlaces(user_id)
    }
}
