package com.example.labratour.domain.repositories;

import java.util.ArrayList;

import io.reactivex.Observable;

public interface  NearbyPlacesRepository <T>{
    Observable<ArrayList<String>> get(String lst, String lon);


    Observable<ArrayList<String>> get(String lat, String lon, String type);
}
