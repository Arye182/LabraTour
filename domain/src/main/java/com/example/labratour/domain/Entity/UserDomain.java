package com.example.labratour.domain.Entity;

import com.example.labratour.domain.UserAtributes;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
public class UserDomain  {
    @SerializedName("userId")

    public String userId = null;
    @SerializedName("countrate")
    public double countRate = 0;
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
    private UserAtributes userAtributes ;

    public UserDomain(){

    }
    public UserDomain(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }
    public double getCountRate() {
        return countRate;
    }

    public void setCountRate(double countRate) {
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
    public void setAtributes(UserAtributes atributes) {
this.userAtributes = atributes;
    }




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
        atributesDict.put("always_open", 0.);
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

//
//public class UserDomain {
//    private  String userId;
//
//
//
//    public UserDomain(String  userId) {
//        this.userId = userId;
//        this.atributes = initAtributesMap();
//
//    }
//    public UserDomain() {
//        this.atributes = initAtributesMap();
//
//    }
//    public HashMap<String, Double> initAtributesMap() {
//        HashMap<String, Double> atributesDict = new HashMap<>();
//        atributesDict.put("ratesCounter", (double) 0);
//        atributesDict.put("price_level", (double) 0);
//        atributesDict.put("useraggragaterating", 0.);
//        atributesDict.put("always_open", Double.valueOf(0));
//        atributesDict.put("casino", (double) 0);
//        atributesDict.put("cafe", (double) 0);
//        atributesDict.put("restaurant", (double) 0);
//        atributesDict.put("rv_park", (double) 0);
//        atributesDict.put("shopping_mall", (double) 0);
//        atributesDict.put("amusement_park", (double) 0);
//        atributesDict.put("aquarium", (double) 0);
//        atributesDict.put("art_gallery", (double) 0);
//        atributesDict.put("campground", (double) 0);
//        atributesDict.put("night_club", (double) 0);
//        atributesDict.put("painter", (double) 0);
//        atributesDict.put("movie_theater", (double) 0);
//        atributesDict.put("museum", (double) 0);
//        atributesDict.put("spa", (double) 0);
//        atributesDict.put("stadium", (double) 0);
//        atributesDict.put("touristAttraction", (double) 0);
//        atributesDict.put("zoo", (double) 0);
//        atributesDict.put("gym", (double) 0);
//        return atributesDict;
//    }
//
//
//    public UserDomain(String userId, String email, String userName, Map<String, Integer> unUpdatedRates, HashMap atributes, String first_name, String last_name) {
//        this.userId = userId;
//        this.email = email;
//        UserName = userName;
//        this.unUpdatedRates = unUpdatedRates;
//        this.atributes = atributes;
//        this.first_name = first_name;
//        this.last_name = last_name;
//    }
//
//    //private String fullName = null;
//    private String email = null;
//    //private String password;
//    public void setUnUpdatedRates(Map<String, Integer> unUpdatedRates) {
//        this.unUpdatedRates = unUpdatedRates;
//    }
//
//    private String UserName = null;
//  //  private String Phone = null;
//   // private String address = null;
//private Map<String, Integer> unUpdatedRates = null;
//    public String getFirst_name() {
//        return first_name;
//    }
//
//    public void setFirst_name(String first_name) {
//        this.first_name = first_name;
//    }
//
//    public void setLast_name(String last_name) {
//        this.last_name = last_name;
//    }
//
//
//
//    public String getLast_name() {
//        return last_name;
//    }
//
//    private HashMap atributes;
//private String first_name = null;
//private String last_name = null;
//
//   // private int countRates;
//
//    public void setAtributes(HashMap atributes) {
//        this.atributes = atributes;
//    }
////    private Object clusters;
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserName(String userName) {
//        UserName = userName;
//    }
//
//
//
//    public String getUserName() {
//        return UserName;
//    }
//
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public HashMap<String, Double> getAtributes() {
//        return atributes;
//    }
//
//
//
//}
