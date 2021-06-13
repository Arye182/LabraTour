package com.example.labratour.data.Entity;

import com.google.gson.annotations.SerializedName;

public class UserEntity {



    @SerializedName("password")
    private String password;
    @SerializedName("email")
    private String email;
    private String uid;
    private String username;
    public UserEntity() {
        /*
         * Default constructor required for calls to DataSnapshot.getValue(UserEntity.class)
         */
    }

    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
