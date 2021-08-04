package com.example.labratour.presentation.model.repositories

import android.location.Location
import androidx.lifecycle.MutableLiveData

interface LocationRepository {

    val currentLocation: MutableLiveData<Location>
        get() = MutableLiveData<Location>()
}
