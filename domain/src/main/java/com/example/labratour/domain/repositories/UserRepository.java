package com.example.labratour.domain.repositories;

import com.example.labratour.domain.Entity.UserDomain;
import com.example.labratour.domain.UserAtributes;

import java.util.Map;
import java.util.Vector;

import io.reactivex.Observable;
import io.reactivex.Single;


//@param userId The user id used to retrieve user data.
public interface UserRepository {
    public Observable<UserDomain> getUser(String userId, boolean fromServer);
    public Single<Vector<Integer>> getUserAtributesVector(String userId);
    public Single<UserAtributes> getUserAtributes(String userId);
public Single<Void> updateNewAtributes(UserAtributes userAtributes, String userId);
    public Single<UserDomain> getUser(String userId);
    public Observable login(String email, String  password);
    public Observable updateUser(UserDomain userDomain, String response);
    public void saveUser(UserDomain userDomain);
    Observable registerNewUser(String email , String password);
    Observable signUp(String email, String password, String first_name, String last_name);

    Observable signUp();
    public Observable<Void> saveNewUnupdatedRatesToUser(Map<String, Integer> newRates);
    Observable<Void> saveNewUser(UserDomain userDomain);
}
