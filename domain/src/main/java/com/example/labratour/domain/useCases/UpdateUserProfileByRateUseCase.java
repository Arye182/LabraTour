package com.example.labratour.domain.useCases;

import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.RatingsRepository;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class UpdateUserProfileByRateUseCase extends UseCase<Void, UpdateUserProfileByRateUseCase.RequestInput> {
    private final RatingsRepository repository;

    protected UpdateUserProfileByRateUseCase(ExecutionThread executionThread, PostExecutionThread postExecutionThread, RatingsRepository ratingRepository) {
        super(executionThread, postExecutionThread);
        this.repository = ratingRepository;
    }
    public void execute(DisposableObserver observer, String userId, String poiId, int rate) {
        execute(observer, new RequestInput(userId, poiId, rate));
        //   Preconditions.checkNotNull(observer);
//        final Observable<User> observable =
//                this.buildUseCaseObservable(new RequestInput(password, email))
//                        .subscribeOn(Schedulers.from(executionThread))
//                        .observeOn(postExecutionThread.getScheduler());
//        addDisposable( observable.subscribeWith(observer));
    }
    @Override
    public Observable<Void> buildUseCaseObservable(RequestInput requestInput) {
        return this.repository.updateUserProfileByRate(requestInput.userId, requestInput.poiId, requestInput.rate);
    }


    public final static class RequestInput{



            String userId;
            String poiId;
            int rate;

            public RequestInput(String userId, String poiId, int rate) {
            }
        }

    }

