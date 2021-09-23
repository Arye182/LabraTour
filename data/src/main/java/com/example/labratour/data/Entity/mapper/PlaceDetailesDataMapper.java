package com.example.labratour.data.Entity.mapper;

import com.example.labratour.data.Entity.PlaceOpeningHoursPeriod;
import com.example.labratour.data.Entity.PoiDetailsEntity;
import com.example.labratour.domain.Atributes;

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
      atributes.setPrice_level(poiDetailsEntity.getPriceLevel() / 5);
      atributes.setUsersAggragateRating((float) (poiDetailsEntity.getRating() / 5));
      atributes.setAlwaysOpen(isAlwaysOpen(poiDetailsEntity.getOpeningHours().getPeriods()));
      updateAtributesFields(poiDetailsEntity.getTypes(), atributes);
    }
    return atributes;
  }

  private void updateAtributesFields(ArrayList<String> types, Atributes atributes) {
    for (String type : types) {
      if (atributes.getClass().getFields().toString().contains(type)) {
        try {
          atributes.getClass().getDeclaredField(type).setBoolean(this, true);

        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (NoSuchFieldException e) {
          e.printStackTrace();
        }
      }
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
