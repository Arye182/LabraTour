package com.example.labratour.presentation.di

import com.example.labratour.presentation.LabratourApplication
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationContainer(labratourApplication: LabratourApplication) {
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(labratourApplication)
}
