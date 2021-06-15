package com.example.labratour.data.datasource;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Singleton;

@Singleton
public class UserDataSourceFactory {

    private final FirebaseAuth firebaseAuth;



    public UserDataSourceFactory(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;

    }


    public CloudUserDataSource createCloudDataSource() {



        return new CloudUserDataSource(firebaseAuth);
    }




}