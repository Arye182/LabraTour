package com.example.labratour.domain.repositories;

import com.example.labratour.domain.Entity.NearbyPlaceEntity;

import io.reactivex.Observable;

public interface  NearbyPlacesRepository {
    Observable<NearbyPlaceEntity> get(String lat, String lon);


  //  Observable<ArrayList<String>> get(String lat, String lon, String type);
    Observable<NearbyPlaceEntity> get(String lat, String lon, String type);
}
