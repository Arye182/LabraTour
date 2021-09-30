package com.example.labratour.domain.repositories;

import com.example.labratour.domain.Atributes;

import io.reactivex.Single;

public interface PlacesRepository {
    public Single<Atributes> getPoiById(String Id);}



