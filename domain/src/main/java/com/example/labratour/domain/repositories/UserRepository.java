package com.example.labratour.domain.repositories;

import com.example.labratour.domain.Entity.UserDomain;

import io.reactivex.Observable;
import io.reactivex.Single;


//@param userId The user id used to retrieve user data.
public interface UserRepository {

     Observable<UserDomain> login(String email, String  password);
    Observable<String> registerNewUser(String email , String password);
    public Observable<UserDomain> getUser(String userId, boolean fromServer);
     Single<UserDomain> getUser(String userId);

    Observable<Void> saveNewUser(UserDomain userDomain, String userId) ;

}
