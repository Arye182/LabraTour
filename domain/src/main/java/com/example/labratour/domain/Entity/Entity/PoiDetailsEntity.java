package com.example.labratour.domain.Entity.Entity;

import com.example.labratour.domain.Entity.OpeningHours1;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

//
public class PoiDetailsEntity  implements Serializable{




        @SerializedName("geometry")
        private Geometry geometry;

        @SerializedName("icon")
        private String icon;



        @SerializedName("name")
        private String name;

        @SerializedName("opening_hours")
        private OpeningHours1 openingHours1 = null;

        @SerializedName("photos")
        private List<Object> photos = null;

        @SerializedName("place_id")
        private String placeId;

        @SerializedName("rating")
        private double rating =0;

        @SerializedName("reference")
        private String reference;

        @SerializedName("scope")
        private String scope;

        @SerializedName("types")
        private String[] types;

        @SerializedName("vicinity")
        private String vicinity;

        @SerializedName("price_level")
        private int priceLevel= 0;

        /**
         * @return The geometry
         */
        public Geometry getGeometry() {
            return geometry;
        }
        //
//        /**
//         * @param geometry The geometry
//         */
        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        /**
         * @return The icon
         */
        public String getIcon() {
            return icon;
        }

        /**
         * @param icon The icon
         */
        public void setIcon(String icon) {
            this.icon = icon;
        }



    public PoiDetailsEntity(OpeningHours1 openingHours1, String placeId, Double rating, String[] types, Integer priceLevel) {
        this.openingHours1 = openingHours1;
        this.placeId = placeId;
        this.rating = rating;
        this.types = types;
        this.priceLevel = priceLevel;
    }

    /**
         * @return The name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return The openingHours
         */
        public OpeningHours1 getOpeningHours() {


            return openingHours1;
        }

        /**
         * @param openingHours1 The opening_hours
         */
        public void setOpeningHours(OpeningHours1 openingHours1) {
            this.openingHours1 = openingHours1;
        }


        /**
         * @return The placeId
         */
        public String getPlaceId() {
            return placeId;
        }

        /**
         * @param placeId The place_id
         */
        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        /**
         * @return The rating
         */
        public Double getRating() {
            return rating;
        }

        /**
         * @param rating The rating
         */
        public void setRating(Double rating) {
            this.rating = rating;
        }

        /**
         * @return The reference
         */
        public String getReference() {
            return reference;
        }

        /**
         * @param reference The reference
         */
        public void setReference(String reference) {
            this.reference = reference;
        }

        /**
         * @return The scope
         */
        public String getScope() {
            return scope;
        }

        /**
         * @param scope The scope
         */
        public void setScope(String scope) {
            this.scope = scope;
        }

        /**
         * @return The types
         */
        public String[] getTypes() {
            return types;
        }

        /**
         * @param types The types
         */
        public void setTypes(String[] types) {
            this.types = types;
        }

        /**
         * @return The vicinity
         */
        public String getVicinity() {
            return vicinity;
        }

        /**
         * @param vicinity The vicinity
         */
        public void setVicinity(String vicinity) {
            this.vicinity = vicinity;
        }

        /**
         * @return The priceLevel
         */
        public double getPriceLevel() {
            return priceLevel;
        }

        /**
         * @param priceLevel The price_level
         */
        public void setPriceLevel(Integer priceLevel) {
            this.priceLevel = priceLevel;
        }


    }



