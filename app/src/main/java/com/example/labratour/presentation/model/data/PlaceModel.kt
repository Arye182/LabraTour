package com.example.labratour.presentation.model.data

import android.graphics.Bitmap
import com.example.labratour.domain.Results
import com.google.android.libraries.places.api.model.Place

data class PlaceModel(
    val id: String,
    val googlePlaceSdk: Place,
    val liked: Boolean,
    val rank: Int,
    val bitmap: Bitmap
)
