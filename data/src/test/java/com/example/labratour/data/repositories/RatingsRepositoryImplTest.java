package com.example.labratour.data.repositories;

import com.example.labratour.data.net.MapsGoogleApiService;
import com.example.labratour.data.net.RestApi;
import com.example.labratour.data.utils.JobExecutor;
import com.example.labratour.domain.Atributes;
import com.example.labratour.domain.UserAtributes;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.useCases.DefaultObserver;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.MalformedURLException;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RatingsRepositoryImplTest {
    UpdateUserProfileByRateObserverTest testObserver = new UpdateUserProfileByRateObserverTest();
     RatingsRepositoryImpl repository;
     Atributes atributes;
     UserAtributes userOldAtributes;
     UserAtributes userExpectedNewAtributes;
     int userCount;
     int rate;
     String result;
     @Mock
    MapsGoogleApiService mMockMapsGoogleApiService;
    @Mock
     PlacesRepositoryImpl mMockPlacesRepositoryImpl;
@Mock
    RestApi mMockRestApi;
    @Mock
    AtributesRepositoryImpl atributesRepository;

    private JobExecutor executionThread;
private TestScheduler testScheduler;
@Mock
    private PostExecutionThread postExecutionThread;
    private PoiDetailsEntity poiDetailsEntity;

    @Before
public void setUp() throws MalformedURLException, NoSuchFieldException {
      testScheduler =   new TestScheduler();
    repository = new RatingsRepositoryImpl( mMockPlacesRepositoryImpl, atributesRepository);
    // repository = new RatingsRepositoryImpl(userRepository, placesRepository, executionThread, postExecutionThread);
//        ioScheduler =  Schedulers.from(executionThread);
given(postExecutionThread.getScheduler()).willReturn(testScheduler);
executionThread = new JobExecutor();
    atributes =  generateAtributes();
         userOldAtributes  = generateUserAtributes(0,0,0,1,1,3/5, 4/5);
         userExpectedNewAtributes = generateUserAtributes(0,0, 0, 12/15, 12/15, 12/25, 48/75);
         userCount = 2;
         rate = 4;


       // given(repository.calculateNewAtributesForUser(atributes, userOldAtributes, userCount, rate)).willReturn(userExpectedNewAtributes);
      //  given(userRepository.updateNewAtributes(userExpectedNewAtributes, "3")).willReturn(new Single<Void>() {


        // useCase.execute( subscriber, new GetNearbyPlacesUseCase.RequestInput("3", "4"));
    }

    public void testUpdateRating() {
    }
    @Test
    public void testCalculateNewAtributesForUser() throws NoSuchFieldException {
    UserAtributes expected = generateUserAtributes(0,0, 0, 12/15, 12/15, 12/25, 48/75);
    UserAtributes actual = repository.calculateNewAtributesForUser(atributes, userOldAtributes, 2, 4);
    Assert.assertTrue(((actual.getUsersAggragateRating()==expected.getUsersAggragateRating())&&
            (actual.getPrice_level()==expected.getPrice_level())&&(actual.getAlwaysOpen()==expected.getAlwaysOpen())&&(actual.getArt_gallery()==expected.getArt_gallery())
            &&(actual.getAmusement_park()==expected.getAmusement_park()&&(actual.getAquarium())==expected.getAquarium())
            &&(actual.getCafe()==expected.getCafe())));

    }
    @Test
    public void testFirstRateByUser() throws MalformedURLException {
   // given(mMockRestApi.getPlaceById("3")).willReturn(Single.just(GenerateAtributesPoiDetails.newInstance()));
        given(mMockPlacesRepositoryImpl.getPoiById("3")).willReturn(Single.just(generateAtributes()));
        SingleObserver<UserAtributes> testObserver = new SingleObserver<UserAtributes>() {
            public UserAtributes result=null;
           public int code=0;

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(UserAtributes value) {
                code = 2;
result = value;

            }

            @Override
            public void onError(Throwable e) {

            }
        };
        Single<Atributes> o1 = repository.buildPoiAtributesSingle("3");
        //todo
        Single<UserAtributes> o2 = Single.just(generateUserAtributes(0,0, 0, 0, 0, 0, 0));

        Single<UserAtributes> single= Single.zip(o1, o2, new BiFunction<Atributes, UserAtributes, UserAtributes>() {
            @Override
            public UserAtributes apply(Atributes atributes, UserAtributes userAtributes)  {
                return repository.calculateNewAtributesForUser(atributes, userAtributes, 0, 5);


            }}
            //subscribe the zip to the observer argument
        );
        testObserver = single.subscribeWith(testObserver);
        Assert.assertNotNull(single.blockingGet());
        Assert.assertEquals(generateAtributes().getUsersAggragateRating(), single.blockingGet().getUsersAggragateRating(),0);
        Assert.assertEquals(generateAtributes().getPrice_level(), single.blockingGet().getPrice_level(), 0);




    }

  @Test
  public void testBuildUserAtributesSingle() {}

@Test
    public void

testUpdateUserProfileByRate() throws MalformedURLException {
    DefaultObserver<String> testObserver = new DefaultObserver<String>() ;
    UserAtributes newAtributes = repository.calculateNewAtributesForUser(generateAtributes(), userOldAtributes, userCount, rate);
    given(mMockPlacesRepositoryImpl.getPoiById("3")).willReturn(Single.just(generateAtributes()));

    given(atributesRepository.getUserAtributes("3")).willReturn(Single.just(userOldAtributes));
//given(atributesRepository.updateNewAtributes(newAtributes, "3")).willReturn(Single.just("atributes: " + newAtributes.toString()+ "saved for user: "+"3"));

        Observable<String> observable = this.repository.updateUserProfileByRate("3", "3",rate, executionThread);


     observable.subscribeWith(testObserver);
    Assert.assertNotNull(observable.blockingFirst());
    Assert.assertEquals("atributes: " + newAtributes.toString()+ "saved for user: "+"3",observable.blockingFirst());

    }
@Test
    public void testFromAtributesToVector() {
    }
    public static Atributes generateAtributes(){
        Atributes g_atributes = new Atributes();
        g_atributes.setAlwaysOpen(false);
        g_atributes.setAmusement_park(false);
        g_atributes.setAquarium(false);
        g_atributes.setCafe(true);
        g_atributes.setArt_gallery(true);
        g_atributes.setPrice_level(3/5);
        g_atributes.setUsersAggragateRating(4/5);

        return g_atributes;

    }
    public static UserAtributes generateUserAtributes(double a1, double a2, double a3, double a4, double a5, double a6, double a7){
        UserAtributes atributes = new UserAtributes();
        atributes.setAlwaysOpen(a1);
        atributes.setAmusement_park(a2);
        atributes.setAquarium(a3);
        atributes.setCafe(a4);
        atributes.setArt_gallery(a5);
        atributes.setPrice_level(a6);
        atributes.setUsersAggragateRating(a7);
        return atributes;
    }
    public static boolean sameAtributes(UserAtributes actual, UserAtributes expected){
        if((actual.getUsersAggragateRating()==expected.getUsersAggragateRating())&&
        (actual.getPrice_level()==expected.getPrice_level())
                &&(actual.getAlwaysOpen()==expected.getAlwaysOpen())&&
                (actual.getArt_gallery()==expected.getArt_gallery())
                &&(actual.getAmusement_park()==expected.getAmusement_park()&&
                (actual.getAquarium())==expected.getAquarium())
                &&(actual.getCafe()==expected.getCafe())){
            return true;
        }
        return false;
    }}
     class UpdateUserProfileByRateObserverTest extends  DisposableObserver<String> {

        private Object result;

        public Object getResult() {
            return result;
        }

        @Override
        public void onNext(String value) {
            result = value;
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
     class IoThreadTest implements PostExecutionThread{

        @Override
        public Scheduler getScheduler() {
            return Schedulers.io();
                }
    }

    class GenerateAtributesPoiDetails  {
        public static PoiDetailsEntity newInstance(){
            ArrayList <PlaceOpeningHoursPeriod> list= new  ArrayList<PlaceOpeningHoursPeriod>();
            for (int i=0; i<6;i++){
                list.add(new PlaceOpeningHoursPeriod(new PlaceOpeningHoursPeriodDetail(i, "0000")));}
            String[] types =new String[]{"spa"};

            return new PoiDetailsEntity(new OpeningHours1(list), "3", 4.0, types, 3);

        }
    }



