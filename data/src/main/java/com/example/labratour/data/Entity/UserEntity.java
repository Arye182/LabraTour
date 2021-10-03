package com.example.labratour.data.Entity;

import android.util.Pair;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class UserEntity  {
    @SerializedName("userId")

    public String userId = null;
    @SerializedName("countrate")
    public int countRate = 0;
    @SerializedName("email")
    public String email = null;
    @SerializedName("username")
    public String userName = null;
    @SerializedName("firstname")
    public String first_name = null;
    @SerializedName("lastname")
    public String last_name = null;
    @SerializedName("atributes")
    private HashMap<String, Double> atributes;

    public UserEntity(){
this.atributes = initAtributesMap();
    }
    public UserEntity(String userId) {
        this.userId = userId;
        this.atributes = initAtributesMap();
    }
    public String getUserId() {
        return userId;
    }
    public int getCountRate() {
        return countRate;
    }

    public void setCountRate(int countRate) {
        this.countRate = countRate;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFirst_name() {
        return first_name;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }



        public void setAtributes(HashMap<String, Double> atributes) {
                  this.atributes.putAll(atributes);
            };

        public void setUserName(String userName) {
            this.userName = userName;
        }


    public String getUserName() {
            return userName;
        }


        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public HashMap<String, Double> getAtributes() {
            return atributes;
        }
    public HashMap<String, Double> initAtributesMap() {
        HashMap<String, Double> atributesDict = new HashMap<>();
        atributesDict.put("ratesCounter", (double) 0);
        atributesDict.put("price_level", (double) 0);
        atributesDict.put("useraggragaterating", 0.);
        atributesDict.put("always_open", Double.valueOf(0));
        atributesDict.put("casino", (double) 0);
        atributesDict.put("cafe", (double) 0);
        atributesDict.put("restaurant", (double) 0);
        atributesDict.put("rv_park", (double) 0);
        atributesDict.put("shopping_mall", (double) 0);
        atributesDict.put("amusement_park", (double) 0);
        atributesDict.put("aquarium", (double) 0);
        atributesDict.put("art_gallery", (double) 0);
        atributesDict.put("campground", (double) 0);
        atributesDict.put("night_club", (double) 0);
        atributesDict.put("painter", (double) 0);
        atributesDict.put("movie_theater", (double) 0);
        atributesDict.put("museum", (double) 0);
        atributesDict.put("spa", (double) 0);
        atributesDict.put("stadium", (double) 0);
        atributesDict.put("touristAttraction", (double) 0);
        atributesDict.put("zoo", (double) 0);
        atributesDict.put("gym", (double) 0);
        return atributesDict;
    }







}
