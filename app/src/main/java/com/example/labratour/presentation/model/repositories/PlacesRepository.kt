package com.example.labratour.presentation.model.repositories

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.labratour.presentation.model.dao.PlaceDao
import com.example.labratour.presentation.model.data.PlaceGoogleModel

class PlacesRepository(private val dao: PlaceDao){

    fun getPlace(id_place: String): PlaceGoogleModel{
        return dao.getPlace(id_place)
    }

    suspend fun insertPlaces(places: List<PlaceGoogleModel>){
        dao.insertPlaces(places)
    }

    suspend fun deleteAllPlaces(){
        dao.deleteAllPlaces()
    }

}
