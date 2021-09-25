package com.example.labratour.data.Entity.mapper;

import com.example.labratour.data.Entity.UserEntity;
import com.example.labratour.domain.Entity.UserDomain;
import com.example.labratour.domain.UserAtributes;
import com.google.firebase.auth.AuthResult;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class UserDataMapper {

 

  public static UserEntity transform(AuthResult authResult) {
    UserEntity userEntity = null;
    if (authResult != null) {
      userEntity = new UserEntity(authResult.getUser().getUid());
      //userEntity.setUserId(authResult.getUser().getUid());
     // userEntity.setUserName(authResult.getUser().getDisplayName());
      userEntity.setUserName(authResult.getUser().getEmail());
    }
    return userEntity;
  }

  public static UserEntity transform(UserDomain userDomain) {
    UserEntity userEntity = null;
    if (userDomain != null) {
      try {
        userEntity = new UserEntity(userDomain.getUserId());
        userEntity.setUserName(userDomain.getUserName());
        // userEntity.setAtributes(transform(userDomain.getAtributes()));
        userEntity.setEmail(userDomain.getEmail());
        // userEntity.setCountRate(userDomain.getCountRates());
        // userEntity.setAddress(userDomain.getAddress());
        // userEntity.setPhone(userDomain.getPhone());}
      } catch (Exception e) {
        throw e;
        }
    }
    return userEntity;

  }



    public static UserDomain transform(UserEntity userEntity) {
    UserDomain userDomain = null;
        if ((userEntity != null)){
            userDomain = new UserDomain(userEntity.getUserId());
            try{
                userDomain = new UserDomain(userEntity.getUserId());
                userDomain.setEmail(userEntity.getEmail());
                userDomain.setUserName(userEntity.getUserName());
                userDomain.setAtributes(transform(userEntity.getAtributes()));
                userDomain.setFirst_name(userEntity.getFirst_name());
                userDomain.setLast_name(userEntity.getLast_name());
                userDomain.setAddress(userEntity.getAddress());
                userDomain.setPhone(userEntity.getPhone());
            } catch (Exception e){
                e.printStackTrace();
                return userDomain;}
    }
        return userDomain;
        }



public static HashMap<String, Object> transform(UserAtributes userAtributes) throws Throwable {

          HashMap<String, Object> atributesDict = new HashMap<>();

          atributesDict.put("ratesCounter",userAtributes.getRatesCounter());
          atributesDict.put("pricelevel", userAtributes.getPrice_level());
          atributesDict.put("useraggragaterating", userAtributes.getUsersAggragateRating());
          atributesDict.put("always_open", userAtributes.getAlwaysOpen());
          atributesDict.put("casino", userAtributes.getCasino());
          atributesDict.put("cafe", userAtributes.getCafe());
          atributesDict.put("restaurant", userAtributes.getRestaurant());
          atributesDict.put("rv_park", userAtributes.getRv_park());
          atributesDict.put("shopping_mall", userAtributes.getShopping_mall());
          atributesDict.put("amusement_park", userAtributes.getAmusement_park());
          atributesDict.put("aquarium",userAtributes.getAquarium());
          atributesDict.put("art_gallery",userAtributes.getArt_gallery());
          atributesDict.put("campground",userAtributes.getCampground() );
          atributesDict.put("night_club",userAtributes.getNight_club() );
          atributesDict.put("painter", userAtributes.getPainter());
          atributesDict.put("movie_theater", userAtributes.getMovie_theater());
          atributesDict.put("museum",userAtributes.getMuseum() );
          atributesDict.put("spa", userAtributes.getSpa());
          atributesDict.put("stadium",userAtributes.getStadium() );
          atributesDict.put("tourist_attraction", userAtributes.getTourist_attraction());
          atributesDict.put("zoo",userAtributes.getZoo() );
          atributesDict.put("gym",userAtributes.getGym() );
          return atributesDict;
}




  public static UserAtributes transform(HashMap<String, Object> stringObjectHashMap) {
    UserAtributes userAtributes = new UserAtributes();
    for (Map.Entry<String, Object> entry: stringObjectHashMap.entrySet()) {
        try{
          Field field = UserAtributes.class.getField(entry.getKey());
          Class<?> targetType = field.getType();
          if(targetType.isPrimitive()){
            field.setDouble(userAtributes, (Double) entry.getValue());
          }
          Object objectValue = targetType.newInstance();
          field.set(userAtributes, entry.getValue());
    } catch (NoSuchFieldException e) {
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InstantiationException e) {
          e.printStackTrace();
        }
    }
    return userAtributes;
  }

}

