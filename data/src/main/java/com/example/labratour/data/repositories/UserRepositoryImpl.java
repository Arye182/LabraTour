package com.example.labratour.data.repositories;

import android.util.Log;

import com.example.labratour.data.Entity.UserEntity;
import com.example.labratour.data.Entity.mapper.UserDataMapper;
import com.example.labratour.data.datasource.CloudUserDataSource;
import com.example.labratour.data.datasource.UserEntityFirebaseStore;
import com.example.labratour.domain.Entity.UserDomain;
import com.example.labratour.domain.repositories.UserRepository;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class UserRepositoryImpl implements UserRepository {
  private  CloudUserDataSource cloudUserDataSource;

  private  UserEntityFirebaseStore userEntityFirebaseStore;
  //   private UserDataMapper userDataMapper;
  // private final CloudUserDataSource cloudUserDataSource;
  public UserRepositoryImpl(FirebaseAuth firebaseAuth, FirebaseDatabase database) {
    this.cloudUserDataSource = new CloudUserDataSource(firebaseAuth);
    // database.setPersistenceEnabled(true);

    this.userEntityFirebaseStore = new UserEntityFirebaseStore(database);
  }



    @Override
    public Observable<UserDomain> getUser(String userId, boolean fromServer) {
        return null;
    }

    @Override
  public Single<UserDomain> getUser(String userId) {
    return this.cloudUserDataSource.getUser(userId).map(new Function<UserEntity, UserDomain>() {
      @Override
      public UserDomain apply(UserEntity userEntity) throws Exception {
        return UserDataMapper.transform(userEntity);
      }
    });

  }

  @Override
  public Observable<UserDomain> login(final String email, final String password) {
    return this.cloudUserDataSource
        .login(email, password)
        .map(
            new Function<AuthResult, UserEntity>() {
              @Override
              public UserEntity apply(AuthResult authResult) throws Exception {
                return new UserDataMapper().transform(authResult);
              }
            })
        .map(
            new Function<UserEntity, UserDomain>() {
              @Override
              public UserDomain apply(UserEntity userEntity) throws Exception {
                return new UserDataMapper().transform(userEntity);
              }
            });
  }

    @Override
    public Observable updateUser(UserDomain userDomain, String response) throws InstantiationException, IllegalAccessException {
        return null;
    }

    @Override
    public void saveUser(UserDomain userDomain) {

    }
  public Observable updateUser(UserEntity userEntity, String response) {
    return this.userEntityFirebaseStore.updateUser(userEntity, response);
  }
//
//  @Override
//  public void saveUser(UserDomain userDomain) {}

  @Override
  public Observable<String> registerNewUser(String email, String password) {
    return this.cloudUserDataSource
        .register(email, password).map(new Function<AuthResult, String>() {
              @Override
              public String apply(@NotNull AuthResult authResult) throws Exception {
                return UserDataMapper.transformToId(authResult);
              }
            });
  }

  @Override
  public Observable<Void> saveNewUser(UserDomain userDomain, String id) {
    Log.i( "signup", "before calling userEntityFirebaseStore.createUserIfNotExists");
    return this.userEntityFirebaseStore.createUserIfNotExists(userDomain, id);
  }

}



