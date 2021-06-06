package com.data.net;

import java.util.List;
import io.reactivex.Observable;
import Entity.UserEntity;

public interface RestApi {

 String API_BASE_URL =
          "https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture/";

  /** Api url for getting all users */
  String API_URL_GET_USER_LIST = API_BASE_URL + "users.json";
  /** Api url for getting a user profile: Remember to concatenate id + 'json' */
  String API_URL_GET_USER_DETAILS = API_BASE_URL + "user_";

  /**
   * Retrieves an {@link Observable} which will emit a List of {@link UserEntity}.
   */
  Observable<List<UserEntity>> userEntityList();

  /**
   * Retrieves an {@link Observable} which will emit a {@link UserEntity}.
  **/
  Observable<UserEntity> userEntityById(final int userId);
 }