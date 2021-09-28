package com.example.labratour.domain.useCases;

import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.PlacesRepository;
import com.fernandocejas.arrow.checks.Preconditions;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class GetNearbyPlacesUseCase extends UseCase<ArrayList<String>, GetNearbyPlacesUseCase.RequestInput> {


    private final PlacesRepository placesRepository;

    public GetNearbyPlacesUseCase(PlacesRepository placesRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
        this.placesRepository = placesRepository;
    }



    public void execute(DisposableObserver observer, String lat, String lon) {
        Preconditions.checkNotNull(observer);
       // Log.i("testNearbyUseCase", "execute before calling build, thread: "+ Looper.myLooper());
    final Observable<ArrayList<String>> observable =
        this.buildUseCaseObservable(new RequestInput(lat, lon))
                .subscribeOn(Schedulers.from(executionThread))
            .observeOn(postExecutionThread.getScheduler());
        //Log.i("testNearbyUseCase", "execute after calling build and subscribeOn AND observeOn befor subscribing: "+ Looper.myLooper());

        addDisposable(observable.subscribeWith(observer));
      //  Log.i("testNearbyUseCase", "execute after  subscribing: "+ Looper.myLooper());

    }
    @Override
    public Observable<ArrayList<String>> buildUseCaseObservable(RequestInput requestInput) {
        return placesRepository.nearbyPlacesIds(requestInput.lat, requestInput.lon);
    }

    public static final class RequestInput {

        protected final String lat;
        protected final String lon;

        public RequestInput(String lat, String lon){
            this.lat = lat;
            this.lon = lon;

        }


    }
}