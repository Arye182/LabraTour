package com.example.labratour.presentation.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.labratour.presentation.model.data.PlaceGoogleModel

@Dao
interface PlaceDao {

    @Query("SELECT * FROM places WHERE id = :id_place")
    fun getPlace(id_place: String): PlaceGoogleModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaces(places: List<PlaceGoogleModel>)

    @Query("DELETE FROM places")
    suspend fun deleteAllPlaces()
}
