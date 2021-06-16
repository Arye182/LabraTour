package com.example.labratour.data;


import com.example.labratour.data.Entity.UserEntity;
import com.example.labratour.data.datasource.CloudUserDataSource;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CloudUserDataSourceTest {
  private CloudUserDataSource cloudUserDataSource;

  public final UserEntity UNSIGHN_USER = new UserEntity("miriyungreis@gmail.com", "1234");
  public final UserEntity SIGN_USER = new UserEntity("arye.amsalem@gmail.com", "123456");
  private TestObserver<AuthResult> subscriber;
  @Mock
  FirebaseAuth mockFirebaseAuth  ;
@Mock AuthResult authResult;
  private Task result = mockFirebaseAuth.getPendingAuthResult();

  @Before
  public void setUp() {
    subscriber = new TestObserver<>();

    //   FirebaseUser firebaseUser = mockFirebaseAuth.getCurrentUser();

    cloudUserDataSource = new CloudUserDataSource(mockFirebaseAuth);
    given(mockFirebaseAuth.signInWithEmailAndPassword("arye.amsalem@gmail.com", "123456")).willReturn(result);

    // given(mockFirebaseAuth.signInWithEmailAndPassword(SIGN_USER.getEmail(),
    // SIGN_USER.getPassword())).willReturn(SuccessContinuation);

  }

  @Test
  public void testLoginHappyCase() {
    Observable<AuthResult> observable = cloudUserDataSource.login(new UserEntity("arye.amsalem@gmail.com", "123456"));
    observable.subscribe(subscriber);
    subscriber.assertComplete();
  }
  }
//
//
//
//
//
//  }
//
//    public ExpectedException expectedException = ExpectedException.none();
//    //
//    @Before
//    public void setUp() {
//        this.useCase = new LogInUseCaseTest.LogInUseCaseTestClass(mockUserRepository, mockExecutionThread, mockPostExecutionThread);
//        this.testObserver = new LogInUseCaseTest.TestDisposableObserver<>();
//        given(mockPostExecutionThread.getScheduler()).willReturn(new TestScheduler());
//        this.logInUseCase = new LogInUseCase(mockUserRepository, mockExecutionThread, mockPostExecutionThread);
//
//
//
//    }
//    //
////
////
//    @Test
//    public void testSubscriptionWhenExecutingUseCase() {
//        useCase.execute(testObserver, "miriyungreis@gmail.com", "4121");
//        useCase.dispose();
//
//        assert(testObserver.isDisposed());
//    }
//    @Test
//    public void testBuildUseCaseObservableReturnCorrectResult() {
//        useCase.execute(testObserver, "miriyungreis@gmail.com", "4121");
//
//        assertThat(testObserver.valuesCount).isZero();
//    }
//    //
//    @Test
//    public void testShouldFailWhenExecuteWithNullObserver() {
//        expectedException.expect(NullPointerException.class);
//        useCase.execute(null, "miriyungreis@gmail.com", "4121");
//    }
//    @Test
//    public void testLogInUseCaseObservableHappyCase() {
//        logInUseCase.buildUseCaseObservable(new LogInUseCase.RequestInput("miriyungreis@gmail.com", "123456"));
//
//        verify(mockUserRepository).login("miriyungreis@gmail.com", "123456");
//        verifyNoMoreInteractions(mockUserRepository);
//        verifyZeroInteractions(mockExecutionThread);
//        verifyZeroInteractions(mockPostExecutionThread);
//    }
//    @Test
//    public void testShouldFailWhenNoOrEmptyParameters() {
//        expectedException.expect(NullPointerException.class);
//        logInUseCase.buildUseCaseObservable(null);
//    }
//
//
////
//private static class LogInUseCaseTestClass extends LogInUseCase {
//
//    LogInUseCaseTestClass(FirebaseAuth firebaseAuth) {
//        super(firebaseAuth);
//    }
//    //
//    public Observable<AuthResult> buildDataSourceObservable(LogInUseCase.RequestInput requestInput) {
//        return Observable.empty();
//    }
//    }
//
//
//    @Override
//    public Observable login() {
//        return buildDataSourceObservable();
//        Observable.create(
//                emitter -> {
//                    Task<AuthResult> authResultTask =
//                            firebaseAuth.signInWithEmailAndPassword(
//                                    userEntity.getEmail(), userEntity.getPassword());
//                    try{
//                        authResultTask.addOnFailureListener(
//                                new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull @NotNull Exception e) {
//                                        if (!(emitter.isDisposed())) {
//                                            emitter.onError(authResultTask.getException());
//                                        }
//                                    }
//                                });
//                        authResultTask.addOnCompleteListener(
//                                new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
//                                        if(!emitter.isDisposed())
//                                            emitter.onComplete();
//                                        else{
//
//                                        }
//                                    }
//                                });
//                    }
//                    catch (Throwable e ){
//                        throw e;
//                    }
//                } );
//
//
//
//    }
//}
////
//private static class TestDisposableObserver<T> extends DisposableObserver<T> {
//    private int valuesCount = 0;
//
//    @Override public void onNext(T value) {
//        valuesCount++;
//    }
//
//    @Override public void onError(Throwable e) {
//        // no-op by default.
//    }
//
//    @Override public void onComplete() {
//        // no-op by default.
//    }
//}







