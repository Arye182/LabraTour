package com.example.labratour.domain.useCases.LowLevelUsecases;

import com.example.labratour.domain.AtributedPoi;
import com.example.labratour.domain.NewRateDomain;
import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.useCases.UseCase;

import java.util.List;

import io.reactivex.Observable;

public class GetRatedPoiByIdFromApiUseCase extends UseCase<List<AtributedPoi>, List<NewRateDomain>> {
    protected GetRatedPoiByIdFromApiUseCase(ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
    }

    @Override
    public Observable<List<AtributedPoi>> buildUseCaseObservable(List<NewRateDomain> newRateDomainList) {
        return null;
    }

    public static class Param{
        private List<NewRateDomain>  newRateDomainList;

        public List<NewRateDomain> getNewRateDomainList() {
            return newRateDomainList;
        }

        public Param(List<NewRateDomain> newRateDomainList) {
            this.newRateDomainList = newRateDomainList;
        }
    }
}
