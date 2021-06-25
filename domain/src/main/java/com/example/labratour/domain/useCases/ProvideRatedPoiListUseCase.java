package com.example.labratour.domain.useCases;

import com.example.labratour.domain.NewRateDomain;
import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;

import io.reactivex.Observable;

public class ProvideRatedPoiListUseCase extends UseCase<Void, NewRateDomain> {

    protected ProvideRatedPoiListUseCase(ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
    }


    @Override
    Observable<Void> buildUseCaseObservable(NewRateDomain newRateDomain) {
        return null;
    }
}
