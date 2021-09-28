package com.example.labratour.domain.repositories;

import com.example.labratour.domain.Atributes;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface PlacesRepository {
    public Observable<ArrayList<String>> nearbyPlaces(String lat, String lon);
    public Observable<ArrayList<String>> nearbyPlacesIds(String lat, String lon);
    public Single<Atributes> getPoiById(String Id) ;
    public Observable<ArrayList<String>> nearbyPlacesByType(String lat, String lon);}



