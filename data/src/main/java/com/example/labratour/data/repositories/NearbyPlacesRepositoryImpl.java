package com.example.labratour.data.repositories;

import android.util.Log;

import com.example.labratour.data.net.NearbyPlaces;
import com.example.labratour.data.net.NearbyPlacesAllTypes;
import com.example.labratour.data.net.NearbyPlacesByType;
import com.example.labratour.domain.Entity.NearbyPlaceEntity;
import com.example.labratour.domain.repositories.NearbyPlacesRepository;

import io.reactivex.Observable;

public class NearbyPlacesRepositoryImpl implements NearbyPlacesRepository {
  private NearbyPlacesByType nearbyPlacesByType;
  private NearbyPlacesAllTypes nearbyPlacesAllTypes;
 // private NearbyPlacesDataMapper nearbyPlacesDataMapper;

  public NearbyPlacesRepositoryImpl(NearbyPlaces restApi) {
   // nearbyPlacesDataMapper = new NearbyPlacesDataMapper();
    nearbyPlacesAllTypes = new NearbyPlacesAllTypes(restApi.getContext().getApplicationContext());
    nearbyPlacesByType = new NearbyPlacesByType(restApi.getContext().getApplicationContext());
  }

  @Override
  public Observable<NearbyPlaceEntity> get(String lat, String lon, String type) {
    Log.i("testNearbyTypeUseCase", "lat:" + lat + "lon:" + lon + type);

    return nearbyPlacesByType
        .getResult(new NearbyPlacesByType.Param(lat, lon, type));
   //     .map(
//            new Function<NearbyPlaceEntity, ArrayList<NearbyPlaceEntity>>() {
//
//              @Override
//              public ArrayList<NearbyPlaceEntity> apply(@NotNull NearbyPlaceEntity myNearbyPlaces)
//                  throws Exception {
//                return NearbyPlacesDataMapper.transform(myNearbyPlaces);
//              }
//            });
  }

  @Override
  public Observable<NearbyPlaceEntity> get(String lat, String lon) {
    return nearbyPlacesAllTypes.getResult(new NearbyPlacesAllTypes.Param(lat, lon));
    // .map(
    //                        new Function<NearbyPlaces, ArrayList<String>>() {
    //                            @Override
    //                            public ArrayList<NearbyPlacesEnt> apply(NearbyPlaceEntity
    // myNearbyPlaces) throws Exception {
    //                                return NearbyPlacesDataMapper.transform
    //
    //                                        (myNearbyPlaces);
    //                            }});}

  }
}
