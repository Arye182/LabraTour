package com.example.labratour.data.repositories;

import com.example.labratour.data.datasource.UserAuth;
import com.example.labratour.data.datasource.UserEntityFirebaseStore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import io.reactivex.Single;

public class AtributesRepositoryImpl implements AtributesRepository{

    private final UserAuth userAuth;

    private final UserEntityFirebaseStore userEntityFirebaseStore;


    public AtributesRepositoryImpl(FirebaseAuth firebaseAuth, FirebaseDatabase database) {
        this.userAuth = new UserAuth(firebaseAuth.getInstance());
        // database.setPersistenceEnabled(true);

        this.userEntityFirebaseStore = new UserEntityFirebaseStore(database);
    }
    public Single<HashMap<String, Double>> getUserAtributes(String userId) {
        return userEntityFirebaseStore.getUserAtributes(userId);
//                .map(new Function<HashMap<String, Object>, UserAtributes>() {
//            @Override
//            public UserAtributes apply(HashMap<String, Object> stringObjectHashMap) throws Exception {
//                return UserDataMapper.transform(stringObjectHashMap);
//            }
//        });
    }

    public Single<Void> updateNewAtributes(HashMap<String, Double> userAtributes, String userId) {

        return this.userEntityFirebaseStore.updateUserAtributes(userAtributes, userId);
    }

}
