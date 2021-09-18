package com.example.labratour.data.Entity;

import com.example.labratour.domain.Atributes;
import com.google.gson.annotations.SerializedName;

public class UserEntity  {







private String userId = null;
    @SerializedName("countrate")
 private int    countRate;

    public int getCountRate() {
        return countRate;
    }

    public void setCountRate(int countRate) {
        this.countRate = countRate;
    }

    private String password;
    @SerializedName("fullname")
private String fullName = null;
private String email = null;
    @SerializedName("username")
private String userName = null;

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
    @SerializedName("phone")
    private String Phone = null;
    @SerializedName("lastname")
private String last_name = null;

    public UserEntity(String email, String password, String first_name, String last_name) {
        this.password = password;
        this.email = email;
        this.last_name = last_name;
        this.first_name = first_name;
        this.atributes = new Atributes();
    }
    @SerializedName("firstname")
    private String first_name;

        public void setAtributes(Atributes atributes) {
            this.atributes = atributes;
        }


//
//     //   public SpscArrayQueue<RateEntity> getRates() {
//            return rates;
//        }
@SerializedName("address")
        private String address;
    @SerializedName("atributes")
        private Atributes atributes;
       // private SpscArrayQueue<RateEntity> rates;
//    private Object clusters;
    public UserEntity() {}
    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;

    }
    public UserEntity(String userId) {this.userId = userId;
    }

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

        public Atributes getAtributes() {
            return atributes;
        }








    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
