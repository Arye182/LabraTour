package com.example.labratour.domain.Entity.Entity;

import com.google.gson.annotations.SerializedName;

public class Location {
  private double lat;
  private double lng;

  @SerializedName("lat")
  public double getLat() {
    return lat;
  }

  @SerializedName("lat")
  public void setLat(double lat) {
    this.lat = lat;
  }

  @SerializedName("lng")
  public double getLng() {
    return lng;
  }

  @SerializedName("lng")
  public void setLng(double lng) {
    this.lng = lng;
  }
}
