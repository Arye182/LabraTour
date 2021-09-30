package com.example.labratour.domain.useCases;


import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.fernandocejas.arrow.checks.Preconditions;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCase<T, P> {
    private final CompositeDisposable myDisposables;
    protected final ExecutionThread executionThread;
    protected final PostExecutionThread postExecutionThread;

    protected UseCase(ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        this.executionThread = executionThread;
        this.myDisposables = new CompositeDisposable();
        this.postExecutionThread = postExecutionThread;
    }

    /**
     * Builds an Observable which will be used when executing the current UseCase.
     */
    public abstract Observable<T> buildUseCaseObservable(P requestInput);

    public void execute(DisposableObserver observer, P requestInput) {
        Preconditions.checkNotNull(observer);
        final Observable<T> observable =
        this.buildUseCaseObservable(requestInput)
            .subscribeOn(Schedulers.from(executionThread))
            .observeOn(postExecutionThread
                    .getScheduler());
        addDisposable(observable.subscribeWith(observer));
    }



    public void dispose() {
        if (!myDisposables.isDisposed()) {
            myDisposables.dispose();
        }
    }
    protected void addDisposable(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(myDisposables);
        myDisposables.add(disposable);
    }

     public class RequestInput {

        public   String lat;
        public   String lon;
        public   String type;

        public RequestInput(String lat, String lon) {
            this.lat = lat;
            this.lon = lon;
        }

        public RequestInput(String lat, String lon, String type) {
            this.lat = lat;
            this.lon = lon;
            this.type = type;
        }


    }



}
