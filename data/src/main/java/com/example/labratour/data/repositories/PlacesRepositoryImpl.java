package com.example.labratour.data.repositories;

import com.example.labratour.data.Entity.PoiDetailsEntity;
import com.example.labratour.data.Entity.mapper.PlaceDetailesDataMapper;
import com.example.labratour.data.net.RestApi;
import com.example.labratour.domain.Atributes;
import com.example.labratour.domain.repositories.PlacesRepository;

import java.net.MalformedURLException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class PlacesRepositoryImpl implements PlacesRepository {

    private final RestApi restApi;
private final PlaceDetailesDataMapper placeDetailesDataMapper;

    //   private UserDataMapper userDataMapper;
    // private final CloudUserDataSource cloudUserDataSource;


    public PlacesRepositoryImpl(RestApi restApi) {
        this.restApi = restApi;
        this.placeDetailesDataMapper = new PlaceDetailesDataMapper();
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


    public Single<Atributes> getPoiById(String Id) throws MalformedURLException {
        return this.restApi.getPlaceById(Id).map(new Function<PoiDetailsEntity, Atributes>() {
            @Override
            public Atributes apply(PoiDetailsEntity poiDetailsEntity) throws Exception {
                return placeDetailesDataMapper.transform(poiDetailsEntity);
            }
        });
    }
}
