package com.example.labratour.domain.useCases;

import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.UserRepository;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class SignUpUseCase extends UseCase<Void, SignUpUseCase.Param> {
    public UserRepository userRepository;

    protected SignUpUseCase(ExecutionThread executionThread, PostExecutionThread postExecutionThread, UserRepository userRepository) {
        super(executionThread, postExecutionThread);
        this.userRepository = userRepository;
    }

    public void execute(DisposableObserver observer, String password, String email,String first_name, String last_name) {
        execute(observer, new Param(password, email, first_name, last_name));
        //   Preconditions.checkNotNull(observer);
//        final Observable<User> observable =
//                this.buildUseCaseObservable(new RequestInput(password, email))
//                        .subscribeOn(Schedulers.from(executionThread))
//                        .observeOn(postExecutionThread.getScheduler());
//        addDisposable( observable.subscribeWith(observer));
    }



    @Override
    public Observable buildUseCaseObservable(Param param) {
        return userRepository.signUp(param.email, param.password, param.first_name, param.last_name);
    }

    public static class Param{
        String email;
        String password;
        String first_name;
        String last_name;

        public Param(String password, String email, String first_name, String last_name) {
        }
    }

}
