package com.example.labratour.data.net;

import android.content.Context;
import android.util.Log;

import com.example.labratour.data.Entity.RootObject;

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


    public abstract Call<RootObject> getNearbyPlaces(Param requestInput) ;


        public Observable<RootObject> getResult(Param input) {
        return Observable.create(emitter -> {
                if (isThereInternetConnection()) {

                        getNearbyPlaces(input).enqueue(new Callback<RootObject>() {
                            @Override
                            public void onResponse(@NotNull Call<RootObject> call, Response<RootObject> response) {
                                if(response.isSuccessful()){
                                    Log.i("testNearbyUseCase", "response:"+ response.body().toString()+"request: "+ call.request().toString());
                                    emitter.onNext(response.body());
                                }else {
                                    Log.i("testNearbyUseCase", "response:"+ response.errorBody().toString()+"request: "+ call.request().toString());

                                    emitter.onError(new Throwable(String.valueOf(response.code())));
                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<RootObject> call, Throwable t) {
                                Log.i("testNearbyUseCase", t.getMessage());

                                Log.i("testNearbyUseCase", call.request().toString());
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


