package com.example.labratour.domain.useCases;


import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.fernandocejas.arrow.checks.Preconditions;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCase<T, Param> {
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
    abstract Observable<T> buildUseCaseObservable(Param requestInput);

    public void execute(DisposableObserver<T> observer, Param requestInput) {
        Preconditions.checkNotNull(observer);
        final Observable<T> observable =
        this.buildUseCaseObservable(requestInput)
            .subscribeOn(Schedulers.from(executionThread))
            .observeOn(postExecutionThread.getScheduler());
        addDisposable((Disposable)observable.subscribeWith(observer));
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

}
