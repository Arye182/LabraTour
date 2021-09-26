package com.example.labratour.data.datasource;

import com.example.labratour.data.repositories.AtributesRepository;
import com.example.labratour.data.repositories.PlacesRepositoryImpl;
import com.example.labratour.data.repositories.RatingsRepositoryImpl;
import com.example.labratour.domain.Atributes;
import com.example.labratour.domain.UserAtributes;
import com.example.labratour.domain.executors.ExecutionThread;
import com.example.labratour.domain.executors.PostExecutionThread;
import com.example.labratour.domain.repositories.UserRepository;

import junit.framework.TestCase;

import org.junit.Before;
import org.mockito.Mock;

import java.net.MalformedURLException;

import io.reactivex.Single;

import static org.mockito.BDDMockito.given;

public class UserEntityFirebaseStoreTest extends TestCase {
  RatingsRepositoryImpl repository;
  Atributes atributes;
  UserAtributes userOldAtributes;
  UserAtributes userExpectedNewAtributes;
  int userCount;
  int rate;
  @Mock
  PlacesRepositoryImpl mMockPlacesRepositoryImpl;

  @Mock
  UserRepository userRepository;
  @Mock
  AtributesRepository atributesRepository;

  @Mock
  private ExecutionThread executionThread;

  @Mock
  private PostExecutionThread postExecutionThread;

  @Before
  public void setUp() throws MalformedURLException, NoSuchFieldException {
    repository =
        new RatingsRepositoryImpl(
            userRepository,
            mMockPlacesRepositoryImpl,
            atributesRepository,
            executionThread,
            postExecutionThread);

    // repository = new RatingsRepositoryImpl(userRepository, placesRepository, executionThread,
    // postExecutionThread);
    //        ioScheduler =  Schedulers.from(executionThread);

    userCount = 2;
    rate = 4;
    given(mMockPlacesRepositoryImpl.getPoiById("3")).willReturn(Single.just(atributes));
    given(atributesRepository.getUserAtributes("3")).willReturn(Single.just(userOldAtributes));
    given(repository.calculateNewAtributesForUser(atributes, userOldAtributes, userCount, rate))
        .willReturn(userExpectedNewAtributes);
    //  given(userRepository.updateNewAtributes(userExpectedNewAtributes, "3")).willReturn(new
    // Single<Void>() {

  }
    // useCase.execute( subscriber, new GetNearbyPlacesUseCase.RequestInput("3", "4"));

  public void testCreateUserIfNotExists() {}

  public void testUpdateUser() {}

  public void testGetUsers() {}

  public void testGetUserRealtime() {

  }
}