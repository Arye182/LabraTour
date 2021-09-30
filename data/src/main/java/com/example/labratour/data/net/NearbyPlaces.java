package com.example.labratour.data.net;

import android.content.Context;
import android.util.Log;

import com.example.labratour.data.Entity.MyNearbyPlaces;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class NearbyPlaces<Param> extends RestApi {
    public NearbyPlaces(Context context) {
        super(context);

    }
    //private Context context;


    public abstract Call<MyNearbyPlaces> getNearbyPlaces(Param requestInput) ;


        public Observable<MyNearbyPlaces> getResult(Param input) {
        return Observable.create(emitter -> {
                if (isThereInternetConnection()) {

                        getNearbyPlaces(input).enqueue(new Callback<MyNearbyPlaces>() {
                            @Override
                            public void onResponse(@NotNull Call<MyNearbyPlaces> call, Response<MyNearbyPlaces> response) {
                                if(response.isSuccessful()){
                                    emitter.onNext(response.body());
                                }else {
                                    emitter.onError(new Throwable(String.valueOf(response.code())));
                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<MyNearbyPlaces> call, Throwable t) {
                                Log.i("testNearbyUseCase", " ");
                            }


                        });
                    }
                else {
                    Log.i("testNearbyUseCase", "No Internet Conection");
                    emitter.onError(new Throwable("No Internet Conection"));
                }


    });}
//    private boolean isThereInternetConnection() {
//        boolean isConnected;
//
//        ConnectivityManager connectivityManager =
//                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());
//
//        return isConnected;
//    }


}


