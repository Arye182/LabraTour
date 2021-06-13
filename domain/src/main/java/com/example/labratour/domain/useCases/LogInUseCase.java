package com.example.labratour.domain.useCases;


import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.UserRepository;
import com.fernandocejas.arrow.checks.Preconditions;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class LogInUseCase extends UseCase<Void, LogInUseCase.RequestInput> {
     private UserRepository userRepository;

    public LogInUseCase(UserRepository userRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
        this.userRepository = userRepository;
    }

    public void execute(DisposableObserver<Void> observer, String password, String email) {
        Preconditions.checkNotNull(observer);
        final Observable<Void> observable =
                this.buildUseCaseObservable(new RequestInput(password, email))
                        .subscribeOn(Schedulers.from(executionThread))
                        .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));
    }
    public Observable buildUseCaseObservable(RequestInput requestInput) {
        return userRepository.login(requestInput.email, requestInput.password);
    }



    public static final class RequestInput {

        private final String password;
        private final String email;

        private RequestInput(String email, String password){
            this.password = password;
            this.email = email;
        }

        public static RequestInput forUser(String email, String password) {
            return new RequestInput(password, email);
        }
}
}
