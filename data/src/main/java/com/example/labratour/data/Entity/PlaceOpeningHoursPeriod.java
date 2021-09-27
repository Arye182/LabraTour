package com.example.labratour.data.Entity;

import com.google.gson.annotations.SerializedName;

public class PlaceOpeningHoursPeriod {
    public PlaceOpeningHoursPeriod(PlaceOpeningHoursPeriodDetail open) {
        this.open = open;
    }

    public PlaceOpeningHoursPeriod(PlaceOpeningHoursPeriodDetail close, PlaceOpeningHoursPeriodDetail open) {
        this.close = close;
        this.open = open;
    }

    public PlaceOpeningHoursPeriodDetail getClose() {
        return close;
    }

    public PlaceOpeningHoursPeriodDetail getOpen() {
        return open;
    }

    @SerializedName("close")

    private PlaceOpeningHoursPeriodDetail close = null;
    @SerializedName("open")
    private PlaceOpeningHoursPeriodDetail open = null;

}
