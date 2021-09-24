package com.example.labratour.domain;


import com.google.gson.annotations.SerializedName;

public class Atributes {
    @SerializedName("pricelevel")

    private int price_level = -5 ;
    //
   // @JsonProperty("rating")
    @SerializedName("useraggragaterating")
    private double usersAggragateRating = 0;
    @SerializedName("aftermidnight")

    private boolean alwaysOpen = false;
    private boolean amusement_park = false;
    private boolean aquarium = false;
    private boolean art_gallery = false;
    private boolean bar = false;
    private boolean campground = false;
    private boolean night_club = false;
    private boolean painter = false;
    private boolean movie_theater  = false;
    private boolean museum = false;

    public void setAlwaysOpen(boolean alwaysOpen) {
        this.alwaysOpen = alwaysOpen;
    }

    private boolean casino = false;
    private boolean cafe = false;
    private boolean restaurant = false;
    private boolean rv_park = false;
    private boolean shopping_mall;
    private boolean spa = false;
    private boolean stadium = false;
    private boolean tourist_attraction = false;
    private boolean zoo = false;
    private boolean gym = false;

    public Atributes() {

    }

    public void setPrice_level(int price_level) {
        this.price_level = price_level;
    }

    public void setUsersAggragateRating(float usersAggragateRating) {
        this.usersAggragateRating = usersAggragateRating;
    }


    public void setAmusement_park(boolean amusement_park) {
        this.amusement_park = amusement_park;
    }

    public void setAquarium(boolean aquarium) {
        this.aquarium = aquarium;
    }

    public void setArt_gallery(boolean art_gallery) {
        this.art_gallery = art_gallery;
    }

    public void setBar(boolean bar) {
        this.bar = bar;
    }

    public void setCampground(boolean campground) {
        this.campground = campground;
    }

    public void setNight_club(boolean night_club) {
        this.night_club = night_club;
    }

    public void setPainter(boolean painter) {
        this.painter = painter;
    }

    public void setMovie_theater(boolean movie_theater) {
        this.movie_theater = movie_theater;
    }

    public void setMuseum(boolean museum) {
        this.museum = museum;
    }

    public void setCasino(boolean casino) {
        this.casino = casino;
    }

    public void setCafe(boolean cafe) {
        this.cafe = cafe;
    }

    public void setRestaurant(boolean restaurant) {
        this.restaurant = restaurant;
    }

    public void setRv_park(boolean rv_park) {
        this.rv_park = rv_park;
    }

    public void setShopping_mall(boolean shopping_mall) {
        this.shopping_mall = shopping_mall;
    }

    public void setSpa(boolean spa) {
        this.spa = spa;
    }

    public void setStadium(boolean stadium) {
        this.stadium = stadium;
    }

    public void setTourist_attraction(boolean tourist_attraction) {
        this.tourist_attraction = tourist_attraction;
    }

    public void setZoo(boolean zoo) {
        this.zoo = zoo;
    }

    public void setGym(boolean gym) {
        this.gym = gym;
    }

    public int getPrice_level() {
        return price_level;
    }

    public double getUsersAggragateRating() {
        return usersAggragateRating;
    }

    public boolean isAlwaysOpen() {
        return alwaysOpen;
    }

    public boolean isAmusement_park() {
        return amusement_park;
    }

    public boolean isAquarium() {
        return aquarium;
    }

    public boolean isArt_gallery() {
        return art_gallery;
    }

    public boolean isBar() {
        return bar;
    }

    public boolean isCampground() {
        return campground;
    }

    public boolean isNight_club() {
        return night_club;
    }

    public boolean isPainter() {
        return painter;
    }

    public boolean isMovie_theater() {
        return movie_theater;
    }

    public boolean isMuseum() {
        return museum;
    }

    public boolean isCasino() {
        return casino;
    }

    public boolean isCafe() {
        return cafe;
    }

    public boolean isRestaurant() {
        return restaurant;
    }

    public boolean isRv_park() {
        return rv_park;
    }

    public boolean isShopping_mall() {
        return shopping_mall;
    }

    public boolean isSpa() {
        return spa;
    }

    public boolean isStadium() {
        return stadium;
    }

    public boolean isTourist_attraction() {
        return tourist_attraction;
    }

    public boolean isZoo() {
        return zoo;
    }

    public boolean isGym() {
        return gym;
    }
}

