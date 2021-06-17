package com.example.labratour.domain;

import com.google.gson.annotations.SerializedName;

public class Rate {

  public String poiId;

  @SerializedName("emeil")
  public String author;

  public int rate;

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getAuthor() {
    return author;
  }

  public int getRate() {
    return rate;
  }

  public Rate(String poiId, int rate, String author) {
    this.author = author;
    this.poiId = poiId;
    this.author = author;
  }

  public String getPoiId() {
    return poiId;
  }
    }
