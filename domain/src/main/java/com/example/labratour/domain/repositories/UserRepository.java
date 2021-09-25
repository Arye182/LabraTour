package com.example.labratour.domain.repositories;

import com.example.labratour.domain.Entity.UserDomain;

import io.reactivex.Observable;
import io.reactivex.Single;


//@param userId The user id used to retrieve user data.
public interface UserRepository {
    public Observable<UserDomain> getUser(String userId, boolean fromServer);
    public Single<UserDomain> getUser(String userId);
    public Observable login(String email, String  password);
    public Observable updateUser(UserDomain userDomain, String response) throws InstantiationException, IllegalAccessException;
    public void saveUser(UserDomain userDomain);
    Observable<String> registerNewUser(String email , String password);

    Observable<Void> saveNewUser(UserDomain userDomain, String userId) ;

}
