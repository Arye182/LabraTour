package com.example.labratour.data.Entity.mapper;

import com.example.labratour.data.Entity.MyNearbyPlaces;

import java.util.ArrayList;

public  class NearbyPlacesDataMapper {
    public NearbyPlacesDataMapper() {
    }

//    public ArrayList<String> transform(Collection<NearbyPlaceResult> result) {
//        final ArrayList<String> placesIds = new ArrayList<>(20);
//        for (NearbyPlaceResult n :result) {
//            final String placesId = transform(n);
//            if (placesId != null){
//                placesIds.add(new String(n.getPlaceId()));}
//        }
//        return placesIds;}



    public static ArrayList<String> transform(MyNearbyPlaces myNearbyPlaces){
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < myNearbyPlaces.getResults().length; i++) {
            ids.add(myNearbyPlaces.getResults()[i].getPlaceId());
        }
        return ids;
    }
    }

