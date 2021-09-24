package com.example.labratour.data.repositories;

import com.example.labratour.data.Entity.UserEntity;
import com.example.labratour.data.Entity.mapper.UserDataMapper;
import com.example.labratour.data.datasource.CloudUserDataSource;
import com.example.labratour.data.datasource.UserEntityFirebaseStore;
import com.example.labratour.domain.Atributes;
import com.example.labratour.domain.Entity.UserDomain;
import com.example.labratour.domain.UserAtributes;
import com.example.labratour.domain.repositories.UserRepository;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.Vector;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class UserRepositoryImpl implements UserRepository {
  private final CloudUserDataSource cloudUserDataSource;

  private final UserEntityFirebaseStore userEntityFirebaseStore;
  //   private UserDataMapper userDataMapper;
  // private final CloudUserDataSource cloudUserDataSource;
  public UserRepositoryImpl(FirebaseAuth firebaseAuth, FirebaseDatabase database) {
    this.cloudUserDataSource = new CloudUserDataSource(firebaseAuth.getInstance());
    // database.setPersistenceEnabled(true);

    this.userEntityFirebaseStore = new UserEntityFirebaseStore(database);
  }
  //    public String addUser(User user) throws Exception {
  //        return cloudUserDataSource.insert(UserDataMapper.transform(user));
  //    }
  //    @Override
  //    public Observable<User> getUser(String userId, boolean fromServer) {
  //        return null;
  //    }

  @Override
  public Observable<UserDomain> getUser(String userId, boolean fromServer) {
    return null;
  }

  @Override
  public Single<Vector<Integer>> getUserAtributesVector(String userId) {
    return null;
  }
  public Single<UserAtributes> getUserAtributes(String userId) {
    return null;
  }

  @Override
  public Single<Void> updateNewAtributes(UserAtributes userAtributes, String userId) {
    return null;
  }

  @Override
  public Single<UserDomain> getUser(String userId) {
    return null;
  }

  @Override
  public Observable<UserDomain> login(final String email, final String password) {
    return this.cloudUserDataSource
        .login(new UserEntity(email, password))
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

  public Observable updateUser(UserDomain userDomain, String response) {
    return this.userEntityFirebaseStore.updateUser(UserDataMapper.transform(userDomain), response);
  }

  @Override
  public void saveUser(UserDomain userDomain) {}

  @Override
  public Observable registerNewUser(String email, String password) {
    return this.cloudUserDataSource
        .register(email, password)
        .map(
            new Function<AuthResult, String>() {
              @Override
              public String apply(AuthResult authResult) throws Exception {
                return authResult.getUser().getUid();
              }
            });
  }

  @Override
  public Observable signUp(String email, String password, String first_name, String last_name) {
    return this.userEntityFirebaseStore.createUserIfNotExists(
        new UserEntity(email, password, first_name, last_name));
  }

  @Override
  public Observable signUp() {
    return null;
  }

  @Override
  public Observable<Void> saveNewUnupdatedRatesToUser(Map<String, Integer> newRates) {
    return null;
  }

  @Override
  public Observable<Void> saveNewUser(UserDomain userDomain) {
    return this.userEntityFirebaseStore.createUserIfNotExists(UserDataMapper.transform(userDomain));
  }
  //    @Override
  //    public Observable<Void> saveNewUnupdatedRatesToUser(Map<String, Integer> newRates){
  //      return userEntityFirebaseStore.savenewUnupdatedRatesToCurrentUser();
  //    }
  public Single<Atributes> getuserAtributesById(String Id) throws MalformedURLException {
    return this.getUser(Id)
        .map(
            new Function<UserDomain, Atributes>() {
              @Override
              public Atributes apply(UserDomain userDomain) throws Exception {
                return UserDataMapper.transformToAtributes(userDomain);
              }
            });
  }

}



