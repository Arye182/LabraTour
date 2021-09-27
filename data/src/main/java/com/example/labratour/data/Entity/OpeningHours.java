package com.example.labratour.data.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OpeningHours {
    public void setPeriods(ArrayList<PlaceOpeningHoursPeriod> periods) {
        this.periods = periods;
    }

    public ArrayList<PlaceOpeningHoursPeriod> getPeriods() {
        return periods;
    }

    public OpeningHours(ArrayList<PlaceOpeningHoursPeriod> periods) {
        this.periods = periods;
    }

    @SerializedName("periods")

    private ArrayList<PlaceOpeningHoursPeriod> periods;

}

