package com.example.Entity;

public class User {
    private final int userId;

    public User(int userId) {
        this.userId = userId;
    }

    private String fullName;
    private String email;
    private String homeDistrict;
//    private Object clusters;

    public int getUserId() {
        return userId;
    }

    public String getHomeDistrict() {
        return homeDistrict;
    }

    public void setHomeDistrict(String homeDistrict) {
        this.homeDistrict = homeDistrict;
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

//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }


}
