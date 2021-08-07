package com.example.labratour.data.repositories;

import com.example.labratour.data.net.RestApi;
import com.example.labratour.domain.repositories.PlacesRepository;

import java.util.ArrayList;

import io.reactivex.Observable;

public class PlacesRepositoryImpl implements PlacesRepository {

    private final RestApi restApi;


    //   private UserDataMapper userDataMapper;
    // private final CloudUserDataSource cloudUserDataSource;


    public PlacesRepositoryImpl(RestApi restApi) {
        this.restApi = restApi;
    }

    public Observable<ArrayList<String>> nearbyPlacesIds(String lat, String lon) {
        return this.restApi
                .nearbyPlaces(lat, lon);
//                .map(
//                        new Function<List<NearbyPlaceResult>, ArrayList<String>>() {
//
//
//                            @Override
//                            public ArrayList<String> apply(List<NearbyPlaceResult> result) throws Exception {
//                                return new NearbyPlacesDataMapper().transform(result);
//                            }
//                        });




    }


    @Override
    public Observable<ArrayList<String>> nearbyPlaces(String lat, String lon) {
        return null;
    }
}
