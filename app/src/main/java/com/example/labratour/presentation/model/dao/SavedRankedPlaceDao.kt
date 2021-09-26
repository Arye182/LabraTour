package com.example.labratour.presentation.model.dao

import androidx.room.* // ktlint-disable no-wildcard-imports
import com.example.labratour.presentation.model.data.SavedRankedPlaceModel

@Dao
interface SavedRankedPlaceDao {

    // get liked places for a user!
//    @Query("SELECT * FROM saved_places WHERE user_id = :user_id_arg AND liked = 1")
//    fun getLikedPlaces(user_id_arg: String): List<String>

    // insert a place liked
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedPlace(place: SavedRankedPlaceModel)

    @Query("DELETE FROM saved_places")
    suspend fun deleteAllSavedPlaces()
}
