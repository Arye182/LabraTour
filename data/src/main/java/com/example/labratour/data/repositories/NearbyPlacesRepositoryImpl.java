package com.example.labratour.data.repositories;

import com.example.labratour.data.Entity.MyNearbyPlaces;
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
    private final NearbyPlaces restApi;
private  final  NearbyPlacesDataMapper nearbyPlacesDataMapper;
    public NearbyPlacesRepositoryImpl(NearbyPlaces restApi) {
        this.restApi = restApi;
        nearbyPlacesDataMapper = new NearbyPlacesDataMapper();
    }




    @Override
    public Observable<ArrayList<String>> get(String lat, String lon, String type) {
         return restApi
                .getResult(new NearbyPlacesByType.Param(lat, lon, type))
                .map(
                        new Function<MyNearbyPlaces, ArrayList<String>>() {

                            @Override
                            public ArrayList<String> apply(@NotNull MyNearbyPlaces myNearbyPlaces) throws Exception {
                                return NearbyPlacesDataMapper.transform(myNearbyPlaces);
                            }
                        });
    }

    @Override
    public Observable<ArrayList<String>> get(String lat, String lon) {
        return restApi
                .getResult(new NearbyPlacesAllTypes.Param(lat, lon))
                .map(
                        new Function<MyNearbyPlaces, ArrayList<String>>() {
                            @Override
                            public ArrayList<String> apply(MyNearbyPlaces myNearbyPlaces) throws Exception {
                                return NearbyPlacesDataMapper.transform(myNearbyPlaces);
                            }});}


}
