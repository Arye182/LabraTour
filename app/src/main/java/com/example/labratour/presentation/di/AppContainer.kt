package com.example.labratour.presentation.di

import com.example.labratour.presentation.LabratourApplication

class AppContainer(labratourApplication: LabratourApplication) {
    val firebaseContainer = FirebaseContainer(labratourApplication)
    val locationContainer = LocationContainer(labratourApplication)
    val googlePlacesContainer = GooglePlacesContainer(labratourApplication)
}
