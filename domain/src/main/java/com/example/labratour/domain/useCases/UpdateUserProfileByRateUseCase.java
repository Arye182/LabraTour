package com.example.labratour.domain.useCases;

import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.RatingsRepository;
import com.fernandocejas.arrow.checks.Preconditions;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class UpdateUserProfileByRateUseCase extends UseCase<String, UpdateUserProfileByRateUseCase.RequestInput> {
    private final RatingsRepository repository;

    public UpdateUserProfileByRateUseCase(ExecutionThread executionThread, PostExecutionThread postExecutionThread, RatingsRepository ratingRepository) {
        super(executionThread, postExecutionThread);
        this.repository = ratingRepository;
    }
    public void execute(DisposableObserver observer, String userId, String poiId, int rate) {

        Preconditions.checkNotNull(observer);

        final Observable<String> observable =
                this.buildUseCaseObservable(new RequestInput(userId, poiId, rate)).subscribeOn(Schedulers.from(executionThread)).observeOn(postExecutionThread.getScheduler());


        addDisposable(observable.subscribeWith(observer));

    }
    @Override
    public Observable<String> buildUseCaseObservable(RequestInput requestInput) {

        return this.repository.updateUserProfileByRate(requestInput.userId, requestInput.poiId, requestInput.rate );
    }


    public  static class RequestInput{
            String userId;
            String poiId;
            int rate;
            public RequestInput(String userId, String poiId, int rate) {
              this.userId =userId;
              this.poiId = poiId;
              this.rate = rate;

            }
        }
    }

