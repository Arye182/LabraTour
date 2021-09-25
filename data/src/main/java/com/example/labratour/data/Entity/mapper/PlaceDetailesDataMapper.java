package com.example.labratour.data.Entity.mapper;

import com.example.labratour.data.Entity.PlaceOpeningHoursPeriod;
import com.example.labratour.data.Entity.PoiDetailsEntity;
import com.example.labratour.domain.Atributes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Vector;

public class PlaceDetailesDataMapper {
  public Vector<Integer> transform(Atributes atributes)
        // todo
      {
    return new Vector<>(8, 1);
  }

  public Atributes transform(PoiDetailsEntity poiDetailsEntity) {
    Atributes atributes = null;
    if (poiDetailsEntity != null) {
      atributes = new Atributes();
      atributes.setPrice_level((poiDetailsEntity.getPriceLevel()+1) / 5);
      atributes.setUsersAggragateRating(poiDetailsEntity.getRating() / 5);
      atributes.setAlwaysOpen(isAlwaysOpen(poiDetailsEntity.getOpeningHours().getPeriods()));
      updateAtributesFields(poiDetailsEntity.getTypes(), atributes);
    }
    return atributes;
  }

  private void updateAtributesFields(ArrayList<String> types, Atributes atributes) {
    Atributes atributes1 = new Atributes();
    atributes1.setPrice_level(atributes.getPrice_level());
    atributes1.setUsersAggragateRating(atributes.getUsersAggragateRating());
    atributes1.setAlwaysOpen(atributes.isAlwaysOpen());
    try {
      //   Map<String, Object> myObjectAsDict = new HashMap<>();
      Field[] allFields = Atributes.class.getDeclaredFields();
      for (Field field : allFields) {
        String fieldName = field.getName();
        if (types.contains(fieldName)) {
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

  private boolean isAlwaysOpen(ArrayList<PlaceOpeningHoursPeriod> periods) {
    int sum = 0;

    for (PlaceOpeningHoursPeriod period : periods) {

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
  }
