package com.example.labratour.domain;


import com.google.gson.annotations.SerializedName;

public class UserAtributes {
    public void setRatesCounter(int ratesCounter) {
        RatesCounter = ratesCounter;
    }

    public int getRatesCounter() {
        return RatesCounter;
    }

    private int RatesCounter = 0;

    @SerializedName("pricelevel")
    private double price_level = 0 ;
    //
    // @JsonProperty("rating")
    @SerializedName("useraggragaterating")
    private double usersAggragateRating = 0;
    @SerializedName("aftermidnight")

    private double alwaysOpen = 0;
    private double amusement_park = 0;
    private double aquarium = 0;
    private double art_gallery = 0;
    private double campground = 0;
    private double night_club = 0;
    private double painter = 0;
    private double movie_theater  = 0;
    private double museum = 0;

    public void setAlwaysOpen(double alwaysOpen) {
        this.alwaysOpen = alwaysOpen;
    }

    private double casino = 0;
    private double cafe = 0;
    private double restaurant = 0;
    private double rv_park = 0;
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
        return tourist_attraction;
    }

    public double getZoo() {
        return zoo;
    }

    public double getGym() {
        return gym;
    }

    private double spa = 0;
    private double stadium = 0;
    private double tourist_attraction = 0;
    private double zoo = 0;
    private double gym = 0;

    public UserAtributes() {

    }

    public void setPrice_level(double price_level) {
        this.price_level = price_level;
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
        this.tourist_attraction = tourist_attraction;
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
        return price_level;
    }

    public double getUsersAggragateRating() {
        return usersAggragateRating;
    }



    public double getAmusement_park() {
        return amusement_park;
    }





}