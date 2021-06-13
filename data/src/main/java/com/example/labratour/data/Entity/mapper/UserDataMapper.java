package com.example.labratour.data.Entity.mapper;

import com.example.labratour.data.Entity.UserEntity;
import com.example.labratour.domain.Entity.User;
import com.google.firebase.auth.AuthResult;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserDataMapper {

  public static UserEntity transform(User user) {
   return null;
  }

  public static UserEntity transform(AuthResult authResult) {
    UserEntity userEntity = null;
    if (authResult != null) {
      userEntity = new UserEntity();
      userEntity.setUid(authResult.getUser().getUid());
      userEntity.setUsername(authResult.getUser().getDisplayName());
      userEntity.setUsername(authResult.getUser().getEmail());
    }
    return userEntity;
  }

  @Inject
  public static User transform(UserEntity userEntity) {
    User user = null;
    if (userEntity != null) {
      user = new User(userEntity.getUid());
      user.setEmail(userEntity.getEmail());
      user.setUserName(userEntity.getEmail());
    }

    return user;
  }
        }

