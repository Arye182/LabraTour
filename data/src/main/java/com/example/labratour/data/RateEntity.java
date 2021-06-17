package com.example.labratour.data;

import com.google.gson.annotations.SerializedName;

public class RateEntity {

        public String poiId;
        @SerializedName("emeil")
        public String author;
        public int rate;
    public String getAuthor() {
        return author;
    }

    public int getRate() {
        return rate;
    }



        public RateEntity(String poiId, int rate, String author) {
            this.author = author;
            this.poiId = poiId;
            this.author = author;
        }


}
