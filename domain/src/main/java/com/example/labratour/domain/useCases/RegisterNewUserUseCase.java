package com.example.labratour.domain.useCases;

import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.UserRepository;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class RegisterNewUserUseCase extends UseCase<String, RegisterNewUserUseCase.Param> {
  private final UserRepository userRepository;

  public RegisterNewUserUseCase(
      UserRepository userRepository,
      ExecutionThread executionThread,
      PostExecutionThread postExecutionThread) {
    super(executionThread, postExecutionThread);
    this.userRepository = userRepository;
  }

  public void execute(DisposableObserver<String> observer, String email, String password) {
    execute(observer, new Param(email, password));
    //   Preconditions.checkNotNull(observer);
    //        final Observable<User> observable =
    //                this.buildUseCaseObservable(new RequestInput(password, email))
    //                        .subscribeOn(Schedulers.from(executionThread))
    //                        .observeOn(postExecutionThread.getScheduler());
    //        addDisposable( observable.subscribeWith(observer));
  }

  public Observable<String> buildUseCaseObservable(@NotNull Param param) {
    return userRepository.registerNewUser(param.email, param.password);
  }


  public static final class Param {

    protected final String password;
    protected final String email;

    public Param(String email, String password) {
      this.password = password;
      this.email = email;
    }

    public static Param forUser(String email, String password) {
      return new Param(password, email);
    }
  }
  }
