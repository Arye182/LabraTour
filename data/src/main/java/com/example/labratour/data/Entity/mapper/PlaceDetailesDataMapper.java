package com.example.labratour.data.Entity.mapper;

import android.os.Looper;
import android.util.Log;

import com.example.labratour.domain.Atributes;
import com.example.labratour.domain.Entity.Entity.PlaceOpeningHoursPeriod;
import com.example.labratour.domain.Entity.Entity.PoiDetailsEntity;
import com.example.labratour.domain.Entity.OpeningHours1;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

public class PlaceDetailesDataMapper {




  public Atributes transform(PoiDetailsEntity poiDetailsEntity) {
    Log.i("looper", "transform: "+(Looper.myLooper() == Looper.getMainLooper())+"");
    Atributes atributes = null;
    if (poiDetailsEntity != null) {
      atributes = new Atributes();
      atributes.setPrice_level((poiDetailsEntity.getPriceLevel()+1) / 5);
      atributes.setUsersAggragateRating(poiDetailsEntity.getRating() / 5);
      atributes.setAlwaysOpen(isAlwaysOpen(poiDetailsEntity.getOpeningHours()));
      updateAtributesFields(poiDetailsEntity.getTypes(), atributes);
    }
    return atributes;
  }

  private void updateAtributesFields(String[] types, Atributes atributes) {
    Log.i("looper", "updateAtributesFields: ");
    Looper.myLooper();
    Looper.getMainLooper();

    Atributes atributes1 = new Atributes();
    atributes1.setPrice_level(atributes.getPrice_level());
    atributes1.setUsersAggragateRating(atributes.getUsersAggragateRating());
    atributes1.setAlwaysOpen(atributes.isAlwaysOpen());
    try {
      //   Map<String, Object> myObjectAsDict = new HashMap<>();
      Field[] allFields = Atributes.class.getDeclaredFields();
      for (Field field : allFields) {
        String fieldName = field.getName();
        if (Arrays.asList(fieldName).contains(fieldName)) {
          try {
            field.setBoolean(atributes1, true);
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
        }}
    } catch (Exception e) {
      e.printStackTrace();
    }
    }

  private boolean isAlwaysOpen(OpeningHours1 periods) {
    int sum = 0;

    for (PlaceOpeningHoursPeriod period : periods.getPeriods()) {

      if (period.getClose() == null) {
        if ((period.getOpen().getDay() == 0) && (period.getOpen().getTime() == "0000")) {
          sum++;
        }
      }
    }
    if (sum == 7) {
      return true;
    }
    return false;
  }

  public static HashMap<String, Object> transform(Atributes Atributes) {

    HashMap<String, Object> atributesDict = new HashMap<>();
    try{
      atributesDict.put("pricelevel", Atributes.getPrice_level());
      atributesDict.put("useraggragate_rating", Atributes.getUsersAggragateRating());
      atributesDict.put("always_open", Atributes.isAlwaysOpen());
      atributesDict.put("casino", Atributes.isCasino());
      atributesDict.put("cafe", Atributes.isCafe());
      atributesDict.put("restaurant", Atributes.isRestaurant());
      atributesDict.put("rv_park", Atributes.isRv_park());
      atributesDict.put("shopping_mall", Atributes.isShopping_mall());
      atributesDict.put("amusement_park", Atributes.isAmusement_park());
      atributesDict.put("aquarium",Atributes.isAquarium());
      atributesDict.put("art_gallery",Atributes.isArt_gallery());
      atributesDict.put("campground",Atributes.isCampground() );
      atributesDict.put("night_club",Atributes.isNight_club() );
      atributesDict.put("painter", Atributes.isPainter());
      atributesDict.put("movie_theater", Atributes.isMovie_theater());
      atributesDict.put("museum",Atributes.isMuseum() );
      atributesDict.put("spa", Atributes.isSpa());
      atributesDict.put("stadium",Atributes.isStadium() );
      atributesDict.put("touristAttraction", Atributes.isTourist_attraction());
      atributesDict.put("zoo",Atributes.isZoo() );
      atributesDict.put("gym",Atributes.isGym()  );
      return atributesDict;
    } catch (Throwable throwable){
      throwable.printStackTrace();
      return atributesDict;
    }}



}
