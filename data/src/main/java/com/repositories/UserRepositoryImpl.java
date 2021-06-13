package com.repositories;

import com.Entity.UserEntity;
import com.Entity.mapper.UserDataMapper;
import com.datasource.CloudUserDataSource;
import com.datasource.UserDataSourceFactory;
import com.example.Entity.User;
import com.example.repositories.UserRepository;
import com.google.firebase.auth.AuthResult;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class UserRepositoryImpl implements UserRepository {
    private UserDataSourceFactory userDataSourceFactory;
    private UserDataMapper userDataMapper;
    private final CloudUserDataSource cloudUserDataSource;
    public UserRepositoryImpl(UserDataSourceFactory userDataSourceFactory) {
        this.cloudUserDataSource = userDataSourceFactory.createCloudDataSource();
        this.userDataMapper = new UserDataMapper();
        this.userDataSourceFactory = userDataSourceFactory;
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
    public Observable<User> login(final String email,
                                       final String password) {
        return this.cloudUserDataSource.login(new UserEntity(email, password))
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
