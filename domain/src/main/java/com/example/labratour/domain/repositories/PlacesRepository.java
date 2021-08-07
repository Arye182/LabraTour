package com.example.labratour.domain.repositories;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface PlacesRepository {
    public Observable<ArrayList<String>> nearbyPlaces(String lat, String lon);
    public Observable<ArrayList<String>> nearbyPlacesIds(String lat, String lon);
}
