package useCases;


import com.fernandocejas.arrow.checks.Preconditions;

import executors.ExecutionThread;
import executors.PostExecutionThread;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCase<T, RequestInput> {
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
    abstract Observable<T> buildUseCaseObservable(RequestInput requestInput);

    public void execute(DisposableObserver<T> observer, RequestInput requestInput) {
        Preconditions.checkNotNull(observer);
        final Observable<T> observable =
        this.buildUseCaseObservable(requestInput)
            .subscribeOn(Schedulers.from(executionThread))
            .observeOn(postExecutionThread.getScheduler());
        addDisposable((Disposable)observable.subscribeWith(observer));
    }

    protected abstract void addDisposable(Observer<? super T> subscribeWith);

    public void dispose() {
        if (!myDisposables.isDisposed()) {
            myDisposables.dispose();
        }
    }
    private void addDisposable(Disposable disposable) {
        Preconditions.checkNotNull(disposable);
        Preconditions.checkNotNull(myDisposables);
        myDisposables.add(disposable);
    }

}
