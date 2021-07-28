package com.example.labratour.presentation.model.data

import android.graphics.Bitmap
import com.google.android.libraries.places.api.model.Place

data class PlaceModel(val googlePlace: Place, val liked: Boolean, val rank: Int, val bitmap: Bitmap)
