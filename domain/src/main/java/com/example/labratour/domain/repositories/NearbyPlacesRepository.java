package com.example.labratour.domain.repositories;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface  NearbyPlacesRepository {
    Observable<ArrayList<String>> get(String lat, String lon);


    Observable<ArrayList<String>> get(String lat, String lon, String type);
}
