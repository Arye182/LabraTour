package com.example.labratour.data.net;

import com.example.labratour.domain.Entity.Entity.PoiDetailsEntity;
import com.example.labratour.domain.Entity.NearbyPlaceEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapsGoogleApiService {
  @GET(
          "details/json?")
  Call<PoiDetailsEntity> poiDetailes(@Query("Id") String poiId,
  @Query("fields") String fields,
  @Query("key") String Key);


  @GET("nearbysearch/json?")
  Call<NearbyPlaceEntity> nearbyPlaces(
@Query("location") String location,
  @Query("radius") int radius,
  @Query("key") String key);





  /**
   * @param location
   * @param type
   * @return
   */
  @GET("nearbysearch/json?")
  Call<NearbyPlaceEntity> nearbyPlacesByType(


                                                              @Query("location") String location,
                                                      @Query("radius") int radius,
                                                        @Query("type") String type,
                                                      @Query("key") String key);

}
