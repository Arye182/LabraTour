package com.example.labratour.data.net;


import com.example.labratour.domain.Results;

import retrofit2.Call;
import retrofit2.http.Query;

//
public interface IGooglePlacesApi {
    Call<Results> getNearByPlaces(@Query("location") String location,
                                  @Query("radius") int radius,
                                  @Query("key") String key);
    Call<Results> getNearByPlacesByType(@Query("location") String location,
                                        @Query("radius") int radius,
                                        @Query("key") String key);}
//
//}
