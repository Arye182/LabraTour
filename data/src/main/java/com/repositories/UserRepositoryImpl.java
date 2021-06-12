package com.repositories;

import com.Entity.UserEntity;
import com.Entity.mapper.UserDataMapper;
import com.example.Entity.User;
import com.example.repositories.UserRepository;
import com.google.firebase.auth.AuthResult;
import com.repositories.datasource.CloudUserDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import repositories.datasource.UserDataSourceFactory;

@Singleton
public class UserRepositoryImpl implements UserRepository {
    private UserDataSourceFactory userDataSourceFactory;
    private UserDataMapper userDataMapper;
    private final CloudUserDataSource cloudUserDataSource;
    @Inject
    public UserRepositoryImpl(CloudUserDataSource cloudUserDataSource) {
        this.cloudUserDataSource = userDataSourceFactory.createCloudDataSource();
        this.userDataMapper = new UserDataMapper();
  }
//    public String addUser(User user) throws Exception {
//        return cloudUserDataSource.insert(UserDataMapper.transform(user));
//    }
//    @Override
//    public Observable<User> getUser(String userId, boolean fromServer) {
//        return null;
//    }

    @Override
    public Observable<User> getUser(String userId, boolean fromServer) {
        return null;
    }

    @Override
    public Observable<User> login(final String password,
                                       final String email) {
        return this.cloudUserDataSource.login(new UserEntity(password, email))
                .map(new Function<AuthResult, UserEntity>() {
                    @Override
                    public UserEntity apply(AuthResult authResult) throws Exception {
                        return UserDataMapper.transform(authResult);
                    }
                })
                .map(new Function<UserEntity, User>() {
                    @Override
                    public User apply(UserEntity userEntity) throws Exception {
                        return UserDataMapper.transform(userEntity);
                    }
                });
    }

    @Override
    public void saveUser(User user) {

    }

//    @Override
//    public void saveUser(User user) {
//
//    }
}
