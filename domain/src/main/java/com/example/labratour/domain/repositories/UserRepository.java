package com.example.labratour.domain.repositories;

import com.example.labratour.domain.Entity.User;

import io.reactivex.Observable;


//@param userId The user id used to retrieve user data.
public interface UserRepository {
    public Observable<User> getUser(String userId, boolean fromServer);
    public Observable login(String email, String  password);
    public Observable updateUser(User user, String response);
    public void saveUser(User user);

    Observable signUp(String email, String password, String first_name, String last_name);

}
