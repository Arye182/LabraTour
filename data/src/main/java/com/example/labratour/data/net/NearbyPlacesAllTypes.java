package com.example.labratour.data.net;

import android.content.Context;
import android.util.Log;

import com.example.labratour.data.Entity.RootObject;

import retrofit2.Call;

public class NearbyPlacesAllTypes extends NearbyPlaces<NearbyPlacesAllTypes.Param> {
    public NearbyPlacesAllTypes(Context context ) {
        super(context);
    }


  public Call<RootObject> getNearbyPlaces(Param requestInput) {
        Log.i("testNearbyUseCase", "lat:"+requestInput.lat+ "lon:" + requestInput.lon);
    return service.nearbyPlaces(requestInput.lat+","+requestInput.lon, 2000, API_KEY);
  }


    public static class Param {
    public String lat;
    public String lon;

        public Param(String lat, String lon) {
            this.lat = lat;
            this.lon = lon;
        }
    }}
