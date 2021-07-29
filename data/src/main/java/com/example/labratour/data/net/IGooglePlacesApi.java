package com.example.labratour.data.net;


import com.example.labratour.data.Entity.MyNearbyPlaces;

import retrofit2.Call;
import retrofit2.http.Url;

public interface IGooglePlacesApi {
    Call<MyNearbyPlaces> getNearByPlaces(@Url String url);
}
