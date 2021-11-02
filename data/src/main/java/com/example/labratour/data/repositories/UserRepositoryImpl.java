package com.example.labratour.data.repositories;

import android.util.Log;

import com.example.labratour.data.Entity.mapper.UserDataMapper;
import com.example.labratour.data.datasource.UserAuth;
import com.example.labratour.data.datasource.UserEntityFirebaseStore;
import com.example.labratour.domain.Entity.UserDomain;
import com.example.labratour.domain.repositories.UserRepository;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class UserRepositoryImpl implements UserRepository {
  private UserAuth userAuth;

  private final UserEntityFirebaseStore userEntityFirebaseStore;
  //   private UserDataMapper userDataMapper;
  // private final CloudUserDataSource cloudUserDataSource;
  public UserRepositoryImpl(FirebaseAuth firebaseAuth, FirebaseDatabase database) {
    this.userAuth = new UserAuth(firebaseAuth);
    // database.setPersistenceEnabled(true);

    this.userEntityFirebaseStore = new UserEntityFirebaseStore(database);
  }




    @Override
  public Single<UserDomain> getUser(String userId) {
    return this.userEntityFirebaseStore.getUser(userId);
            //.map(new Function<UserDomain, UserDomain>() {
//      @Override
//      public UserDomain apply(UserEntity userEntity) throws Exception {
//        return UserDataMapper.transform(userEntity);
//      }


  }

  @Override
  public Observable<UserDomain> login(final String email, final String password) {
    return this.userAuth
        .login(email, password)
        .map(
            new Function<AuthResult, UserDomain>() {
              @Override
              public UserDomain apply(AuthResult authResult) throws Exception {
                return new UserDomain(Objects.requireNonNull(authResult.getUser()).getUid());
              }
            });
//        .map(
//            new Function<UserEntity, UserDomain>() {
//              @Override
//              public UserDomain apply(UserEntity userEntity) throws Exception {
//                return UserDataMapper.transform(userEntity);
//              }
//            });
  }


  @Override
  public Observable<String> registerNewUser(String email, String password) {
    return this.userAuth
        .register(email, password).map(new Function<AuthResult, String>() {
              @Override
              public String apply(@NotNull AuthResult authResult) throws Exception {
                return UserDataMapper.transformToId(authResult);
              }
            });
  }

    @Override
    public Observable<UserDomain> getUser(String userId, boolean fromServer) {
        return null;
    }

    @Override
  public Observable<String> saveNewUser(UserDomain userDomain, String id) {
    Log.i( "signup", "before calling userEntityFirebaseStore.createUserIfNotExists");
    return this.userEntityFirebaseStore.createUserIfNotExists(userDomain, id);
  }

}



