package com.example.labratour.domain.useCases;

import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.NearbyPlacesRepository;
import com.fernandocejas.arrow.checks.Preconditions;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class GetNearbyPlacesAllTypesUseCase extends NearbyPlacesUseCase  {


    public GetNearbyPlacesAllTypesUseCase(NearbyPlacesRepository nearbyPlacesRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(nearbyPlacesRepository, executionThread, postExecutionThread);
    }

    public void execute(DisposableObserver observer, String lat, String lon) {
        Preconditions.checkNotNull(observer);
    final Observable<ArrayList<String>> observable =
        this.buildUseCaseObservable(lat, lon)
                .subscribeOn(Schedulers.from(executionThread))
            .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));

    }


    public Observable<ArrayList<String>> buildUseCaseObservable(String lat,String lon) {
        return repository.get(lat, lon);
    }

}