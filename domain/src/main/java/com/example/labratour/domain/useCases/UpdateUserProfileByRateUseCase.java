package com.example.labratour.domain.useCases;

import android.os.Looper;
import android.util.Log;

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
        Log.e("UpdateUseCase","before build observer in execute run on:" +Looper.myLooper().toString(), new Throwable("couldnt print my looper"));

        Preconditions.checkNotNull(observer);
        Log.e("UpdateUseCase","before build execute run on:" +Looper.myLooper().toString(), new Throwable("couldnt print my looper"));

        final Observable<String> observable =
                this.buildUseCaseObservable(new RequestInput(userId, poiId, rate));
        Log.e("UpdateUseCase","before subscription and subscribeON, ObserveOn execute run on:" +Looper.myLooper().toString(), new Throwable("couldnt print my looper"));
        observable.subscribeOn(Schedulers.from(executionThread)).observeOn(postExecutionThread.getScheduler());
        Log.e("UpdateUseCase","before subscription and after subscribeON, ObserveOn execute run on:" +Looper.myLooper().toString(), new Throwable("couldnt print my looper"));


        addDisposable(observable.subscribeWith(observer));
        Log.e("UpdateUseCase","after subscription execute run on:" +Looper.myLooper().toString(), new Throwable("couldnt print my looper"));

    }
    @Override
    public Observable<String> buildUseCaseObservable(RequestInput requestInput) {

        return this.repository.updateUserProfileByRate(requestInput.userId, requestInput.poiId, requestInput.rate,executionThread );
    }


    public final static class RequestInput{
            String userId;
            String poiId;
            int rate;
            public RequestInput(String userId, String poiId, int rate) {
            }
        }
    }

