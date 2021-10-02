package com.example.labratour.domain.Entity.Entity;

import com.google.gson.annotations.SerializedName;
//  public Results[] getResults() {
//      return re


public class Geometry {
  private Location location;
   private Viewport viewport;

   @SerializedName("location")
   public Location getLocation() {
     return location;
   }

   @SerializedName("location")
   public void setLocation(Location location) {
     this.location = location;
   }

   @SerializedName("viewport")
   public Viewport getViewport() {
     return viewport;
   }

   @SerializedName("viewport")
   public void setViewport(Viewport viewport) {
     this.viewport = viewport;
   }
 }


