package com.example.labratour.domain.Entity;

import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.UserRepository;
import com.example.labratour.domain.useCases.DefaultObserver;
import com.example.labratour.domain.useCases.SignUpUseCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UseCaseTest extends BaseUseCaseTest<UseCaseTest.TestUseCase, UseCaseTest.TestRepository> {

    private DisposableObserver disposableObserver;

    @Override
    public void setUp() {
        super.setUp();

        this.disposableObserver =  new DefaultObserver<User>();
    }

    @Override
    protected TestUseCase createUseCase() {
        return new TestUseCase(mockRepository, mockExecutionThread, new PostExecutionThread() {
            @Override
            public Scheduler getScheduler() {
                return new TestScheduler();
            }
        });
    }

    @Override
    protected TestRepository createRepository() {
        TestRepository testRepository = mock(TestRepository.class);
        when(testRepository.signUp()).thenReturn(Observable.just( new User("NULL")));
        return testRepository;
    }

    @Test
    @Override
    public void testBuildUseCaseObservable() throws Exception {
        testBuildUseCaseObservable(null, new Action() {
            @Override
            public void run() throws Exception {
                verify(mockRepository).signUp();

            }
        });
    }

    @Test
    public void buildUseCaseObservable_AsCorrectResult() {
        useCase.execute(disposableObserver, new Param());
        assert (disposableObserver.isDisposed());
    }


    interface TestRepository extends UserRepository {
        Observable<User> signUp();
    }

    public static class TestUseCase extends SignUpUseCase {


        public TestUseCase(TestRepository repository,

                            ExecutionThread executionThread,
                          PostExecutionThread postExecutionThread) {

            super(executionThread, postExecutionThread, repository);
        }
@Override
        public Observable<User> buildUseCaseObservable(Param param) {
            return userRepository.signUp("null", "null", "null", "null");
        }
        }




    public static class Param{
        String email;
        String password;
        String first_name;
        String last_name;

        public Param() {
        }
    }
}

