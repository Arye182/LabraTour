package com.example.labratour.data.repositories;

import com.example.labratour.data.Entity.UserEntity;
import com.example.labratour.data.Entity.mapper.UserDataMapper;
import com.example.labratour.data.datasource.CloudUserDataSource;
import com.example.labratour.data.datasource.UserDataSourceFactory;
import com.example.labratour.data.di.FirebaseContainer;
import com.example.labratour.domain.Entity.User;
import com.example.labratour.domain.repositories.UserRepository;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class UserRepositoryImpl implements UserRepository {
    private UserDataSourceFactory userDataSourceFactory;
    private final CloudUserDataSource cloudUserDataSource;
    private FirebaseContainer firebaseContainer;
 //   private UserDataMapper userDataMapper;
    //private final CloudUserDataSource cloudUserDataSource;
    public UserRepositoryImpl(FirebaseAuth firebaseContainer) {
        this.userDataSourceFactory = new UserDataSourceFactory(FirebaseAuth.getInstance());
        this.cloudUserDataSource = userDataSourceFactory.createCloudDataSource();
      //  this.userDataMapper = new UserDataMapper();

  }
//    public String addUser(User user) throws Exception {
//        return cloudUserDataSource.insert(UserDataMapper.transform(user));
//    }
//    @Override
//    public Observable<User> getUser(String userId, boolean fromServer) {
//        return null;
//    }

    @Override
    public Observable<User> getUser(String email, boolean fromServer) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
// See the UserRecord reference doc for the contents of userRecord.
        System.out.println("Successfully fetched user data: " + userRecord.getEmail());
    }

    @Override
    public Observable<User> login(final String email,
                                       final String password) {
        return this.cloudUserDataSource.login(new UserEntity(email, password))
                .map(new Function<AuthResult, UserEntity>() {
                    @Override
                    public UserEntity apply(AuthResult authResult) throws Exception {
                        return new UserDataMapper().transform(authResult);
                    }
                })
                .map(new Function<UserEntity, User>() {
                    @Override
                    public User apply(UserEntity userEntity) throws Exception {
                        return new UserDataMapper().transform(userEntity);
                    }
                });
    }

    @Override
    public Observable addRate(String emeil, String ratedPoiId, int rate) {
        return null;
    }

    @Override
    public void saveUser(User user) {

    }

//    @Override
//    public void saveUser(User user) {
//
//    }
}
