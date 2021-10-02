package com.example.labratour.domain.useCases;

import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.NearbyPlacesRepository;
import com.fernandocejas.arrow.checks.Preconditions;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class GetNearbyPlacesByTypeUseCase2 extends NearbyPlacesUseCase{


  public GetNearbyPlacesByTypeUseCase2(NearbyPlacesRepository nearbyPlacesRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
    super(nearbyPlacesRepository, executionThread, postExecutionThread);
  }

  public void execute(DisposableObserver observer, String lat, String lon, String type) {
      Preconditions.checkNotNull(observer);
      final Observable<ArrayList<String>> observable =
              this.buildUseCaseObservable(lat,  lon, type)
                      .subscribeOn(Schedulers.from(executionThread))
                      .observeOn(postExecutionThread
                              .getScheduler());
      addDisposable(observable.subscribeWith(observer));
    }


    public Observable<ArrayList<String>> buildUseCaseObservable(String lat,String lon, String type) {
        return repository.get(lat, lon, type);
    }



}


