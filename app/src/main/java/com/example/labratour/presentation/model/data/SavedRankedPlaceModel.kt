package com.example.labratour.presentation.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_places")
data class SavedRankedPlaceModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "saved_id")
    val saved_id: Int,
    @ColumnInfo(name = "user_id")
    val user_id: String,
    @ColumnInfo(name = "place_id")
    val place_id: String,
    @ColumnInfo(name = "place_type")
    val place_type: String,
    @ColumnInfo(name = "liked")
    val liked: Int,
    @ColumnInfo(name = "rank")
    val rank: Int
)
