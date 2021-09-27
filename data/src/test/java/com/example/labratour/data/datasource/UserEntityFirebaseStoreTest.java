package com.example.labratour.data.datasource;

import com.example.labratour.domain.Atributes;
import com.example.labratour.domain.UserAtributes;

import junit.framework.TestCase;

import org.junit.Before;

import java.net.MalformedURLException;

public class UserEntityFirebaseStoreTest extends TestCase {
  Atributes atributes;
  UserAtributes userOldAtributes;
  UserAtributes userExpectedNewAtributes;
  int userCount;
  int rate;




  @Before
  public void setUp() throws MalformedURLException, NoSuchFieldException {


    // repository = new RatingsRepositoryImpl(userRepository, placesRepository, executionThread,
    // postExecutionThread);
    //        ioScheduler =  Schedulers.from(executionThread);


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