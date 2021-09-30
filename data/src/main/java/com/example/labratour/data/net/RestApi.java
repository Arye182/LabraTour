package com.example.labratour.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.util.Log;

import com.example.labratour.data.Entity.PoiDetailsEntity;
import com.example.labratour.data.Entity.mapper.NearbyPlaceJsonMapper;
import com.example.labratour.data.Entity.mapper.PlaceDetailesDataMapper;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestApi {

  private final Retrofit retrofit;
  protected MapsGoogleApiService service;
  // private static final String API_KEY ="AIzaSyDjOvu7E3j3ddZAUG0PBFE6tmfHEaR3kZc" ;
  protected Context context;
  private PlaceDetailesDataMapper placeDetailsDataMapper;
  private NearbyPlaceJsonMapper nearbyPlaceJsonMapper;

  public RestApi(Context context) {
    this.nearbyPlaceJsonMapper = new NearbyPlaceJsonMapper();
    this.placeDetailsDataMapper = new PlaceDetailesDataMapper();
    retrofit = RetrofitClient.getRetrofitClient("https://maps.googleapis.com/maps/api/place/");
    service = retrofit.create(MapsGoogleApiService.class);
    this.context = context.getApplicationContext();
  }

  boolean isThereInternetConnection() {
    boolean isConnected;

    ConnectivityManager connectivityManager =
        (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

    return isConnected;
  }

  public Single<PoiDetailsEntity> getPlaceById(String id) {
    Log.e(
        "UpdateUseCase",
        "inside getPlaceId in restapi  before create  run on:" + Looper.myLooper().toString(),
        new Throwable("couldnt print my looper"));

    return Single.create(
        emitter -> {
          if (isThereInternetConnection()) {
            try {
              service
                  .poiDetailes(id)
                  .enqueue(
                      new Callback<PoiDetailsEntity>() {
                        @Override
                        public void onResponse(
                            Call<PoiDetailsEntity> call, Response<PoiDetailsEntity> response) {
                          if (response != null) {
                            emitter.onSuccess(response.body());
                            ;
                          } else {
                            emitter.onError(new Exception("No response from map/api"));
                          }
                        }

                        @Override
                        public void onFailure(Call<PoiDetailsEntity> call, Throwable t) {
                          emitter.onError(t);
                        }
                      });
            } catch (Exception e) {
              emitter.onError(new Exception(e.getCause()));
            }
          } else {
            emitter.onError(new Exception("No Internet Conection"));
          }
        });
  }
}
