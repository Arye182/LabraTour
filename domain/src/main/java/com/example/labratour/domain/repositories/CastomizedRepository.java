package com.example.labratour.domain.repositories;

import com.example.labratour.domain.Entity.NearbyPlaceEntity;

import io.reactivex.Observable;

public interface CastomizedRepository {
  public Observable<NearbyPlaceEntity> getCastomizedPlaces (String lat, String lon  );

  }
