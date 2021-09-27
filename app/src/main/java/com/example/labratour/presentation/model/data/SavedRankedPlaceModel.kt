package com.example.labratour.presentation.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_places")
data class SavedRankedPlaceModel(
    @PrimaryKey(autoGenerate = true)
    val saved_id: Int,
    val user_id: String,
    val place_id: String,
    val liked: Int,
    val rank: Int
)
