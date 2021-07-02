package com.example.labratour.domain.Entity;

import com.example.labratour.domain.Atributes;

public class UserDomain {
    private final String userId;





    public UserDomain(String  userId) {
        this.userId = userId;

    }


    private String fullName;
    private String email;
    private String UserName;
    private String Phone;
    private String address;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setCountRates(int countRates) {
        this.countRates = countRates;
    }

    public String getLast_name() {
        return last_name;
    }

    private Atributes atributes;
private String first_name;
private String last_name;
    public int getCountRates() {
        return countRates;
    }

    private int countRates;

    public void setAtributes(Atributes atributes) {
        this.atributes = atributes;
    }
//    private Object clusters;

    public String getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return UserName;
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

    public  Atributes getAtributes() {
        return atributes;
    }

//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }


}
