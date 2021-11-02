package com.example.labratour.data.repositories;

import com.example.labratour.data.datasource.UserAuth;
import com.example.labratour.data.datasource.UserEntityFirebaseStore;
import com.example.labratour.domain.Entity.UserDomain;
import com.example.labratour.domain.UserAtributes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

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

  public Single<HashMap<String, Double>> getUserAtributes(String userId) {
    return userEntityFirebaseStore
        .getUserAtributes(userId)
        .map(
            new Function<DataSnapshot, HashMap<String, Double>>() {

              @Override
              public HashMap<String, Double> apply(DataSnapshot dataSnapshot) throws Exception {
                UserDomain userEntity = new UserDomain();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                  userEntity.setUserId(ds.child("userId").getValue(String.class));
                  userEntity.setEmail(ds.child("email").getValue(String.class));
                  userEntity.setCountRate(ds.child("countRate").getValue(double.class));
                  userEntity.setUserName(ds.child("userName").getValue(String.class));
                  userEntity.setAtributes(ds.child("atributes").getValue(UserAtributes.class));
                }
                return Objects.requireNonNull(dataSnapshot
                        .child("atributes")
                        .getValue(UserAtributes.class))
                    .getUserAtributes();
              }
            });
    //  .map(
    //            new Function<UserDomain, HashMap<String, Double>>() {
    //              @Override
    //              public HashMap<String, Double> apply(UserDomain userEntity) {
    //                if (userEntity.getAtributes() == null) {
    //               Log.i("Atributes", "userEntity.getAtributes()=null)");}
    //                return userEntity.getAtributes();
    //
    //
    //            }});}

  }

    public Single<Void> updateNewAtributes(HashMap<String, Double> userAtributes, String userId) {

        return this.userEntityFirebaseStore.updateUserAtributes(userAtributes, userId);
    }

}
