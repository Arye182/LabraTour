package com.example.labratour.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.labratour.data.Entity.mapper.PlaceDetailesDataMapper;
import com.example.labratour.domain.Entity.Entity.PoiDetailsEntity;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestApi {

  private final Retrofit retrofit;
  protected MapsGoogleApiService service;
   protected static final String API_KEY ="AIzaSyDjOvu7E3j3ddZAUG0PBFE6tmfHEaR3kZc" ;
  protected Context context;

    public Context getContext() {
        return context;
    }

    private PlaceDetailesDataMapper placeDetailsDataMapper;
  //private NearbyPlaceJsonMapper nearbyPlaceJsonMapper;

  public RestApi(Context context) {
   // this.nearbyPlaceJsonMapper = new NearbyPlaceJsonMapper();
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
    Log.i("UpdateUseCase", "1");

    return Single.create(
        emitter -> {
          if (isThereInternetConnection()) {
            Log.i("UpdateUseCase", "2");

            try {
              service
                  .poiDetailes(id, "opening_hours,price_level,rating", API_KEY)
                  .enqueue(
                      new Callback<PoiDetailsEntity>() {
                        @Override
                        public void onResponse(
                            @NotNull Call<PoiDetailsEntity> call,
                            @NotNull Response<PoiDetailsEntity> response) {
                          if(response.body()==null){
                            Log.i("UpdateUseCase", "eror"+response.toString() );}
else{
                          emitter.onSuccess( response.body());
                        }}

                        @Override
                        public void onFailure(
                            @NotNull Call<PoiDetailsEntity> call, @NotNull Throwable t) {
                          emitter.onError(t);
                        }
                      });
            } catch (Exception ignored) {
            }
          } else {
            Log.i("UpdateUseCase", "eror");
          }
        });
  }
}
