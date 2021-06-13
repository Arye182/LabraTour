package com.example.labratour.domain.repositories;

import com.example.labratour.domain.Entity.User;

import io.reactivex.Observable;


//@param userId The user id used to retrieve user data.
public interface UserRepository extends Repository {
    public Observable<User> getUser(String userId, boolean fromServer);
    public Observable login(String password, String  email);

    public void saveUser(User user);
            }
