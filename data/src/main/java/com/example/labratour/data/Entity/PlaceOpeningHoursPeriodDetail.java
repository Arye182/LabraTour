package com.example.labratour.data.Entity;

import com.google.gson.annotations.SerializedName;

public class PlaceOpeningHoursPeriodDetail {
    @SerializedName("day")
    private int day;

    public void setDay(int day) {
        this.day = day;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDay() {
        return day;
    }

    @SerializedName("time")
    private String time;

    public String getTime() {
        return time;
    }
}
