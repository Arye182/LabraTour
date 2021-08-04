package com.example.labratour.domain.repositories;

import java.util.List;

import io.reactivex.Observable;

public interface PlacesRepository {
    public Observable<List<String>> nearbyPlaces(String lat, String lon);
}
