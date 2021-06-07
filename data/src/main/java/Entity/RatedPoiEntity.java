package Entity;
import android.util.ArraySet;

import com.google.gson.annotations.SerializedName;

public class RatedPoiEntity {
//    @SerializedName("place_id")
//    private String placeId;

    @SerializedName("rating")
    private double aggregatedUsersRatings;

    @SerializedName("price_level")
    private int priceLevel;

//    @SerializedName("opening_hours")
//    private String description;

    @SerializedName("types")
    private ArraySet<String> types;



    public RatedPoiEntity() {
        //empty
    }

    public double getAggregatedUsersRatings() {
        return aggregatedUsersRatings;
    }



    public int getPriceLevel() {
        return priceLevel;
    }

    public ArraySet<String> getTypes() {
        return types;
    }


}
