package com.example.labratour.data.Entity;

import com.google.gson.annotations.SerializedName;

public class Viewport {
  private Location northeast;
  private Location southwest;

  @SerializedName("northeast")
  public Location getNortheast() {
    return northeast;
  }

  @SerializedName("northeast")
  public void setNortheast(Location northeast) {
    this.northeast = northeast;
  }

  @SerializedName("southwest")
  public Location getSouthwest() {
    return southwest;
  }

  @SerializedName("southwest")
  public void setSouthwest(Location southwest) {
    this.southwest = southwest;
  }


}
