package com.example.labratour.data.net;

import android.content.Context;

import com.example.labratour.data.Entity.MyNearbyPlaces;

import retrofit2.Call;

public class NearbyPlacesByType extends NearbyPlaces<NearbyPlacesByType.Param>{


    public NearbyPlacesByType(Context context) {
        super(context);
    }

    @Override
    public Call<MyNearbyPlaces> getNearbyPlaces(Param param) {
    return this.service.nearbyPlacesByType(
        param.lat + "%2C" + param.lon, param.type);
    }

public static class Param {
   protected String lat;
    protected String lon;

    public Param(String lat, String lon, String type) {
        this.lat = lat;
        this.lon = lon;
        this.type = type;
    }

    protected String type;
}}
