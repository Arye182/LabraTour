package com.example.labratour.data.net;

import android.content.Context;
import android.util.Log;

import com.example.labratour.domain.Entity.NearbyPlaceEntity;

import retrofit2.Call;

public class NearbyPlacesByType extends NearbyPlaces<NearbyPlacesByType.Param>{


    public NearbyPlacesByType(Context context) {
        super(context);
    }

    @Override
    public Call<NearbyPlaceEntity> getNearbyPlaces(Param param) {
      Log.i("type", "lat:"+param.lat+ "lon:" + param.lon+ param.type);

      return this.service.nearbyPlacesByType(
        param.lat + "," + param.lon, 2000, param.type, API_KEY);
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

