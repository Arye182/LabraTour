//package com.example.Entity;
//
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import executors.ExecutionThread;
//import executors.PostExecutionThread;
//import io.reactivex.Observable;
//import io.reactivex.observers.DisposableObserver;
//import io.reactivex.schedulers.TestScheduler;
//import repositories.UserRepository;
//
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//
//@RunWith(MockitoJUnitRunner.class)
//public class LogInUseCaseTest {
//
//    private LogInUseCaseTestClass useCase;
//
//    private TestDisposableObserver<Void> testObserver;
//
//    @Mock
//    private ExecutionThread mockExecutionThread;
//    @Mock private PostExecutionThread mockPostExecutionThread;
//    @Mock private UserRepository mockUserRepository;
//
//    @Rule
//    public ExpectedException expectedException = ExpectedException.none();
//
//    @Before
//    public void setUp() {
//        this.useCase = new LogInUseCaseTestClass(mockUserRepository, mockExecutionThread, mockPostExecutionThread);
//        this.testObserver = new TestDisposableObserver<>();
//        given(mockPostExecutionThread.getScheduler()).willReturn(new TestScheduler());
//        verify(mockUserRepository.login("miriyungreis@gmail.com", "4121"));
//
//
//    }
//
//
//
//    @Test
//    public void testSubscriptionWhenExecutingUseCase() {
//        useCase.execute(testObserver, "miriyungreis@gmail.com", "4121");
//        useCase.dispose();
//
//        assertThat(testObserver.isDisposed()).isTrue();
//    }
//
//    @Test
//    public void testShouldFailWhenExecuteWithNullObserver() {
//        expectedException.expect(NullPointerException.class);
//        useCase.execute(null, "password", "email");
//    }
//
//    private static class LogInUseCaseTestClass extends LogInUseCase {
//
//        LogInUseCaseTestClass(UserRepository userRepository, ExecutionThread executionThread, PostExecutionThread postExecutionThread) {
//            super(userRepository, executionThread, postExecutionThread);
//        }
//
//         protected Observable<Object> buildUseCaseObservable(LogInUseCaseTest.RequestInput requestInput) {
//            return Observable.empty();
//        }
//
//        @Override
//        public void execute(DisposableObserver<Void> observer, String email, String password) {
//            super.execute(observer, password, email);
//        }
//    }
//
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
//    }
//    private static class RequestInput {
//        private static RequestInput EMPTY = new RequestInput();
//        private RequestInput() {
//
//        }
//
//
//    }
//
//}




