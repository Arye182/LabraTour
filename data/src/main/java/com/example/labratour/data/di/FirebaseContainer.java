package com.example.labratour.data.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseContainer {
    // create the view model for login fragment manually with factory - we do that in OnCreate Method
    // instantiate firebase
    private final FirebaseAuth firebaseAuth;
    private final FirebaseDatabase database;

    public FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    public FirebaseDatabase getDatabase() {
        return FirebaseDatabase.getInstance();
    }

    public FirebaseContainer() {
        this.firebaseAuth =  FirebaseAuth.getInstance();
        this.database =  FirebaseDatabase.getInstance();
    }
}
