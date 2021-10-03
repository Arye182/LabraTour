package com.example.labratour.data.repositories;

import com.example.labratour.domain.Entity.NearbyPlaceEntity;
import com.example.labratour.domain.repositories.CastomizedRepository;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CastomizedRepositoryImpl implements CastomizedRepository {
  private NearbyPlacesRepositoryImpl repository;

  public CastomizedRepositoryImpl(NearbyPlacesRepositoryImpl repository) {
    this.repository = repository;
  }

  @Override
  public Observable<NearbyPlaceEntity>  getCastomizedPlaces (String lat, String lon  ){
    return Observable.create(
            new ObservableOnSubscribe<NearbyPlaceEntity>() {
              @Override
              public void subscribe(ObservableEmitter<NearbyPlaceEntity> emitter) throws Exception {
                   repository.get(lat, lon).subscribe(new Observer<NearbyPlaceEntity>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(NearbyPlaceEntity value) {
                      //todo calculate
                      onNext(new NearbyPlaceEntity());

                    }

                    @Override
                    public void onError(Throwable e) {
                      emitter.onError(e);
                    }


                    @Override
                    public void onComplete() {
                    } });}});}
}

