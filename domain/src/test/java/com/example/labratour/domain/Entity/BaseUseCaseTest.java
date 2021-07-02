package com.example.labratour.domain.Entity;

//
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoMoreInteractions;
//import static org.mockito.Mockito.verifyZeroInteractions;
//
//@RunWith(MockitoJUnitRunner.class)
//public abstract class BaseUseCaseTest<USE_CASE extends UseCase, REPOSITORY extends UserRepository> {
//
//    public USE_CASE useCase;
//
//    protected REPOSITORY mockRepository;
////
////    @Mock
////    protected Messenger mockMessenger;
//
//    @Mock
//    protected ExecutionThread mockExecutionThread;
//
//    @Mock
//    protected PostExecutionThread mockPostExecutionThread;
//
//    @Before
//    public void setUp() {
//        mockRepository = createRepository();
//        useCase = createUseCase();
//    }
//
//    protected abstract USE_CASE createUseCase();
//
//    protected abstract REPOSITORY createRepository();
//   // public abstract void testBuildUseCaseObservable();
//
////
////    public void testBuildUseCaseObservable(Object requestData)  {
////        useCase.buildUseCaseObservable(requestData);
////        verify(mockRepository).signUp();
////        verifyNoMoreInteractions(mockRepository);
////        verifyZeroInteractions(mockExecutionThread);
////        verifyZeroInteractions(mockPostExecutionThread);
////    }
//}
