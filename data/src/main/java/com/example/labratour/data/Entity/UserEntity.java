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
    public HashMap<String, Object> atributes = null;

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



        public void setAtributes(HashMap<String, Object> atributes) {
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

        public HashMap<String, Object> getAtributes() {
            return atributes;
        }
    public HashMap<String, Object> initAtributesMap() {
        HashMap<String, Object> atributesDict = new HashMap<>();
        atributesDict.put("ratesCounter",0);
        atributesDict.put("pricelevel", 0);
        atributesDict.put("useraggragaterating", 0);
        atributesDict.put("always_open", 0);
        atributesDict.put("casino", 0);
        atributesDict.put("cafe", 0);
        atributesDict.put("restaurant", 0);
        atributesDict.put("rv_park", 0);
        atributesDict.put("shopping_mall", 0);
        atributesDict.put("amusement_park", 0);
        atributesDict.put("aquarium",0);
        atributesDict.put("art_gallery",0);
        atributesDict.put("campground",0 );
        atributesDict.put("night_club",0 );
        atributesDict.put("painter", 0);
        atributesDict.put("movie_theater", 0);
        atributesDict.put("museum",0 );
        atributesDict.put("spa", 0);
        atributesDict.put("stadium",0 );
        atributesDict.put("touristAttraction", 0);
        atributesDict.put("zoo",0 );
        atributesDict.put("gym",0 );
        return atributesDict;
    }







}
