package com.example.labratour.domain.Entity;

import com.example.labratour.domain.UserAtributes;

import java.util.Map;

public class UserDomain {
    private  String userId;

//    public UserDomain(String email, String password, String first_name, String last_name) {
//        this.email = email;
//        this.first_name = first_name;
//        this.last_name = last_name;
//    }

    public UserDomain(String  userId) {
        this.userId = userId;
        this.atributes = new UserAtributes();

    }
    public UserDomain() {
        this.atributes = new UserAtributes();

    }


    public UserDomain(String userId, String email, String userName, Map<String, Integer> unUpdatedRates, UserAtributes atributes, String first_name, String last_name) {
        this.userId = userId;
        this.email = email;
        UserName = userName;
        this.unUpdatedRates = unUpdatedRates;
        this.atributes = atributes;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    //private String fullName = null;
    private String email = null;
    //private String password;
    public void setUnUpdatedRates(Map<String, Integer> unUpdatedRates) {
        this.unUpdatedRates = unUpdatedRates;
    }

    private String UserName = null;
  //  private String Phone = null;
   // private String address = null;
private Map<String, Integer> unUpdatedRates = null;
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }



    public String getLast_name() {
        return last_name;
    }

    private UserAtributes atributes;
private String first_name = null;
private String last_name = null;

   // private int countRates;

    public void setAtributes(UserAtributes atributes) {
        this.atributes = atributes;
    }
//    private Object clusters;

    public String getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }



    public String getUserName() {
        return UserName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public  UserAtributes getAtributes() {
        return atributes;
    }



}
