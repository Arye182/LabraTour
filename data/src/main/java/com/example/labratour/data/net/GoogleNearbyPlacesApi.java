package com.example.labratour.data.net;

public class GoogleNearbyPlacesApi {
    private static final String KEY ="&key=AIzaSyBXOteaabBVfmHXUYYyOgRv-DcE05g6-1E" ;
        private static final String LATITUDE = "";
    private static final String LONGITUDE = "";
    private static final String RADIUS = "";
    private static final String PARAMETES = KEY + LATITUDE + LONGITUDE + RADIUS;
    private static final String NEARBY_PLACES_BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";

    public static IGooglePlacesApi getGooglePlacesAPIService()
    {
        return RetrofitClient.getRetrofitClient(NEARBY_PLACES_BASE_URL).create(IGooglePlacesApi.class);
    }

}
