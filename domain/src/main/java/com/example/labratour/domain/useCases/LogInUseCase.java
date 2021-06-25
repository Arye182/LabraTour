package com.example.labratour.domain.useCases;


import com.example.labratour.domain.Entity.User;
import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.UserRepository;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class LogInUseCase extends UseCase<User, LogInUseCase.RequestInput> {
     private final UserRepository userRepository;

    public LogInUseCase(UserRepository userRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
        super(executionThread, postExecutionThread);
        this.userRepository = userRepository;
    }

    public void execute(DisposableObserver<User> observer, String password, String email) {
        execute(observer, new RequestInput(password, email));
     //   Preconditions.checkNotNull(observer);
//        final Observable<User> observable =
//                this.buildUseCaseObservable(new RequestInput(password, email))
//                        .subscribeOn(Schedulers.from(executionThread))
//                        .observeOn(postExecutionThread.getScheduler());
//        addDisposable( observable.subscribeWith(observer));
    }
    public Observable<User> buildUseCaseObservable(@NotNull RequestInput requestInput) {
        return userRepository.login(requestInput.email, requestInput.password);
    }




    public static final class RequestInput {

        protected final String password;
        protected final String email;

        public RequestInput(String email, String password){
            this.password = password;
            this.email = email;
        }

        public static RequestInput forUser(String email, String password) {
            return new RequestInput(password, email);
        }
}
}
