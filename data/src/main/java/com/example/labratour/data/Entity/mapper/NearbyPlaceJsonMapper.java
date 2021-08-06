package com.example.labratour.data.Entity.mapper;

import com.example.labratour.data.Entity.MyNearbyPlaces;
import com.example.labratour.data.Entity.NearbyPlaceResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class NearbyPlaceJsonMapper {
    private final Gson gson;


    public NearbyPlaceJsonMapper() {
        this.gson = new Gson();
    }
    public NearbyPlaceResult transformNearbyPlaceResult(String singleNearbyPlaceResponse) throws JsonSyntaxException {
        final Type nearbyPlaceType = new TypeToken<NearbyPlaceResult>() {}.getType();
        return this.gson.fromJson(singleNearbyPlaceResponse, nearbyPlaceType);
    }
    public  ArrayList<NearbyPlaceResult> transformCollection(String response)
         throws JsonSyntaxException {
            final Type nearbyPlaceType = new TypeToken<MyNearbyPlaces>() {}.getType();
            MyNearbyPlaces nearbyPlaces = this.gson.fromJson(response, MyNearbyPlaces.class);
           // return new ArrayList<NearbyPlaceResult>(Arrays.asList(nearbyPlaces.getResults()));}
        return new ArrayList<NearbyPlaceResult>(Arrays.asList(nearbyPlaces.getResults()));
//        final ArrayList<NearbyPlaceResult> places = new ArrayList<>(20);
//        for (String n :arr) {
//            final NearbyPlaceResult place = transformNearbyPlaceResult(n);
//            if (place != null){
//                places.add(place);}
//        }
//        return places;}
    }}
