package com.example.labratour.data.Entity.mapper;

import com.example.labratour.data.Entity.NearbyPlaceResult;
import com.example.labratour.data.Entity.UserEntity;
import com.example.labratour.domain.Entity.UserDomain;
import com.google.firebase.auth.AuthResult;

import java.util.List;

public class UserDataMapper {



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

  public static UserEntity transform(UserDomain userDomain) {
    UserEntity userEntity = null;
    if (userDomain != null) {
      if (userDomain.getUserId() == null) {
        userEntity = new UserEntity();
      } else {
        userEntity = new UserEntity(userDomain.getUserId());
      }
      userEntity.setUserName(userDomain.getUserName());
      userEntity.setAtributes(userDomain.getAtributes());
      userEntity.setEmail(userDomain.getEmail());
      userEntity.setCountRate(userDomain.getCountRates());
      userEntity.setAddress(userDomain.getAddress());
      userEntity.setPhone(userDomain.getPhone());
    }
    return userEntity;

  }

  public static UserDomain transform(UserEntity userEntity) {
    UserDomain userDomain = null;
    if ((userEntity != null)&&(userEntity.getUserId()!=null)) {

      userDomain = new UserDomain(userEntity.getUserId());
      userDomain.setEmail(userEntity.getEmail());
      userDomain.setUserName(userEntity.getUserName());
    }
    return userDomain;
  }

    public List<String> transform(List<NearbyPlaceResult> result) {
      List<String> placesIds = null;
      for (NearbyPlaceResult n :result) {
        placesIds.add(new String(n.getId()));
      }
      return placesIds;
    }

//  public static UserView transform(User user) {
//    UserView userView = null;
//    if (user != null) {
//
//      userView =  new UserView(user.getUserId(), null, null, null, null, null, 0, null, 0);
//    }
//    return userView;
//  }
    }

