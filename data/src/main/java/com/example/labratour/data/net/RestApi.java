package com.example.labratour.data.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.labratour.data.Entity.NearbyPlaceResult;
import com.example.labratour.data.Entity.mapper.NearbyPlaceJsonMapper;

import java.net.MalformedURLException;
import java.util.List;

import io.reactivex.Observable;

public class RestApi {

 private static final String API_KEY ="AIzaSyBXOteaabBVfmHXUYYyOgRv-DcE05g6-1E" ;
 private Context context;
 private NearbyPlaceJsonMapper nearbyPlaceJsonMapper;
 public RestApi() {
  if (context == null || nearbyPlaceJsonMapper == null) {
   throw new IllegalArgumentException("The constructor parameters cannot be null");
  }
  this.context = context.getApplicationContext();
  this.nearbyPlaceJsonMapper = new NearbyPlaceJsonMapper();
 }
 public Observable<List<NearbyPlaceResult>> nearbyPlaces(String lat, String lon) {
  return Observable.create(emitter -> {
   if (isThereInternetConnection()) {
    try {
     String response = getNearbyPlacesIds(lat, lon);
     if (response != null) {
      emitter.onNext(this.nearbyPlaceJsonMapper.transformCollection(
              response));
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
  return Connection.createGET("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+lat+","+lon+"&radius=1500&key="+API_KEY).requestSyncCall();
 }
 private boolean isThereInternetConnection() {
  boolean isConnected;

  ConnectivityManager connectivityManager =
          (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
  NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
  isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

  return isConnected;
 }


 }