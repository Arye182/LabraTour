//package com.example.labratour.domain.Entity;
//
//import com.example.labratour.domain.executors.ExecutionThread;
//import com.example.labratour.domain.executors.PostExecutionThread;
//import com.example.labratour.domain.repositories.UserRepository;
//import com.example.labratour.domain.useCases.LogInUseCase;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import io.reactivex.Observable;
//import io.reactivex.observers.DisposableObserver;
//import io.reactivex.schedulers.TestScheduler;
//
//import static org.mockito.BDDMockito.given;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.BDDMockito.verify;
//import static org.mockito.BDDMockito.verifyNoMoreInteractions;
//import static org.mockito.BDDMockito.verifyZeroInteractions;
//
//@RunWith(MockitoJUnitRunner.class)
//public class LogInUseCaseTest {
////
//   private LogInUseCaseTestClass useCase;
////
//    private TestDisposableObserver<Void> testObserver;
//private LogInUseCase logInUseCase;
//    @Mock
//    private ExecutionThread mockExecutionThread;
//    @Mock private PostExecutionThread mockPostExecutionThread;
//    @Mock private UserRepository mockUserRepository;
//
//    @Rule
//    public ExpectedException expectedException = ExpectedException.none();
////
//    @Before
//    public void setUp() {
//        this.useCase = new LogInUseCaseTestClass(mockUserRepository, mockExecutionThread, mockPostExecutionThread);
//        this.testObserver = new TestDisposableObserver<>();
//        given(mockPostExecutionThread.getScheduler()).willReturn(new TestScheduler());
//        this.logInUseCase = new LogInUseCase(mockUserRepository, mockExecutionThread, mockPostExecutionThread);
//
//
//
//    }
////
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
////
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
//    private static class LogInUseCaseTestClass extends LogInUseCase {
//
//        LogInUseCaseTestClass(UserRepository userRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
//            super(userRepository, executionThread, postExecutionThread);
//        }
////
//         public Observable<Object> buildUseCaseObservable(LogInUseCase.RequestInput requestInput) {
//            return Observable.empty();
//        }
//
//
//        @Override
//        public void execute(DisposableObserver observer, String email, String password) {
//            super.execute(observer, password, email);
//        }
//    }
////
//    private static class TestDisposableObserver<T> extends DisposableObserver<T> {
//        private int valuesCount = 0;
//
//        @Override public void onNext(T value) {
//            valuesCount++;
//        }
//
//        @Override public void onError(Throwable e) {
//            // no-op by default.
//        }
//
//        @Override public void onComplete() {
//            // no-op by default.
//        }
//    }}







