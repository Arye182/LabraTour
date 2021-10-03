package com.example.labratour.data.repositories;

import com.example.labratour.data.Entity.mapper.PlaceDetailesDataMapper;
import com.example.labratour.data.net.RestApi;
import com.example.labratour.domain.Atributes;
import com.example.labratour.domain.Entity.Entity.PoiDetailsEntity;
import com.example.labratour.domain.repositories.PlacesRepository;

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






    public Single<Atributes> getPoiById(String Id)  {
        return this.restApi.getPlaceById(Id).map(new Function<PoiDetailsEntity, Atributes>() {
            @Override
            public Atributes apply(PoiDetailsEntity poiDetailsEntity) throws Exception {
                return placeDetailesDataMapper.transform(poiDetailsEntity);
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.computation());
    }


}
