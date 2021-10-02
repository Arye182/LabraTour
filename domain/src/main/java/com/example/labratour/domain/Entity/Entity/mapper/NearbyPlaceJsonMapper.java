package com.example.labratour.domain.Entity.Entity.mapper;

import android.os.Looper;
import android.util.Log;

import com.example.labratour.domain.Entity.NearbyPlaceEntity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NearbyPlaceJsonMapper {
    private final Gson gson;


    public NearbyPlaceJsonMapper() {
        this.gson = new Gson();
    }
    public NearbyPlaceEntity transformNearbyPlaceResult(String singleNearbyPlaceResponse) throws JsonSyntaxException {
        final Type nearbyPlaceType = new TypeToken<NearbyPlaceEntity>() {}.getType();
        return this.gson.fromJson(singleNearbyPlaceResponse, nearbyPlaceType);
    }
    public  ArrayList<String> transformCollectionToIds(String response)
          {
              Log.e("testNearbyUseCase","inside JsonMapper.transformCollectionToIds called from apply in placesRepository.getPoiById  run on:" + Looper.myLooper().toString(), new Throwable("couldnt print my looper"));

              JsonArray arr = JsonParser.parseString(response).getAsJsonObject().getAsJsonArray("results");
//todo ArrayList<NearbyPlaceResult> transformCollection(JsonArray nearbyPlaces)
        ArrayList<String> Ids = new ArrayList<String>(20);
        for (int i = 0; i< arr.size(); i++) {
                if (!(arr.get(i)).isJsonNull()){
                    Ids.add(arr.get(i).getAsJsonObject().get("place_id").getAsString());
                }
        }
        return Ids;
        }
         //   final Type nearbyPlaceType = new TypeToken<MyNearbyPlaces>() {}.getType();


           // return new ArrayList<NearbyPlaceResult>(Arrays.asList(nearbyPlaces.getResults()));}
//        final ArrayList<NearbyPlaceResult> places = new ArrayList<>(20);
//        for (String n :arr) {
//            final NearbyPlaceResult place = transformNearbyPlaceResult(n);
//            if (place != null){
//                places.add(place);}
//        }
//        return places;}

    public  NearbyPlaceEntity transformCollection(String response){
        return null;
    }}
