package com.example.labratour.domain.Entity;

public class DomainNearbyPlaceResult {
    private String nearbyPlaceId;

    public String getNearbyPlaceId() {
        return nearbyPlaceId;
    }

    public void setNearbyPlacesIds(String nearbyPlaceId) {
        this.nearbyPlaceId = nearbyPlaceId;
    }

    public DomainNearbyPlaceResult(String nearbyPlaceId) {
        this.nearbyPlaceId = nearbyPlaceId;
    }
}
