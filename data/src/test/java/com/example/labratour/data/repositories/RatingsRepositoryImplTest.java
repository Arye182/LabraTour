package com.example.labratour.data.repositories;

import com.example.labratour.domain.Atributes;
import com.example.labratour.domain.UserAtributes;
import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.UserRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.MalformedURLException;

import io.reactivex.Single;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RatingsRepositoryImplTest {
     RatingsRepositoryImpl repository;
     Atributes atributes;
     UserAtributes userOldAtributes;
    @Mock
     PlacesRepositoryImpl mMockPlacesRepositoryImpl;

    @Mock
     UserRepository userRepository;

    @Mock
    private ExecutionThread executionThread;

    @Mock
    private PostExecutionThread postExecutionThread;

@Before
public void setUp() throws MalformedURLException {
    repository = new RatingsRepositoryImpl(userRepository, mMockPlacesRepositoryImpl, executionThread, postExecutionThread);

    // repository = new RatingsRepositoryImpl(userRepository, placesRepository, executionThread, postExecutionThread);
//        ioScheduler =  Schedulers.from(executionThread);
        atributes =  generateAtributes();
         userOldAtributes  = generateUserAtributes(0,0,0,1,1,3/5, 4/5);
//
//        items.add("y");
//        items2.add("u");
        given(mMockPlacesRepositoryImpl.getPoiById("3")).willReturn(Single.just(atributes));
        given(userRepository.getUserAtributes("3")).willReturn(Single.just(userOldAtributes));

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
    public void testBuildPoiAtributesSingle() {
    }

    public void testBuildUserAtributesSingle() {
    }


    public void testUpdateUserProfileByRate() {
    }

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
    }
}