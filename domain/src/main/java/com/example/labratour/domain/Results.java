package com.example.labratour.domain;

import com.example.labratour.domain.Entity.Entity.Geometry;
import com.example.labratour.domain.Entity.OpeningHours1;
import com.google.gson.annotations.SerializedName;

public class
Results{
    private Geometry geometry;
    private String icon;
    private String icon_background_color;
    private String icon_mask_base_uri;
    private String name;
    private Photos[] photos;
    private String place_id;
    private String reference;
    private String scope;
    private String[] types;
    private String vicinity;

    @SerializedName("geometry")
    public Geometry getGeometry() {
      return geometry;
    }

    @SerializedName("geometry")
    public void setGeometry(Geometry geometry) {
      this.geometry = geometry;
    }

    @SerializedName("icon")
    public String getIcon() {
      return icon;
    }

    @SerializedName("icon")
    public void setIcon(String icon) {
      this.icon = icon;
    }

    @SerializedName("icon_background_color")
    public String getIconBackgroundColor() {
      return icon_background_color;
    }

    @SerializedName("icon_background_color")
    public void setIconBackgroundColor(String icon_background_color) {
      this.icon_background_color = icon_background_color;
    }

    @SerializedName("icon_mask_base_uri")
    public String getIconMaskBaseUri() {
      return icon_mask_base_uri;
    }

    @SerializedName("icon_mask_base_uri")
    public void setIconMaskBaseUri(String icon_mask_base_uri) {
      this.icon_mask_base_uri = icon_mask_base_uri;
    }

    @SerializedName("name")
    public String getName() {
      return name;
    }

    @SerializedName("name")
    public void setName(String name) {
      this.name = name;
    }

    @SerializedName("photos")
    public Photos[] getPhotos() {
      return photos;
    }

    @SerializedName("photos")
    public void setPhotos(Photos[] photos) {
      this.photos = photos;
    }

    @SerializedName("place_id")
    public String getPlaceId() {
      return place_id;
    }

    @SerializedName("place_id")
    public void setPlaceId(String place_id) {
      this.place_id = place_id;
    }

    @SerializedName("reference")
    public String getReference() {
      return reference;
    }

    @SerializedName("reference")
    public void setReference(String reference) {
      this.reference = reference;
    }

    @SerializedName("scope")
    public String getScope() {
      return scope;
    }

    @SerializedName("scope")
    public void setScope(String scope) {
      this.scope = scope;
    }

    @SerializedName("types")
    public String[] getTypes() {
      return types;
    }

    @SerializedName("types")
    public void setTypes(String[] types) {
      this.types = types;
    }

    @SerializedName("vicinity")
    public String getVicinity() {
      return vicinity;
    }

    @SerializedName("vicinity")
    public void setVicinity(String vicinity) {
      this.vicinity = vicinity;
    }}

//  public Results[] getResults() {
//      return re

   class OpeningHours {
      private boolean open_now;

      @SerializedName("open_now")
      public boolean getOpenNow() {
        return open_now;
      }

      @SerializedName("open_now")
      public void setOpenNow(boolean open_now) {
        this.open_now = open_now;
      }
    }

class Type {
    private String business_status;
    private Geometry geometry;
    private String icon;
    private String icon_background_color;
    private String icon_mask_base_uri;
    private String name;
    private Photos photos;
    private String place_id;
    private PlusCode plus_code;
    private double rating;
    private String reference;
    private String scope;
    private Type[] types;
    private int user_ratings_total;
    private String vicinity;

    @SerializedName("business_status")
    public String getBusinessStatus() {
      return business_status;
    }

    @SerializedName("business_status")
    public void setBusinessStatus(String business_status) {
      this.business_status = business_status;
    }

    @SerializedName("geometry")
    public Geometry getGeometry() {
      return geometry;
    }

    @SerializedName("geometry")
    public void setGeometry(Geometry geometry) {
      this.geometry = geometry;
    }

    @SerializedName("icon")
    public String getIcon() {
      return icon;
    }

    @SerializedName("icon")
    public void setIcon(String icon) {
      this.icon = icon;
    }

    @SerializedName("icon_background_color")
    public String getIconBackgroundColor() {
      return icon_background_color;
    }

    @SerializedName("icon_background_color")
    public void setIconBackgroundColor(String icon_background_color) {
      this.icon_background_color = icon_background_color;
    }

    @SerializedName("icon_mask_base_uri")
    public String getIconMaskBaseUri() {
      return icon_mask_base_uri;
    }

    @SerializedName("icon_mask_base_uri")
    public void setIconMaskBaseUri(String icon_mask_base_uri) {
      this.icon_mask_base_uri = icon_mask_base_uri;
    }

    @SerializedName("name")
    public String getName() {
      return name;
    }

    @SerializedName("name")
    public void setName(String name) {
      this.name = name;
    }

    @SerializedName("photos")
    public Photos getPhotos() {
      return photos;
    }

    @SerializedName("photos")
    public void setPhotos(Photos photos) {
      this.photos = photos;
    }

    @SerializedName("place_id")
    public String getPlaceId() {
      return place_id;
    }

    @SerializedName("place_id")
    public void setPlaceId(String place_id) {
      this.place_id = place_id;
    }

    @SerializedName("plus_code")
    public PlusCode getPlusCode() {
      return plus_code;
    }

    @SerializedName("plus_code")
    public void setPlusCode(PlusCode plus_code) {
      this.plus_code = plus_code;
    }

    @SerializedName("rating")
    public double getRating() {
      return rating;
    }

    @SerializedName("rating")
    public void setRating(double rating) {
      this.rating = rating;
    }

    @SerializedName("reference")
    public String getReference() {
      return reference;
    }

    @SerializedName("reference")
    public void setReference(String reference) {
      this.reference = reference;
    }

    @SerializedName("scope")
    public String getScope() {
      return scope;
    }

    @SerializedName("scope")
    public void setScope(String scope) {
      this.scope = scope;
    }

    @SerializedName("types")
    public Type[] getTypes() {
      return types;
    }

    @SerializedName("types")
    public void setTypes(Type[] types) {
      this.types = types;
    }

    @SerializedName("user_ratings_total")
    public int getUserRatingsTotal() {
      return user_ratings_total;
    }

    @SerializedName("user_ratings_total")
    public void setUserRatingsTotal(int user_ratings_total) {
      this.user_ratings_total = user_ratings_total;
    }

    @SerializedName("vicinity")
    public String getVicinity() {
      return vicinity;
    }

    @SerializedName("vicinity")
    public void setVicinity(String vicinity) {
      this.vicinity = vicinity;
    }
  }

  class Types2 {
    private String business_status;
    private Geometry geometry;
    private String icon;
    private String icon_background_color;
    private String icon_mask_base_uri;
    private String name;
    private OpeningHours1 opening_hours;
    private Photos photos;
    private String place_id;
    private PlusCode plus_code;
    private int price_level;
    private double rating;
    private String reference;
    private String scope;
    private Types2[] types;
    private int user_ratings_total;
    private String vicinity;

    @SerializedName("business_status")
    public String getBusinessStatus() {
      return business_status;
    }

    @SerializedName("business_status")
    public void setBusinessStatus(String business_status) {
      this.business_status = business_status;
    }

    @SerializedName("geometry")
    public Geometry getGeometry() {
      return geometry;
    }

    @SerializedName("geometry")
    public void setGeometry(Geometry geometry) {
      this.geometry = geometry;
    }

    @SerializedName("icon")
    public String getIcon() {
      return icon;
    }

    @SerializedName("icon")
    public void setIcon(String icon) {
      this.icon = icon;
    }

    @SerializedName("icon_background_color")
    public String getIconBackgroundColor() {
      return icon_background_color;
    }

    @SerializedName("icon_background_color")
    public void setIconBackgroundColor(String icon_background_color) {
      this.icon_background_color = icon_background_color;
    }

    @SerializedName("icon_mask_base_uri")
    public String getIconMaskBaseUri() {
      return icon_mask_base_uri;
    }

    @SerializedName("icon_mask_base_uri")
    public void setIconMaskBaseUri(String icon_mask_base_uri) {
      this.icon_mask_base_uri = icon_mask_base_uri;
    }

    @SerializedName("name")
    public String getName() {
      return name;
    }

    @SerializedName("name")
    public void setName(String name) {
      this.name = name;
    }

    @SerializedName("opening_hours")
    public OpeningHours1 getOpeningHours() {
      return opening_hours;
    }

    @SerializedName("opening_hours")
    public void setOpeningHours(OpeningHours1 opening_hours) {
      this.opening_hours = opening_hours;
    }

    @SerializedName("photos")
    public Photos getPhotos() {
      return photos;
    }

    @SerializedName("photos")
    public void setPhotos(Photos photos) {
      this.photos = photos;
    }

    @SerializedName("place_id")
    public String getPlaceId() {
      return place_id;
    }

    @SerializedName("place_id")
    public void setPlaceId(String place_id) {
      this.place_id = place_id;
    }

    @SerializedName("plus_code")
    public PlusCode getPlusCode() {
      return plus_code;
    }

    @SerializedName("plus_code")
    public void setPlusCode(PlusCode plus_code) {
      this.plus_code = plus_code;
    }

    @SerializedName("price_level")
    public int getPriceLevel() {
      return price_level;
    }

    @SerializedName("price_level")
    public void setPriceLevel(int price_level) {
      this.price_level = price_level;
    }

    @SerializedName("rating")
    public double getRating() {
      return rating;
    }

    @SerializedName("rating")
    public void setRating(double rating) {
      this.rating = rating;
    }

    @SerializedName("reference")
    public String getReference() {
      return reference;
    }

    @SerializedName("reference")
    public void setReference(String reference) {
      this.reference = reference;
    }

    @SerializedName("scope")
    public String getScope() {
      return scope;
    }

    @SerializedName("scope")
    public void setScope(String scope) {
      this.scope = scope;
    }

    @SerializedName("types")
    public Types2[] getTypes() {
      return types;
    }

    @SerializedName("types")
    public void setTypes(Types2[] types) {
      this.types = types;
    }

    @SerializedName("user_ratings_total")
    public int getUserRatingsTotal() {
      return user_ratings_total;
    }

    @SerializedName("user_ratings_total")
    public void setUserRatingsTotal(int user_ratings_total) {
      this.user_ratings_total = user_ratings_total;
    }

    @SerializedName("vicinity")
    public String getVicinity() {
      return vicinity;
    }

    @SerializedName("vicinity")
    public void setVicinity(String vicinity) {
      this.vicinity = vicinity;
    }
  }

  class Types3 {
    private String business_status;
    private Geometry geometry;
    private String icon;
    private String icon_background_color;
    private String icon_mask_base_uri;
    private String name;
    private OpeningHours1 opening_hours;
    private Photos photos;
    private String place_id;
    private PlusCode plus_code;
    private double rating;
    private String reference;
    private String scope;
    private Types3[] types;
    private int user_ratings_total;
    private String vicinity;

    @SerializedName("business_status")
    public String getBusinessStatus() {
      return business_status;
    }

    @SerializedName("business_status")
    public void setBusinessStatus(String business_status) {
      this.business_status = business_status;
    }

    @SerializedName("geometry")
    public Geometry getGeometry() {
      return geometry;
    }

    @SerializedName("geometry")
    public void setGeometry(Geometry geometry) {
      this.geometry = geometry;
    }

    @SerializedName("icon")
    public String getIcon() {
      return icon;
    }

    @SerializedName("icon")
    public void setIcon(String icon) {
      this.icon = icon;
    }

    @SerializedName("icon_background_color")
    public String getIconBackgroundColor() {
      return icon_background_color;
    }

    @SerializedName("icon_background_color")
    public void setIconBackgroundColor(String icon_background_color) {
      this.icon_background_color = icon_background_color;
    }

    @SerializedName("icon_mask_base_uri")
    public String getIconMaskBaseUri() {
      return icon_mask_base_uri;
    }

    @SerializedName("icon_mask_base_uri")
    public void setIconMaskBaseUri(String icon_mask_base_uri) {
      this.icon_mask_base_uri = icon_mask_base_uri;
    }

    @SerializedName("name")
    public String getName() {
      return name;
    }

    @SerializedName("name")
    public void setName(String name) {
      this.name = name;
    }

    @SerializedName("opening_hours")
    public OpeningHours1 getOpeningHours() {
      return opening_hours;
    }

    @SerializedName("opening_hours")
    public void setOpeningHours(OpeningHours1 opening_hours) {
      this.opening_hours = opening_hours;
    }

    @SerializedName("photos")
    public Photos getPhotos() {
      return photos;
    }

    @SerializedName("photos")
    public void setPhotos(Photos photos) {
      this.photos = photos;
    }

    @SerializedName("place_id")
    public String getPlaceId() {
      return place_id;
    }

    @SerializedName("place_id")
    public void setPlaceId(String place_id) {
      this.place_id = place_id;
    }

    @SerializedName("plus_code")
    public PlusCode getPlusCode() {
      return plus_code;
    }

    @SerializedName("plus_code")
    public void setPlusCode(PlusCode plus_code) {
      this.plus_code = plus_code;
    }

    @SerializedName("rating")
    public double getRating() {
      return rating;
    }

    @SerializedName("rating")
    public void setRating(double rating) {
      this.rating = rating;
    }

    @SerializedName("reference")
    public String getReference() {
      return reference;
    }

    @SerializedName("reference")
    public void setReference(String reference) {
      this.reference = reference;
    }

    @SerializedName("scope")
    public String getScope() {
      return scope;
    }

    @SerializedName("scope")
    public void setScope(String scope) {
      this.scope = scope;
    }

    @SerializedName("types")
    public Types3[] getTypes() {
      return types;
    }

    @SerializedName("types")
    public void setTypes(Types3[] types) {
      this.types = types;
    }

    @SerializedName("user_ratings_total")
    public int getUserRatingsTotal() {
      return user_ratings_total;
    }

    @SerializedName("user_ratings_total")
    public void setUserRatingsTotal(int user_ratings_total) {
      this.user_ratings_total = user_ratings_total;
    }

    @SerializedName("vicinity")
    public String getVicinity() {
      return vicinity;
    }

    @SerializedName("vicinity")
    public void setVicinity(String vicinity) {
      this.vicinity = vicinity;
    }
  }

  class Types4 {
    private String business_status;
    private Geometry geometry;
    private String icon;
    private String icon_background_color;
    private String icon_mask_base_uri;
    private String name;
    private boolean permanently_closed;
    private Photos photos;
    private String place_id;
    private PlusCode plus_code;
    private double rating;
    private String reference;
    private String scope;
    private Types3[] types;
    private int user_ratings_total;
    private String vicinity;

    @SerializedName("business_status")
    public String getBusinessStatus() {
      return business_status;
    }

    @SerializedName("business_status")
    public void setBusinessStatus(String business_status) {
      this.business_status = business_status;
    }

    @SerializedName("geometry")
    public Geometry getGeometry() {
      return geometry;
    }

    @SerializedName("geometry")
    public void setGeometry(Geometry geometry) {
      this.geometry = geometry;
    }

    @SerializedName("icon")
    public String getIcon() {
      return icon;
    }

    @SerializedName("icon")
    public void setIcon(String icon) {
      this.icon = icon;
    }

    @SerializedName("icon_background_color")
    public String getIconBackgroundColor() {
      return icon_background_color;
    }

    @SerializedName("icon_background_color")
    public void setIconBackgroundColor(String icon_background_color) {
      this.icon_background_color = icon_background_color;
    }

    @SerializedName("icon_mask_base_uri")
    public String getIconMaskBaseUri() {
      return icon_mask_base_uri;
    }

    @SerializedName("icon_mask_base_uri")
    public void setIconMaskBaseUri(String icon_mask_base_uri) {
      this.icon_mask_base_uri = icon_mask_base_uri;
    }

    @SerializedName("name")
    public String getName() {
      return name;
    }

    @SerializedName("name")
    public void setName(String name) {
      this.name = name;
    }

    @SerializedName("permanently_closed")
    public boolean getPermanentlyClosed() {
      return permanently_closed;
    }

    @SerializedName("permanently_closed")
    public void setPermanentlyClosed(boolean permanently_closed) {
      this.permanently_closed = permanently_closed;
    }

    @SerializedName("photos")
    public Photos getPhotos() {
      return photos;
    }

    @SerializedName("photos")
    public void setPhotos(Photos photos) {
      this.photos = photos;
    }

    @SerializedName("place_id")
    public String getPlaceId() {
      return place_id;
    }

    @SerializedName("place_id")
    public void setPlaceId(String place_id) {
      this.place_id = place_id;
    }

    @SerializedName("plus_code")
    public PlusCode getPlusCode() {
      return plus_code;
    }

    @SerializedName("plus_code")
    public void setPlusCode(PlusCode plus_code) {
      this.plus_code = plus_code;
    }

    @SerializedName("rating")
    public double getRating() {
      return rating;
    }

    @SerializedName("rating")
    public void setRating(double rating) {
      this.rating = rating;
    }

    @SerializedName("reference")
    public String getReference() {
      return reference;
    }

    @SerializedName("reference")
    public void setReference(String reference) {
      this.reference = reference;
    }

    @SerializedName("scope")
    public String getScope() {
      return scope;
    }

    @SerializedName("scope")
    public void setScope(String scope) {
      this.scope = scope;
    }

    @SerializedName("types")
    public Types3[] getTypes() {
      return types;
    }

    @SerializedName("types")
    public void setTypes(Types3[] types) {
      this.types = types;
    }

    @SerializedName("user_ratings_total")
    public int getUserRatingsTotal() {
      return user_ratings_total;
    }

    @SerializedName("user_ratings_total")
    public void setUserRatingsTotal(int user_ratings_total) {
      this.user_ratings_total = user_ratings_total;
    }

    @SerializedName("vicinity")
    public String getVicinity() {
      return vicinity;
    }

    @SerializedName("vicinity")
    public void setVicinity(String vicinity) {
      this.vicinity = vicinity;
    }
  }

class Photos {
    private int height;
    private String[] html_attributions;
    private String photo_reference;
    private int width;

    @SerializedName("height")
    public int getHeight() {
      return height;
    }

    @SerializedName("height")
    public void setHeight(int height) {
      this.height = height;
    }

    @SerializedName("html_attributions")
    public String[] getHtmlAttributions() {
      return html_attributions;
    }

    @SerializedName("html_attributions")
    public void setHtmlAttributions(String[] html_attributions) {
      this.html_attributions = html_attributions;
    }

    @SerializedName("photo_reference")
    public String getPhotoReference() {
      return photo_reference;
    }

    @SerializedName("photo_reference")
    public void setPhotoReference(String photo_reference) {
      this.photo_reference = photo_reference;
    }

    @SerializedName("width")
    public int getWidth() {
      return width;
    }

    @SerializedName("width")
    public void setWidth(int width) {
      this.width = width;
    }
  }

  class PlusCode {
    private String compound_code;
    private String global_code;

    @SerializedName("compound_code")
    public String getCompoundCode() {
      return compound_code;
    }

    @SerializedName("compound_code")
    public void setCompoundCode(String compound_code) {
      this.compound_code = compound_code;
    }

    @SerializedName("global_code")
    public String getGlobalCode() {
      return global_code;
    }

    @SerializedName("global_code")
    public void setGlobalCode(String global_code) {
      this.global_code = global_code;
    }
  }





