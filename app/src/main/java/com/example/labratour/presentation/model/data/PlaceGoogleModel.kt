package com.example.labratour.presentation.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class PlaceGoogleModel(
    @PrimaryKey val id: String,
    val liked: Boolean,
    val rank: Int
)
