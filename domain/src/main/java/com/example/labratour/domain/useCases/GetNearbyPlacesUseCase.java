package com.example.labratour.domain.useCases;

import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.PlacesRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class GetNearbyPlacesUseCase extends UseCase<List<String>, GetNearbyPlacesUseCase.RequestInput> {


    private final PlacesRepository placesRepository;

    public GetNearbyPlacesUseCase(PlacesRepository placesRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
        this.placesRepository = placesRepository;
    }



    public void execute(DisposableObserver observer, String lat, String lon) {
        execute(observer, new RequestInput("31.887347","34.796566"));}
    @Override
    public Observable<List<String>> buildUseCaseObservable(RequestInput requestInput) {
        return placesRepository.nearbyPlaces(requestInput.lat, requestInput.lon);
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