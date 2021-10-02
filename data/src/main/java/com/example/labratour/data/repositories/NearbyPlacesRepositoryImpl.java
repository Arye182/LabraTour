package com.example.labratour.data.repositories;

import android.util.Log;

import com.example.labratour.data.Entity.RootObject;
import com.example.labratour.data.Entity.mapper.NearbyPlacesDataMapper;
import com.example.labratour.data.net.NearbyPlaces;
import com.example.labratour.data.net.NearbyPlacesAllTypes;
import com.example.labratour.data.net.NearbyPlacesByType;
import com.example.labratour.domain.repositories.NearbyPlacesRepository;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class NearbyPlacesRepositoryImpl implements NearbyPlacesRepository {
    private  NearbyPlacesByType nearbyPlacesByType;
    private NearbyPlacesAllTypes nearbyPlacesAllTypes;
private    NearbyPlacesDataMapper nearbyPlacesDataMapper;
    public NearbyPlacesRepositoryImpl(NearbyPlaces restApi) {
        nearbyPlacesDataMapper = new NearbyPlacesDataMapper();
    nearbyPlacesAllTypes = new NearbyPlacesAllTypes(restApi.getContext().getApplicationContext());
        nearbyPlacesByType = new NearbyPlacesByType(restApi.getContext().getApplicationContext());
    }




    @Override
    public Observable<ArrayList<String>> get(String lat, String lon, String type) {
      Log.i("testNearbyTypeUseCase", "lat:"+lat+ "lon:" + lon+ type);

      return nearbyPlacesByType

                .getResult(new NearbyPlacesByType.Param(lat, lon, type))
                .map(
                        new Function<RootObject, ArrayList<String>>() {

                            @Override
                            public ArrayList<String> apply(@NotNull RootObject myNearbyPlaces) throws Exception {
                                return NearbyPlacesDataMapper.transform(myNearbyPlaces);
                            }
                        });
    }

    @Override
    public Observable<ArrayList<String>> get(String lat, String lon) {
        return nearbyPlacesAllTypes
                .getResult(new NearbyPlacesAllTypes.Param(lat, lon))
                .map(
                        new Function<RootObject, ArrayList<String>>() {
                            @Override
                            public ArrayList<String> apply(RootObject myNearbyPlaces) throws Exception {
                                return NearbyPlacesDataMapper.transform(myNearbyPlaces);
                            }});}


}
