package com.example.labratour.data.Entity.mapper;

import android.os.Looper;
import android.util.Log;

import com.example.labratour.data.Entity.UserEntity;
import com.example.labratour.domain.Entity.UserDomain;
import com.example.labratour.domain.UserAtributes;
import com.google.firebase.auth.AuthResult;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserDataMapper {

    public static String transformToId(AuthResult register){
            return Objects.requireNonNull(register.getUser()).getUid();

    }



  public static UserEntity transform(UserDomain userDomain)  {
      UserEntity userEntity = new UserEntity(userDomain.getUserId());
      try {
        userEntity.setEmail(userDomain.getEmail());
        userEntity.setUserName(userDomain.getUserName());
        userEntity.setFirst_name(userDomain.getFirst_name());
        userEntity.setLast_name(userDomain.getLast_name());
        userEntity.setAtributes(
                userDomain.getAtributes());
      } catch (Exception exception){
exception.printStackTrace();}
      return  userEntity;}



    public static UserDomain transform(UserEntity userEntity) {
    UserDomain userDomain = null;
        if ((userEntity != null)&&(userEntity.getUserId()!=null)){
            userDomain = new UserDomain(userEntity.getUserId());
            try{
               // userDomain = new UserDomain(userEntity.getUserId());
                userDomain.setEmail(userEntity.getEmail());
                userDomain.setUserName(userEntity.getUserName());
                userDomain.setFirst_name(userEntity.getFirst_name());
                userDomain.setLast_name(userEntity.getLast_name());

                userDomain.setAtributes(userEntity.getAtributes());
            } catch (Exception e){
                e.printStackTrace();
                return userDomain;}
    }
        return userDomain;
        }



public static HashMap<String, Object> transform(UserAtributes userAtributes) {
    Log.e("UpdateUseCase","inside transform called from apply on zip run on:" + Looper.myLooper().toString(), new Throwable("couldnt print my looper"));


    HashMap<String, Object> atributesDict = new HashMap<>();
try{
          atributesDict.put("ratesCounter",userAtributes.getRatesCounter());
          atributesDict.put("pricelevel", userAtributes.getPrice_level());
          atributesDict.put("useraggragate_rating", userAtributes.getUsersAggragateRating());
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
          atributesDict.put("touristAttraction", userAtributes.getTourist_attraction());
          atributesDict.put("zoo",userAtributes.getZoo() );
          atributesDict.put("gym",userAtributes.getGym() );
          return atributesDict;
} catch (Throwable throwable){
    throwable.printStackTrace();
    return atributesDict;
}}




  public static UserAtributes transform(HashMap<String, Object> stringObjectHashMap) {
    UserAtributes userAtributes = new UserAtributes();
    for (Map.Entry<String, Object> entry: stringObjectHashMap.entrySet()) {
        if(entry.getKey()!="RatesCounter"){
        try{
          Field field = UserAtributes.class.getDeclaredField(entry.getKey());
          Class<?> targetType = field.getType();


            field.setDouble(userAtributes, (Double) entry.getValue());

          Object objectValue = targetType.newInstance();
          field.set(userAtributes, entry.getValue());
    } catch (NoSuchFieldException e) {
          e.printStackTrace();
          return userAtributes;
        } catch (IllegalAccessException e) {
          e.printStackTrace();
          return userAtributes;
        } catch (InstantiationException e) {
          e.printStackTrace();
          return userAtributes;
        }
    }}
    return userAtributes;
  }

}

