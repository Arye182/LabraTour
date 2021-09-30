package com.example.labratour.data.repositories;

import com.example.labratour.data.Entity.MyNearbyPlaces;
import com.example.labratour.data.Entity.NearbyPlaceResult;
import com.example.labratour.data.Entity.PoiDetailsEntity;
import com.example.labratour.data.Entity.mapper.PlaceDetailesDataMapper;
import com.example.labratour.data.net.RestApi;
import com.example.labratour.domain.Atributes;
import com.example.labratour.domain.repositories.PlacesRepository;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class PlacesRepositoryImpl implements PlacesRepository {

    private final RestApi restApi;
private final PlaceDetailesDataMapper placeDetailesDataMapper;

    //   private UserDataMapper userDataMapper;
    // private final CloudUserDataSource cloudUserDataSource;


    public PlacesRepositoryImpl(RestApi restApi) {
        this.restApi = restApi;
        this.placeDetailesDataMapper = new PlaceDetailesDataMapper();
    }

    @Override
    public Observable<ArrayList<String>> nearbyPlacesByType(String lat, String lon, String type) {
        return this.restApi
                .getPlaceNearbyType(lat, lon, type).map(new Function<NearbyPlaceResult[], ArrayList<String>>() {

                                                             @Override
                                                             public ArrayList<String> apply(NearbyPlaceResult[] nearbyPlaceResults)  {
                                                                 ArrayList<String> ids = new ArrayList<>();
                                                                 for (int i = 0; i<nearbyPlaceResults.length;i++) {
                                                                     ids.add(nearbyPlaceResults[i].getPlaceId());

                                                                 }
                                                                 return ids;}
                                                         }
                );    }

    @Override
    public Observable<ArrayList<String>> nearbyPlaces(String lat, String lon) {
        return null;
    }

    public Observable<ArrayList<String>> nearbyPlacesIds(String lat, String lon) {
    return this.restApi
        .getPlaceNearbyAlternative(lat, lon)
        .map(
            new Function<MyNearbyPlaces, ArrayList<String>>() {

              @Override
              public ArrayList<String> apply(MyNearbyPlaces nearbyPlaceResults) {
                  ArrayList<String> ids = new ArrayList<>();
                if (nearbyPlaceResults.getResults() != null) {
                for (int i = 0; i < nearbyPlaceResults.getResults().length; i++) {
                    if(nearbyPlaceResults.getResults()[i].getPlaceId()!=null){
                  ids.add(nearbyPlaceResults.getResults()[i].getPlaceId());
                }}}
                return ids;
              }
            });
                //.subscribeOn(Schedulers.io()).observeOn(Schedulers.computation());




    }




    public Single<Atributes> getPoiById(String Id)  {
        return this.restApi.getPlaceById(Id).map(new Function<PoiDetailsEntity, Atributes>() {
            @Override
            public Atributes apply(PoiDetailsEntity poiDetailsEntity) throws Exception {
                return placeDetailesDataMapper.transform(poiDetailsEntity);
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation());
    }
}
