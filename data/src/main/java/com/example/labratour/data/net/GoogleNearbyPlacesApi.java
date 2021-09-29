package com.example.labratour.data.net;
//
//import com.example.labratour.data.Entity.MyNearbyPlaces;
//
//import retrofit2.Call;
//
//public class GoogleNearbyPlacesApi implements IGooglePlacesApi{
//    private static final String KEY ="&key=AIzaSyBXOteaabBVfmHXUYYyOgRv-DcE05g6-1E" ;
//        private static final String LATITUDE = "";
//    private static final String LONGITUDE = "";
//    private static final String RADIUS = "";
//    private static final String PARAMETES = KEY + LATITUDE + LONGITUDE + RADIUS;
//    private static final String NEARBY_PLACES_BASE_URL = "https://maps.googleapis.com/maps/api/place";
//
//    public static IGooglePlacesApi getGooglePlacesAPIService()
//    {
//        return RetrofitClient.getRetrofitClient(NEARBY_PLACES_BASE_URL).create(IGooglePlacesApi.class);
//    }
//
//    @Override
//    public Call<MyNearbyPlaces> getNearByPlaces(String location, int radius, String key) {
//        return null;
//    }
//}
