package com.example.labratour.domain.repositories;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Vector;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface PlacesRepository {
    public Observable<ArrayList<String>> nearbyPlaces(String lat, String lon);
    public Observable<ArrayList<String>> nearbyPlacesIds(String lat, String lon);
    public Single<Vector<Integer>> getPoiById(String Id) throws MalformedURLException;}


