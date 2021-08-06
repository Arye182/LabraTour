package com.example.labratour.data.Entity.mapper;

import com.example.labratour.data.Entity.NearbyPlaceResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NearbyPlacesDataMapper {


    public List<String> transform(Collection<NearbyPlaceResult> result) {
        final List<String> placesIds = new ArrayList<>(20);
        for (NearbyPlaceResult n :result) {
            final String placesId = transform(n);
            if (placesId != null){
                placesIds.add(new String(n.getId()));}
        }
        return placesIds;}



    public String transform(NearbyPlaceResult result) {
        String id = null;
        if (result != null) {
            id  = result.getId();
        }
        return id;
    }}

