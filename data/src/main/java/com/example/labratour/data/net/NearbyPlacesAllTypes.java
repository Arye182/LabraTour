package com.example.labratour.data.net;

import android.content.Context;

import com.example.labratour.data.Entity.MyNearbyPlaces;

import retrofit2.Call;

public class NearbyPlacesAllTypes extends NearbyPlaces<NearbyPlacesAllTypes.Param> {
    public NearbyPlacesAllTypes(Context context ) {
        super(context);
    }


  public Call<MyNearbyPlaces> getNearbyPlaces(Param requestInput) {
    return service.nearbyPlaces(requestInput.lat + "%2C" + requestInput.lon);
  }


    public static class Param {
    public String lat;
    public String lon;

        public Param(String lat, String lon) {
            this.lat = lat;
            this.lon = lon;
        }
    }}
