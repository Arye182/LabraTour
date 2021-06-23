package com.example.labratour.data.repositories;

import com.example.labratour.data.Entity.UserEntity;
import com.example.labratour.data.Entity.mapper.UserDataMapper;
import com.example.labratour.data.datasource.CloudUserDataSource;
import com.example.labratour.data.datasource.UserEntityFirebaseStore;
import com.example.labratour.domain.Entity.User;
import com.example.labratour.domain.repositories.UserRepository;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class UserRepositoryImpl implements UserRepository
{
    private final CloudUserDataSource cloudUserDataSource;

    private final UserEntityFirebaseStore userEntityFirebaseStore;
 //   private UserDataMapper userDataMapper;
    //private final CloudUserDataSource cloudUserDataSource;
    public UserRepositoryImpl(FirebaseAuth firebaseAuth) {
        this.cloudUserDataSource = new CloudUserDataSource(firebaseAuth.getInstance());
        this.userEntityFirebaseStore = new UserEntityFirebaseStore();
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
                        return new UserDataMapper().transform(authResult);
                    }
                })
                .map(new Function<UserEntity, User>() {
                    @Override
                    public User apply(UserEntity userEntity) throws Exception {
                        return new UserDataMapper().transform(userEntity);
                    }
                }).map(new Function<User, UserView>() {
                    @Override
                    public UserView apply(User user) throws Exception {
                        return new UserDataMapper().transform(user);
                    }
                });
    }



    @Override
    public void saveUser(User user) {

    }
    }



//    @Override
//    public void saveUser(User user) {
//
//    }

