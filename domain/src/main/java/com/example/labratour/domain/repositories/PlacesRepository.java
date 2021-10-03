package com.example.labratour.domain.repositories;

import java.util.HashMap;

import io.reactivex.Single;

public interface PlacesRepository {
    public Single<HashMap<String, Object>> getPoiById(String Id);}



