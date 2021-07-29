package com.example.labratour.domain.useCases;

import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;

import java.util.List;

import io.reactivex.Observable;

public class GetNearbyPlacesUseCase extends UseCase<List<String>, LogInUseCase.RequestInput> {


    protected GetNearbyPlacesUseCase(ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
    }

    @Override
    public Observable<List<String>> buildUseCaseObservable(LogInUseCase.RequestInput requestInput) {
        return null;
    }
}