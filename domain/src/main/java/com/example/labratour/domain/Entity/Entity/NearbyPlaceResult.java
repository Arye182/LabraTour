package com.example.labratour.domain.Entity.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NearbyPlaceResult implements Serializable {
        @SerializedName("place_id")
        public String placeId;
        public NearbyPlaceResult(String placeId) {
                this.placeId = placeId;
        }

        public NearbyPlaceResult() {
        }
//        @SerializedName("geometry")
//        private Geometry geometry;
//
//        @SerializedName("icon")
//        private String icon;

//        @SerializedName("place_id")
//        private String id;
//
//        @SerializedName("name")
//        private String name;
//
//        @SerializedName("opening_hours")
//        private OpeningHours openingHours;
//
//        @SerializedName("photos")
//        private List<Object> photos = null;


//        @SerializedName("rating")
//        private Double rating;
//
//        @SerializedName("reference")
//        private String reference;
//
//        @SerializedName("scope")
//        private String scope;
//
//        @SerializedName("types")
//        private List<String> types = new ArrayList<String>();
//
//        @SerializedName("vicinity")
//        private String vicinity;
//
//        @SerializedName("price_level")
//        private Integer priceLevel;

        /**
         * @return The geometry
//         */
//        public Geometry getGeometry() {
//            return geometry;
//        }
////
////        /**
////         * @param geometry The geometry
////         */
//        public void setGeometry(Geometry geometry) {
//            this.geometry = geometry;
//        }
//
//        /**
//         * @return The icon
//         */
//        public String getIcon() {
//            return icon;
//        }
//
//        /**
//         * @param icon The icon
//         */
//        public void setIcon(String icon) {
//            this.icon = icon;
//        }
//
//        /**
//         * @return The id
//         */
//        public String getId() {
//            return id;
//        }
//
//        /**
//         * @param id The id
//         */
//        public void setId(String id) {
//            this.id = id;
//        }


        public String getPlaceId() {
                return placeId;
        }

        public void setPlaceId(String placeId) {
                this.placeId = placeId;
        }




}
