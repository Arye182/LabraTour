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
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class UseCaseTest extends BaseUseCaseTest<UseCaseTest.TestUseCase, UseCaseTest.TestRepository> {

    private DisposableObserver<? extends UserDomain> disposableObserver;

    @Override
    public void setUp() {
        super.setUp();

        this.disposableObserver =  new DefaultObserver<UserDomain>();
        given(mockPostExecutionThread.getScheduler()).willReturn(new TestScheduler());
    }

    @Override
    protected TestUseCase createUseCase() {
        return new TestUseCase(mockExecutionThread, mockPostExecutionThread, mockRepository

                  );
    }

    @Override
    protected TestRepository createRepository() {
        TestRepository testRepository = mock(TestRepository.class);
        when(testRepository.signUp()).thenReturn(Observable.just( new UserDomain("NULL")));
        return testRepository;
    }

    @Test
     public void testBuildUseCaseObservable()  {
        testBuildUseCaseObservable(new SignUpUseCase.Param("", "", "", ""));
    }

    @Test
    public void buildUseCaseObservable_AsCorrectResult() {
    useCase.execute(disposableObserver, new SignUpUseCase.Param("null", "null", "null", "null"));
     assertThat(disposableObserver.isDisposed());
    }


    public interface TestRepository extends UserRepository {

      @Override  Observable signUp();
    }

    protected class TestUseCase extends SignUpUseCase{
        public TestUseCase(ExecutionThread executionThread, PostExecutionThread postExecutionThread, UserRepository userRepository) {
            super(executionThread, postExecutionThread, userRepository);
        }

        //
    //        public TestUseCase(TestRepository repository,
    //
    //                            ExecutionThread executionThread,
    //                          PostExecutionThread postExecutionThread) {
    //
    //            super(executionThread, postExecutionThread, repository);

    @Override
    public Observable<Void> buildUseCaseObservable(Param param) {
      return userRepository.signUp();
    }
}



}

