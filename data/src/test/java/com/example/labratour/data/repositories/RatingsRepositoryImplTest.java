package com.example.labratour.data.repositories;

import com.example.labratour.data.Entity.OpeningHours;
import com.example.labratour.data.Entity.PlaceOpeningHoursPeriod;
import com.example.labratour.data.Entity.PlaceOpeningHoursPeriodDetail;
import com.example.labratour.data.Entity.PoiDetailsEntity;
import com.example.labratour.data.net.MapsGoogleApiService;
import com.example.labratour.data.net.RestApi;
import com.example.labratour.data.utils.JobExecutor;
import com.example.labratour.domain.Atributes;
import com.example.labratour.domain.UserAtributes;
import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;

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
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

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

    @Mock
    private ExecutionThread executionThread;


    private PostExecutionThread postExecutionThread;
    private PoiDetailsEntity poiDetailsEntity;

    @Before
public void setUp() throws MalformedURLException, NoSuchFieldException {
    repository = new RatingsRepositoryImpl( mMockPlacesRepositoryImpl, atributesRepository);
    // repository = new RatingsRepositoryImpl(userRepository, placesRepository, executionThread, postExecutionThread);
//        ioScheduler =  Schedulers.from(executionThread);
    this.postExecutionThread = new IoThreadTest();
    this.executionThread = new JobExecutor();
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
    public void testBuildPoiAtributesSingle() throws MalformedURLException {
    given(mMockRestApi.getPlaceById("3")).willReturn(Single.just(GenerateAtributesPoiDetails.newInstance()));
    repository.buildPoiAtributesSingle("3").test().assertValue((Predicate<Atributes>) Single.just(GenerateAtributesPoiDetails.newInstance()));

        }

  @Test
  public void testBuildUserAtributesSingle() {}

@Test
    public void testUpdateUserProfileByRate() throws MalformedURLException {
        given(mMockPlacesRepositoryImpl.getPoiById("3")).willReturn(Single.just(atributes));
        given(atributesRepository.getUserAtributes("3")).willReturn(Single.just(userOldAtributes));
        Observable<String> observable = this.repository.updateUserProfileByRate("3", "3",4, );
        observable.subscribeOn(Schedulers.from(executionThread)).observeOn(this.postExecutionThread.getScheduler());
        UpdateUserProfileByRateObserverTest observer = observable.subscribeWith(testObserver);

        Assert.assertTrue(observer.isDisposed());
        Assert.assertNotNull(observer.getResult());


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
        ArrayList list = new  ArrayList<PlaceOpeningHoursPeriod>();
        for (int i=0; i<7;i++){
            list.add(new PlaceOpeningHoursPeriod(null,new PlaceOpeningHoursPeriodDetail(i, "0000")));}
        ArrayList<String> types = new ArrayList<>();
        types.add("resturant");
        types.add("spa");
        return new PoiDetailsEntity(  new OpeningHours(list), "3", 4.0, types, 3);

    }
}

