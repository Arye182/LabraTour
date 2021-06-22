package com.example.labratour.data.Entity;

import com.example.labratour.data.RateEntity;
import com.example.labratour.domain.Atributes;

import io.reactivex.internal.queue.SpscArrayQueue;

public class UserEntity  {







private String userId = null;






private String password;
private String fullName;
private String email;
private String userName;
private String Phone;

        public void setAtributes(Atributes atributes) {
            this.atributes = atributes;
        }



        public SpscArrayQueue<RateEntity> getRates() {
            return rates;
        }

        private String address;
        private Atributes atributes;
        private SpscArrayQueue<RateEntity> rates;
//    private Object clusters;
    public UserEntity() {}
    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;

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

    public void setRates(SpscArrayQueue<RateEntity> rates) {
        this.rates = rates;
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








    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
