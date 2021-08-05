package com.example.labratour.data.Entity.mapper;

import com.example.labratour.data.Entity.NearbyPlaceResult;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class NearbyPlaceJsonMapper {
    private final Gson gson;


    public NearbyPlaceJsonMapper() {
        this.gson = new Gson();
    }
    public NearbyPlaceResult transformUserEntity(String singleNearbyPlaceResponse) throws JsonSyntaxException {
        final Type nearbyPlaceType = new TypeToken<NearbyPlaceResult>() {}.getType();
        return this.gson.fromJson(singleNearbyPlaceResponse, nearbyPlaceType);
    }
    public  List<NearbyPlaceResult> transformCollection(String response)
         throws JsonSyntaxException {
            final Type nearbyPlaceType = new TypeToken<List<NearbyPlaceJsonMapper>>() {}.getType();
            return this.gson.fromJson(response, nearbyPlaceType);
    }
}
