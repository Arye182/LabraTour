package com.data.net;

public interface RestApi {
    String GOOGLE_API_BASE = "https://maps.googleapis.com/maps/api/place/";
    String GET_NEARBY_PLACES_LIST_BASE = GOOGLE_API_BASE + "nearbysearch/" + "json";
    String KEY = "AIzaSyBXOteaabBVfmHXUYYyOgRv-DcE05g6-1E";
    String BASE_GET_PLACE_DETAILES= "https://maps.googleapis.com/maps/api/place/details/json?" +KEY;



}