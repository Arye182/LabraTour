package com.example.labratour.domain.repositories;

import com.example.labratour.domain.Entity.User;

import io.reactivex.Observable;


//@param userId The user id used to retrieve user data.
public interface UserRepository {
    public Observable<User> getUser(String userId, boolean fromServer);
    public Observable login(String email, String  password);

    public void saveUser(User user);

            }
