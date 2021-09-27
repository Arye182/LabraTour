package com.example.labratour.data.repositories;

import com.example.labratour.data.Entity.mapper.UserDataMapper;
import com.example.labratour.data.datasource.UserAuth;
import com.example.labratour.data.datasource.UserEntityFirebaseStore;
import com.example.labratour.domain.UserAtributes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class AtributesRepositoryImpl implements AtributesRepository{

    private final UserAuth userAuth;

    private final UserEntityFirebaseStore userEntityFirebaseStore;


    public AtributesRepositoryImpl(FirebaseAuth firebaseAuth, FirebaseDatabase database) {
        this.userAuth = new UserAuth(firebaseAuth.getInstance());
        // database.setPersistenceEnabled(true);

        this.userEntityFirebaseStore = new UserEntityFirebaseStore(database);
    }
    public Single<UserAtributes> getUserAtributes(String userId) {
        return userEntityFirebaseStore.getUserAtributes(userId).map(new Function<HashMap<String, Object>, UserAtributes>() {
            @Override
            public UserAtributes apply(HashMap<String, Object> stringObjectHashMap) throws Exception {
                return UserDataMapper.transform(stringObjectHashMap);
            }
        });
    }

    public Single<String> updateNewAtributes(UserAtributes userAtributes, String userId) {
        return this.userEntityFirebaseStore.updateUserAtributes(userAtributes, userId);
    }

}
