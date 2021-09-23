package com.example.labratour.data.Entity;

import com.google.gson.annotations.SerializedName;

public class PlaceOpeningHoursPeriod {
    public PlaceOpeningHoursPeriodDetail getClose() {
        return close;
    }

    public PlaceOpeningHoursPeriodDetail getOpen() {
        return open;
    }

    @SerializedName("close")

    private PlaceOpeningHoursPeriodDetail close = null;
    @SerializedName("open")
    private PlaceOpeningHoursPeriodDetail open;

}
