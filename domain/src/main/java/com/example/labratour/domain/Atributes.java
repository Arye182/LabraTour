package com.example.labratour.domain;


import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Atributes {
    @SerializedName("pricelevel")

    public double price_level = 0;
    //
   // @JsonProperty("rating")
    @SerializedName("useraggragaterating")
    public double usersAggragateRating = 0;
    @SerializedName("alwaysOpen")

    public boolean alwaysOpen = false;
 public boolean amusement_park = false;
 public boolean aquarium = false;
 public boolean art_gallery = false;
 public boolean bar = false;
 public boolean campground = false;
 public boolean night_club = false;
 public boolean painter = false;
 public boolean movie_theater  = false;
 public boolean museum = false;

    public void setAlwaysOpen(boolean alwaysOpen) {
        this.alwaysOpen = alwaysOpen;
    }

   public boolean casino = false;
   public boolean cafe = false;
   public boolean restaurant = false;
   public boolean rv_park = false;
   public boolean shopping_mall;
   public boolean spa = false;
   public boolean stadium = false;
   public boolean tourist_attraction = false;
   public boolean zoo = false;
   public boolean gym = false;
public HashMap<String, Object> atributesMap;
    public Atributes() {
atributesMap = initAtributesMap();
    }

    public void setPrice_level(double price_level) {
        this.price_level = price_level;
    }

    public void setUsersAggragateRating(double uersAggragateRating) {
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

    public double getPrice_level() {
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

  public HashMap<String, Object> getAtributesMap() {
    return atributesMap;
  }

  public HashMap<String, Object> initAtributesMap() {
    HashMap<String, Object> atributesDict = new HashMap<>();
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
}

