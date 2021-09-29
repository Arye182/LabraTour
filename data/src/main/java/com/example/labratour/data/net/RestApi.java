package com.example.labratour.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.util.Log;

import com.example.labratour.data.Entity.NearbyPlaceResult;
import com.example.labratour.data.Entity.PoiDetailsEntity;
import com.example.labratour.data.Entity.mapper.NearbyPlaceJsonMapper;
import com.example.labratour.data.Entity.mapper.PlaceDetailesDataMapper;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RestApi {
private  Retrofit retrofit;
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
  MapsGoogleApiService service = retrofit.create(MapsGoogleApiService.class);
 }
 public Observable<String> nearbyPlaces(String lat, String lon) {
//  Log.i("testNearbyUseCase","inside nearbyplaces in restapi  before create  run on:" + Looper.myLooper().toString(), new Throwable("couldnt print my looper"));

  return Observable.create(emitter -> {
   if (isThereInternetConnection()) {
    try {

     String response = getNearbyPlacesIds(lat, lon);
     if (response != null) {
      emitter.onNext(
              response);
      emitter.onComplete();
     } else {
      emitter.onError(new Exception());
     }
    } catch (Exception e) {
     emitter.onError(new Exception(e.getCause()));
    }
   } else {
    emitter.onError(new Exception());
   }
  });
 }
 private String getNearbyPlacesIds(String lat, String lon) throws MalformedURLException {
  //Log.i("testNearbyUseCase","inside nearbyplacesIds in restapi  called from inside emitter   run on:" + Looper.myLooper().toString(), new Throwable("couldnt print my looper"));
 // Retrofit retrofit =  RetrofitClient.getRetrofitClient("https://maps.googleapis.com/maps/api/place");
//  MapsGoogleApiService service = retrofit.create(MapsGoogleApiService.class);
  Connection connection = Connection.createGET("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lon+"&radius=1000&key="+API_KEY);
  return connection.requestSyncCall();
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
 public Observable<NearbyPlaceResult[]> getPlaceNearbyAlternative(String lat, String lon) {

  return Observable.create(emitter -> {
   if (isThereInternetConnection()) {
    try {
//     Retrofit retrofit =  RetrofitClient.getRetrofitClient("https://maps.googleapis.com/maps/api/place");
//     MapsGoogleApiService service = retrofit.create(MapsGoogleApiService.class);
     service.nearbyPlaces(lat, lon).enqueue(new Callback<NearbyPlaceResult[]>() {
      @Override
      public void onResponse(@NotNull Call<NearbyPlaceResult[]> call, @NotNull Response<NearbyPlaceResult[]> response) {
       emitter.onNext(response.body());

      }

      @Override
      public void onFailure(@NotNull Call<NearbyPlaceResult[]> call, Throwable t) {
       Log.i("testNearbyUseCase", "call failed");

       emitter.onError(t);
      }
     });
    } catch (Exception e) {
     Log.i("testNearbyUseCase", " ");

     emitter.onError(e);
    }
   }
   else {
    Log.i("testNearbyUseCase", "No Internet Conection");
    emitter.onError(new Exception("No Internet Conection"));
   }

  });}




    // something went completely south (like no internet connection)
  //  Log.d("Error", t.getMessage());

   }

