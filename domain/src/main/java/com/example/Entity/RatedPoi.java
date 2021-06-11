package com.example.Entity;

import android.util.ArraySet;

public class RatedPoi {


        private final String placeId;
        private String userId;


        //todo feturs in map
        private ArraySet<String> types;
  /** 0 — Free
  // 1 — Inexpensive
  // 2 — Moderate
  // 3 — Expensive
   4 — Very Expensive**/
  private double aggregatedUsersRatings;
        private int priceLevel;
        private double userRating;

    public RatedPoi(String placeId) {
        this.placeId = placeId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getPlaceId() {
            return placeId;
        }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }
    public void setAggregatedUsersRatings(double aggregatedUsersRatings) {
        this.aggregatedUsersRatings = aggregatedUsersRatings;

    }
    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }
    public void setTypes(ArraySet<String> types) {
        this.types = types;
    }

    }


