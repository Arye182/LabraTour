package com.data.net;

import io.reactivex.Observable;
import Entity.RatedPoiEntity;

public interface RestApi {
String My_API_KEY = "AIzaSyBXOteaabBVfmHXUYYyOgRv-DcE05g6-1E"
 String API_BASE_URL =
          "https://maps.googleapis.com/maps/api/place/";

  String API_URL_GET_POI_FETURES = API_BASE_URL+"details/json?place_id=" + PLACE_ID+ "&fields=rating,price_level,types&key="+MY_API_KEY;




  /**
   * Retrieves an {@link Observable} which will emit a {@link RatedPoiEntity}.
  **/
  Observable<RatedPoiEntity> ratedPoiEntityById(final int userId);
 }