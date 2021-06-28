package com.example.labratour.domain.repositories;

import com.example.labratour.domain.NewRateDomain;

import java.util.List;

import io.reactivex.Observable;

public interface AtributedPoiRepository {

    Observable getListByNewRateIdList(List<NewRateDomain> requestInput);
}
