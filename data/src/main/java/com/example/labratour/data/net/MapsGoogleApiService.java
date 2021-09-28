package com.example.labratour.data.net;

import com.example.labratour.data.Entity.NearbyPlaceResult;
import com.example.labratour.data.Entity.PoiDetailsEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MapsGoogleApiService {
  @GET(
      "details/json?place_id=\"+Id+\"&fields=name%2Crating%2Ctypes&key=\"+API_KEY")
  Call<PoiDetailsEntity> poiDetailes(@Path("Id") String user);
  @GET("/nearbysearch/json?location=\"+lat+\"lon+\"&radius=1000&key=\"+API_KEY")
  Call<List<NearbyPlaceResult>> nearbyPlaces(@Path("lat") String lat, @Path("lon")String lon);
}
