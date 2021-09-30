package com.example.labratour.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.util.Log;

import com.example.labratour.data.Entity.MyNearbyPlaces;
import com.example.labratour.data.Entity.NearbyPlaceResult;
import com.example.labratour.data.Entity.PoiDetailsEntity;
import com.example.labratour.data.Entity.mapper.NearbyPlaceJsonMapper;
import com.example.labratour.data.Entity.mapper.PlaceDetailesDataMapper;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestApi {
private final Retrofit retrofit;
private MapsGoogleApiService service;
 private static final String API_KEY ="AIzaSyDjOvu7E3j3ddZAUG0PBFE6tmfHEaR3kZc" ;
 private Context context;
 private PlaceDetailesDataMapper placeDetailsDataMapper;
 private NearbyPlaceJsonMapper nearbyPlaceJsonMapper ;
// public RestApi(Context context) {
//  if (context == null ) {
//   throw new IllegalArgumentException("The constructor parameters cannot be null");
//  }

 public RestApi(Context context) {

 //this.context = context.getApplicationContext();
  this.nearbyPlaceJsonMapper = new NearbyPlaceJsonMapper();
  this.placeDetailsDataMapper = new PlaceDetailesDataMapper();
   retrofit =  RetrofitClient.getRetrofitClient("https://maps.googleapis.com/maps/api/place/");
   service = retrofit.create(MapsGoogleApiService.class);
   this.context = context.getApplicationContext();
 }
 private boolean isThereInternetConnection() {
  boolean isConnected;

  ConnectivityManager connectivityManager =
          (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
  NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
  isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

  return isConnected;
 }
 public Single<PoiDetailsEntity> getPlaceById(String id) {
  Log.e("UpdateUseCase","inside getPlaceId in restapi  before create  run on:" + Looper.myLooper().toString(), new Throwable("couldnt print my looper"));

  return Single.create(emitter -> {
   if (isThereInternetConnection()) {
    try {
//     Retrofit retrofit =  RetrofitClient.getRetrofitClient("https://maps.googleapis.com/maps/api/place");
//     MapsGoogleApiService service = retrofit.create(MapsGoogleApiService.class);
     service.poiDetailes(id).enqueue(new Callback<PoiDetailsEntity>() {
      @Override
      public void onResponse(Call<PoiDetailsEntity> call, Response<PoiDetailsEntity> response) {
       if (response!=null){
       emitter.onSuccess(response.body());
       ;}
       else{
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
    }
else {
     emitter.onError(new Exception("No Internet Conection"));
    }

 });}
 public Observable<MyNearbyPlaces> getPlaceNearbyAlternative(String lat, String lon) {

  return Observable.create(emitter -> {
   if (isThereInternetConnection()) {
    try {
//     Retrofit retrofit =  RetrofitClient.getRetrofitClient("https://maps.googleapis.com/maps/api/place");
//     MapsGoogleApiService service = retrofit.create(MapsGoogleApiService.class);
     service.nearbyPlaces(lat+"%2C"+lon).enqueue(new Callback<MyNearbyPlaces>() {
      @Override
      public void onResponse(@NotNull Call<MyNearbyPlaces> call, Response<MyNearbyPlaces> response) {
       if(response.isSuccessful()){
       emitter.onNext(response.body());
      }else {
        Log.i("testNearbyUseCase", " ");
        Log.i("testNearbyUseCase", " not succesful");

        emitter.onError(new Throwable(String.valueOf(response.code())));
       }
      }

      @Override
      public void onFailure(@NotNull Call<MyNearbyPlaces> call, Throwable t) {
       Log.i("testNearbyUseCase", " ");

      }


     });
    } catch (Exception e) {
     Log.i("testNearbyUseCase", " exception in emmision:" + e.getMessage());


    }
   }
   else {
    Log.i("testNearbyUseCase", "No Internet Conection");
  //  emitter.onError(new Exception("No Internet Conection"));
   }

  });}

 public  Observable<NearbyPlaceResult[]> getPlaceNearbyType(String lat, String lon, String type) {
  return Observable.create(emitter -> {
           if (isThereInternetConnection()) {
            try {

             service.nearbyPlacesByType(lat+"%2C"+lon, type).enqueue(new Callback<NearbyPlaceResult[]>() {
              @Override
              public void onResponse(@NotNull Call<NearbyPlaceResult[]> call, @NotNull Response<NearbyPlaceResult[]> response) {
               emitter.onNext(response.body());

              }

              @Override
              public void onFailure(@NotNull Call<NearbyPlaceResult[]> call, Throwable t) {
               Log.i("testNearbyUseCaseByType", "call failed");

               emitter.onError(t);
              }
             });
            } catch (Exception e) {
             Log.i("testNearbyUseCase", "exception ");

             emitter.onError(e);
            }
           }
           else {
            Log.i("testNearbyUseCase", "No Internet Conection");
            emitter.onError(new Exception("No Internet Conection"));
           }});
 }


 // something went completely south (like no internet connection)
  //  Log.d("Error", t.getMessage());

   }

