package com.example.labratour.domain;

public class AtributedPoi {

  private final String poiId;
  private String authorId;
  private int rate;
  private Atributes poiAtributes;

  public void setPoiAtributes(Atributes poiAtributes) {
    this.poiAtributes = poiAtributes;
  }

  public Atributes getPoiAtributes() {
    return poiAtributes;
  }

  public void setAuthorId(String author) {
        this.authorId = author;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getAuthorId() {
    return authorId;
  }

  public int getRate() {
    return rate;
  }

  public AtributedPoi(String poiId, int rate, String authorId) {
    this.authorId = authorId;
    this.poiId = poiId;
    this.rate = rate;
  }

  public String getPoiId() {
    return poiId;
  }
    }
