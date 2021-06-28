package com.example.labratour.domain.repositories;

import com.example.labratour.domain.Entity.PresentablePoiDomain;

import java.util.List;

import io.reactivex.Observable;

public interface PresentablePoiRepository {
    Observable<List<PresentablePoiDomain>> getPresentablePoiById(String requestInput);
}
