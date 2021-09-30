package com.example.labratour.data.net;

import com.example.labratour.data.Entity.MyNearbyPlaces;
import com.example.labratour.data.Entity.NearbyPlaceResult;
import com.example.labratour.data.Entity.PoiDetailsEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapsGoogleApiService {
  @GET(
      "details/json?place_id=\"Id\"&fields=name%2Crating%2Ctypes&key=\"+API_KEY")
  Call<PoiDetailsEntity> poiDetailes(@Query("Id") String user);

  @GET("nearbysearch/json?location=\"location\"&radius=1000&key=\"+API_KEY")
  Call<MyNearbyPlaces> nearbyPlaces(@Query("location") String location);

  @GET("nearbysearch/json?location=\"{location}\"&radius=1000&type=\"type+\"&key=\"+API_KEY")
  Call<NearbyPlaceResult[]> nearbyPlacesByType(
      @Query("location") String location, @Query("type") String type);
}
