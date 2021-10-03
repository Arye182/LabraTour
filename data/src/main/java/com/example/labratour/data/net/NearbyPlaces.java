package com.example.labratour.data.net;

import android.content.Context;
import android.util.Log;

import com.example.labratour.domain.Entity.NearbyPlaceEntity;

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


    public abstract Call<NearbyPlaceEntity> getNearbyPlaces(Param requestInput) ;


        public Observable<NearbyPlaceEntity> getResult(Param input) {
          Log.i("type", input.toString()+"before create emitter in get result");

          return Observable.create(emitter -> {
                if (isThereInternetConnection()) {
                  Log.i("type", input.toString()+ "inside emitter in getresults");

                            getNearbyPlaces(input).enqueue(new Callback<NearbyPlaceEntity>() {
                            @Override
                            public void onResponse(@NotNull Call<NearbyPlaceEntity> call, Response<NearbyPlaceEntity> response) {
                                if(response.isSuccessful()){
                                    Log.i("type", "response:"+ response.body().toString()+"request: "+ call.request().toString());
                                    emitter.onNext(response.body());
                                }else {
                                    Log.i("type", "response:"+ response.errorBody().toString()+"request: "+ call.request().toString());

                                    emitter.onError(new Throwable(String.valueOf(response.code())));
                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<NearbyPlaceEntity> call, Throwable t) {
                                Log.i("type", t.getMessage());

                                Log.i("type", call.request().toString());
                            }


                        });
                    }
                else {
                    Log.i("type", "No Internet Conection");
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


