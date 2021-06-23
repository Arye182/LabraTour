package com.example.labratour.data.Entity.mapper;

import com.example.labratour.data.Entity.UserEntity;
import com.example.labratour.domain.Entity.User;
import com.google.firebase.auth.AuthResult;


public class UserDataMapper {

  public static UserEntity transform(User user) {
   return null;
  }

  public static UserEntity transform(AuthResult authResult) {
    UserEntity userEntity = null;
    if (authResult != null) {
      userEntity = new UserEntity();
      userEntity.setUserId(authResult.getUser().getUid());
      userEntity.setUserName(authResult.getUser().getDisplayName());
      userEntity.setUserName(authResult.getUser().getEmail());
    }
    return userEntity;
  }


  public static User transform(UserEntity userEntity) {
    User user = null;
    if (userEntity != null) {
      user = new User(userEntity.getUserId());
      user.setEmail(userEntity.getEmail());
      user.setUserName(userEntity.getUserName());
    }

    return user;
  }
  public static UserView transform(UserEntity userEntity) {
    UserView userView = null;
    if (userEntity != null) {
      user = new User(userEntity.getUserId());
      user.setEmail(userEntity.getEmail());
      user.setUserName(userEntity.getUserName());
    }

    return user;
        }

