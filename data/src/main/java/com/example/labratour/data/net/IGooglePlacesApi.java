package com.example.labratour.data.net;


import com.example.labratour.data.Entity.MyNearbyPlaces;

import retrofit2.Call;
import retrofit2.http.Query;

//
public interface IGooglePlacesApi {
    Call<MyNearbyPlaces> getNearByPlaces(@Query("location") String location,
                                         @Query("radius") int radius,
                                         @Query("key") String key);
    Call<MyNearbyPlaces> getNearByPlacesByType(@Query("location") String location,
                                               @Query("radius") int radius,
                                               @Query("key") String key);}
//
//}
