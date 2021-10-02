package com.example.labratour.domain.Entity.Entity;

import com.example.labratour.domain.Atributes;
import com.google.gson.annotations.SerializedName;

public class UserProfileEntity {
    @SerializedName("countrate")
    private int    countRate;
    public int getCountRate() {
        return countRate;
    }

    public void setCountRate(int countRate) {
        this.countRate = countRate;
    }
    public void setAtributes(Atributes atributes) {
        this.atributes = atributes;
    }
    @SerializedName("atributes")
    private Atributes atributes;

}
