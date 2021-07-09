package com.example.labratour.domain.useCases;

import com.example.labratour.domain.Entity.UserDomain;
import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.UserRepository;

import io.reactivex.Observable;

public class SaveNewUserToFirebaseUseCase extends UseCase<Void, UserDomain> {
  private final UserRepository userRepository;

  public SaveNewUserToFirebaseUseCase(
      UserRepository userRepository,
      ExecutionThread executionThread,
      PostExecutionThread postExecutionThread) {
    super(executionThread, postExecutionThread);
    this.userRepository = userRepository;
  }

//  public void execute(DisposableObserver<UserDomain> observer, UserDomain userDomain) {
//    execute(observer, );
    //   Preconditions.checkNotNull(observer);
    //        final Observable<User> observable =
    //                this.buildUseCaseObservable(new RequestInput(password, email))
    //                        .subscribeOn(Schedulers.from(executionThread))
    //                        .observeOn(postExecutionThread.getScheduler());
    //        addDisposable( observable.subscribeWith(observer));


    @Override
    public Observable<Void> buildUseCaseObservable(UserDomain userDomain) {
      return userRepository.saveNewUser(userDomain);
    }


  }
