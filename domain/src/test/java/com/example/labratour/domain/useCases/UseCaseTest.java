//package com.example.labratour.domain.useCases;
//
//
//import com.example.labratour.domain.executors.ExecutionThread;
//import com.example.labratour.domain.executors.PostExecutionThread;
//import com.example.labratour.domain.repositories.NearbyPlacesRepository;
//import com.example.labratour.domain.repositories.PlacesRepository;
//
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//
//import io.reactivex.Observable;
//import io.reactivex.Scheduler;
//import io.reactivex.observers.DisposableObserver;
//import io.reactivex.schedulers.Schedulers;
//import io.reactivex.schedulers.TestScheduler;
//
//import static org.mockito.BDDMockito.given;
//
//@RunWith(MockitoJUnitRunner.class)
//public class UseCaseTest {
//    private UseCase useCase;
//    private ExecutionThread executionThread;
//    private TestPostExecutionThread testPostExecutionThread = new TestPostExecutionThread(new TestScheduler());
//    private TestScheduler mainScheduler;
//    private Scheduler ioScheduler;
//    private DisposableObserver subscriber = new DefaultObserver() ;
//    private ArrayList items;
//    private ArrayList items2;
//
//
//    @Mock
//    private PlacesRepository placesRepository;
//
//    @Before
//    public void setUp() {
//        useCase = new GetNearbyPlacesAllTypesUseCase((NearbyPlacesRepository) placesRepository, executionThread, testPostExecutionThread);
//       ioScheduler =  Schedulers.from(executionThread);
//        items =  new ArrayList();
//        items2 =  new ArrayList();
//
//        items.add("y");
//        items2.add("u");
//        given(placesRepository.nearbyPlacesIds("3","4")).willReturn(Observable.just(items, items2));
//        useCase.execute( subscriber, new GetNearbyPlacesAllTypesUseCase.RequestInput("3", "4"));
//    }
//
////    @Test
////    public void should_subscribe_repository_on_the_io_scheduler() throws Exception {
////        subscriber.getTestObserver().assertNoValues();
////
////        testPostExecutionThread.getScheduler().triggerActions();
////
////        subscriber.getTestObserver().assertNoValues();
////    }
//
//
//
//
//
//    public static class TestPostExecutionThread implements PostExecutionThread {
//        private TestScheduler testScheduler;
//
//        public TestPostExecutionThread(TestScheduler testScheduler){
//            this.testScheduler = testScheduler;
//        }
//
//        @Override
//        public TestScheduler getScheduler() {
//            return testScheduler;
//        }
//    }
//    public void testBuildUseCaseObservable() {
//    }
//
//    public void testExecute() {
//    }
//
//    public void testDispose() {
//    }
//
//    public void testAddDisposable() {
//    }
//
    //}