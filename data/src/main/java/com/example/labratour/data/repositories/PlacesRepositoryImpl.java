package com.example.labratour.data.repositories;

import android.util.Log;

import com.example.labratour.data.Entity.mapper.PlaceDetailesDataMapper;
import com.example.labratour.data.net.RestApi;
import com.example.labratour.domain.Entity.Entity.PoiDetailsEntity;
import com.example.labratour.domain.repositories.PlacesRepository;

import java.util.HashMap;

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






    public Single<HashMap<String, Object>> getPoiById(String Id)  {
        Log.i( "rate 2", "id"+Id);

        return this.restApi.getPlaceById(Id).map(new Function<PoiDetailsEntity, HashMap<String, Object>>() {
            @Override
            public HashMap<String, Object> apply(PoiDetailsEntity poiDetailsEntity) throws Exception {
                Log.i("rate", "in apply of getpoibyid "+poiDetailsEntity.getTypes().toString());
                return placeDetailesDataMapper.transform(poiDetailsEntity).getAtributesMap();
            }
        });
    }


}
