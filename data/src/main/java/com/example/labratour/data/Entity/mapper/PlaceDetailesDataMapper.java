package com.example.labratour.data.Entity.mapper;

import com.example.labratour.domain.Atributes;
import com.example.labratour.domain.Entity.Entity.PlaceOpeningHoursPeriod;
import com.example.labratour.domain.Entity.Entity.PoiDetailsEntity;
import com.example.labratour.domain.Entity.OpeningHours1;

import java.lang.reflect.Field;
import java.util.HashMap;

public class PlaceDetailesDataMapper {




  public Atributes transform(PoiDetailsEntity poiDetailsEntity) throws NoSuchFieldException {
    Atributes atributes = new Atributes();
    if (poiDetailsEntity != null) {
      atributes = new Atributes();
      atributes.setPrice_level((poiDetailsEntity.getPriceLevel()+1) / 5);
      atributes.setUsersAggragateRating(poiDetailsEntity.getRating() / 5);
      if(poiDetailsEntity.getOpeningHours()==null){
        atributes.setAlwaysOpen(false);
      }else{
      atributes.setAlwaysOpen(isAlwaysOpen(poiDetailsEntity.getOpeningHours()));}
      updateAtributesFields(poiDetailsEntity.getTypes(), atributes);
    }
    return atributes;
  }

  private void updateAtributesFields(String[] types, Atributes atributes) throws NoSuchFieldException {

if((types!=null)&&(types.length>1)){
      for(int i=0 ; i <types.length;i++){

     try{
       String fieldName = Atributes.class.getDeclaredField(types[i]).getName();
        Field field = Atributes.class.getDeclaredField(types[i]);
                field.setBoolean(atributes, true);
     }

     catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
          }
        }}}


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
