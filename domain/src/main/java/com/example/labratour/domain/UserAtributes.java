package com.example.labratour.domain;


import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class UserAtributes {
    public UserAtributes( ) {

    }

    public void setRatesCounter(int ratesCounter) {
        RatesCounter = ratesCounter;
    }

    public int getRatesCounter() {
        return RatesCounter;
    }
    private HashMap<String, Object> initAtributesMap() {
        HashMap<String, Object> atributesDict = new HashMap<>();
        atributesDict.put("ratesCounter",0);
        atributesDict.put("price_level", 0);
        atributesDict.put("useraggragaterating", 0);
        atributesDict.put("always_open", 0);
        atributesDict.put("casino", 0);
        atributesDict.put("cafe", 0);
        atributesDict.put("restaurant", 0);
        atributesDict.put("rv_park", 0);
        atributesDict.put("shopping_mall", 0);
        atributesDict.put("amusement_park", 0);
        atributesDict.put("aquarium",0);
        atributesDict.put("art_gallery",0);
        atributesDict.put("campground",0 );
        atributesDict.put("night_club",0 );
        atributesDict.put("painter", 0);
        atributesDict.put("movie_theater", 0);
        atributesDict.put("museum",0 );
        atributesDict.put("spa", 0);
        atributesDict.put("stadium",0 );
        atributesDict.put("touristAttraction", 0);
        atributesDict.put("zoo",0 );
        atributesDict.put("gym",0 );
        return atributesDict;
    }
    @SerializedName("RatesCounter")
    private int RatesCounter = 0;

    @SerializedName("price_level")
    private double pricelevel = 0 ;
    //
    // @JsonProperty("rating")
    @SerializedName("useraggragaterating")
    private double usersAggragateRating = 0;
    @SerializedName("always_open")

    private double alwaysOpen = 0;
    @SerializedName("amusement_park")
    private double amusement_park = 0;
    @SerializedName("aquarium")
    private double aquarium = 0;
    @SerializedName("art_gallery")
    private double art_gallery = 0;
    @SerializedName("campground")
    private double campground = 0;
    @SerializedName("night_club")
    private double night_club = 0;
    @SerializedName("painter")
    private double painter = 0;
    @SerializedName("movie_theater")
    private double movie_theater  = 0;
    @SerializedName("museum")
    private double museum = 0;

    public void setAlwaysOpen(double alwaysOpen) {
        this.alwaysOpen = alwaysOpen;
    }

    @SerializedName("casino")
    private double casino = 0;
    @SerializedName("cafe")
    private double cafe = 0;
    @SerializedName("restaurant")
    private double restaurant = 0;
    @SerializedName("rv_park")
    private double rv_park = 0;
    @SerializedName("shopping_mall")
    private double shopping_mall = 0;

    public double getAlwaysOpen() {
        return alwaysOpen;
    }

    public double getAquarium() {
        return aquarium;
    }

    public double getArt_gallery() {
        return art_gallery;
    }

    public double getCampground() {
        return campground;
    }

    public double getNight_club() {
        return night_club;
    }

    public double getPainter() {
        return painter;
    }

    public double getMovie_theater() {
        return movie_theater;
    }

    public double getMuseum() {
        return museum;
    }

    public double getCasino() {
        return casino;
    }

    public double getCafe() {
        return cafe;
    }

    public double getRestaurant() {
        return restaurant;
    }

    public double getRv_park() {
        return rv_park;
    }

    public double getShopping_mall() {
        return shopping_mall;
    }

    public double getSpa() {
        return spa;
    }

    public double getStadium() {
        return stadium;
    }

    public double getTourist_attraction() {
        return touristAttraction;
    }

    public double getZoo() {
        return zoo;
    }

    public double getGym() {
        return gym;
    }

    @SerializedName("spa")
    private double spa = 0;
    @SerializedName("stadium")
    private double stadium = 0;
    @SerializedName("tourist_attraction")
    private double touristAttraction = 0;
    @SerializedName("zoo")
    private double zoo = 0;
    @SerializedName("gym")
    private double gym = 0;
HashMap<String, Double> atributesDict = null;
    public HashMap<String, Double> getUserAtributes() {
if(atributesDict==null){
    initAtributesMap();
}
return atributesDict;
    }

    public void setPrice_level(double price_level) {
        this.pricelevel = price_level;
    }

    public void setUsersAggragateRating(double usersAggragateRating) {
        this.usersAggragateRating = usersAggragateRating;
    }


    public void setAmusement_park(double amusement_park) {
        this.amusement_park = amusement_park;
    }






    public void setArt_gallery(double art_gallery) {
        this.art_gallery = art_gallery;
    }

    public void setCampground(double campground) {
        this.campground = campground;
    }

    public void setNight_club(double night_club) {
        this.night_club = night_club;
    }

    public void setPainter(double painter) {
        this.painter = painter;
    }

    public void setMovie_theater(double movie_theater) {
        this.movie_theater = movie_theater;
    }

    public void setMuseum(double museum) {
        this.museum = museum;
    }

    public void setCasino(double casino) {
        this.casino = casino;
    }

    public void setCafe(double cafe) {
        this.cafe = cafe;
    }

    public void setRestaurant(double restaurant) {
        this.restaurant = restaurant;
    }

    public void setRv_park(double rv_park) {
        this.rv_park = rv_park;
    }

    public void setShopping_mall(double shopping_mall) {
        this.shopping_mall = shopping_mall;
    }

    public void setSpa(double spa) {
        this.spa = spa;
    }

    public void setStadium(double stadium) {
        this.stadium = stadium;
    }

    public void setTourist_attraction(double tourist_attraction) {
        this.touristAttraction = tourist_attraction;
    }

    public void setZoo(double zoo) {
        this.zoo = zoo;
    }

    public void setGym(double gym) {
        this.gym = gym;
    }

    public void setAquarium(double aquarium) {
        this.aquarium = aquarium;
    }


    public double getPrice_level() {
        return pricelevel;
    }

    public double getUsersAggragateRating() {
        return usersAggragateRating;
    }



    public double getAmusement_park() {
        return amusement_park;
    }





}