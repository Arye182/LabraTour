//package com.example.labratour.data.net;
//
//import android.content.Context;
//
//import com.example.labratour.data.Entity.mapper.RatedPoiEntityJsonMapper;
//
//public class GooglePlacesRestApi implements RestApi{
//    String MY_API_KEY = "AIzaSyBXOteaabBVfmHXUYYyOgRv-DcE05g6-1E";
//    String API_BASE_URL =
//            "https://maps.googleapis.com/maps/api/place/";
//
//    //String API_URL_GET_POI_FETURES = API_BASE_URL+"details/json?place_id=" + PLACE_ID + "&fields=rating,price_level,types&key="+MY_API_KEY;
//    String API_URL_GET_POI = API_BASE_URL+"details/json?place_id=";
//    String _FETURES = "&fields=rating,price_level,types&key=";
//    private final RatedPoiEntityJsonMapper ratedPoiEntityJsonMapper;
//    private final Context context;
//
//    public GooglePlacesRestApi(RatedPoiEntityJsonMapper ratedPoiEntityJsonMapper, Context context) {
//        this.ratedPoiEntityJsonMapper = ratedPoiEntityJsonMapper;
//        this.context = context;
//    }
//}
