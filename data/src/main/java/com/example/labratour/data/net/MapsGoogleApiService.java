package com.example.labratour.data.net;

import com.example.labratour.data.Entity.PoiDetailsEntity;
import com.example.labratour.data.Entity.RootObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapsGoogleApiService {
  @GET(
      "details/json?place_id=\"Id\"&fields=name%2Crating%2Ctypes&key=\"+API_KEY")
  Call<PoiDetailsEntity> poiDetailes(@Query("Id") String user);

  @GET("nearbysearch/json?")
  Call<RootObject> nearbyPlaces(
@Query("location") String location,
  @Query("radius") int radius,
  @Query("key") String key);





  /**
   * @param location
   * @param type
   * @return
   */
  @GET("nearbysearch/json?")
  Call<RootObject> nearbyPlacesByType(


                                                              @Query("location") String location,
                                                      @Query("radius") int radius,
          @Query("type") String type,
                                                      @Query("key") String key);

}
