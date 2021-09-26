package com.example.labratour.data.Entity;

import android.util.Pair;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class UserEntity  {
    public String userId = null;
    @SerializedName("countrate")
    public int countRate = 0;
    @SerializedName("fullname")
    public String fullName = null;
    @SerializedName("email")
    public String email = null;
    @SerializedName("username")
    public String userName = null;
    @SerializedName("phone")
    public String Phone = null;
    @SerializedName("firstname")
    public String first_name = null;
    @SerializedName("lastname")
    public String last_name = null;
    @SerializedName("address")
    public String address;
    @SerializedName("atributes")
    public HashMap<String, Object> atributes;

    public UserEntity(){
this.atributes = initAtributesMap();
    }
    public UserEntity(String userId) {
        this.userId = userId;
        this.atributes = initAtributesMap();
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


    public UserEntity(String userId, int countRate, String password, String fullName, String email, String userName, String phone, String last_name, String first_name, String address, HashMap<String, Object> atributes) {
        this.userId = userId;
        this.countRate = countRate;
      //  this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.userName = userName;
        Phone = phone;
        this.last_name = last_name;
        this.first_name = first_name;
        this.address = address;
        setAtributes(atributes);
    }

        public void setAtributes(HashMap<String, Object> atributes) {
                  this.atributes.putAll(atributes);
            };

        public String getUserId() {
            return userId;
        }

        public void setUserName(String userName) {
            userName = userName;
        }

        public void setPhone(String phone) {
            Phone = phone;
        }

        public void setAddress(String address) {
            this.address = address;
        }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
            return userName;
        }


    public String getPhone() {
            return Phone;
        }

        public String getAddress() {
            return address;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
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
