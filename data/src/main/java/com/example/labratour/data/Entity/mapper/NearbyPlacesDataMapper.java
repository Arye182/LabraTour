package com.example.labratour.data.Entity.mapper;

import com.example.labratour.data.Entity.NearbyPlaceResult;

import java.util.List;

public class NearbyPlacesDataMapper {
    public List<String> transform(List<NearbyPlaceResult> result) {
        List<String> placesIds = null;
        for (NearbyPlaceResult n :result) {
            placesIds.add(new String(n.getId()));
        }
        return placesIds;
    }
}
