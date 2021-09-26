package com.example.labratour.data.datasource;

import androidx.annotation.NonNull;

import com.example.labratour.data.Entity.UserEntity;
import com.example.labratour.data.Entity.mapper.UserDataMapper;
import com.example.labratour.domain.Entity.UserDomain;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;

//public static final String CHILD_USERS = "users";
public class UserEntityFirebaseStore {


        private FirebaseDatabase database;

  public UserEntityFirebaseStore(FirebaseDatabase firebaseDatabase) {
// this.database.setPersistenceEnabled(true);
       //   firebaseDatabase.setPersistenceEnabled(true);

    this.database = FirebaseDatabase.getInstance();
  }

public Observable<Void> createUserIfNotExists(UserDomain userDomain, String id) {

    UserEntity userEntity = UserDataMapper.transform(userDomain);
    return Observable.create(
        new ObservableOnSubscribe<Void>() {
          @Override
          public void subscribe(ObservableEmitter<Void> emitter) throws Exception {

              Task<Void> task= database
                  .getReference()
                  .child("users")
                  .
                   push()
                  .setValue(userEntity).addOnSuccessListener(new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void unused) {

                          emitter.onNext(Void.TYPE.cast(unused));}}).addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull @NotNull Exception e) {
                              emitter.onError(e.getCause());
                          }
                      });}});}
//                  .addOnSuccessListener(
//                      new OnSuccessListener<Void>() {
//
//                          @Override
//                          public void onSuccess(Optional<Void> unused) {
//
//                                       emitter.onNext(new (unused));
//                          }
//
//                          @Override
//                          public void onSuccess(Object o) {
//                              emitter.onNext(Res);
//                          }
//                        })
//
//                  .addOnFailureListener(
//                      new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull @NotNull Exception e) {
//                                emitter.onError(e);
//                        }
//                            });} catch (Exception exception) {
//                if(userEntity==null){
//                Log.i(
//                        "signup",
//                        "transform user domain to entity failed userEntityFirebaseStore.createUserIfNotExists");}else {
//                    Log.i(
//                            "signup",
//                            "exception when trying to save new user userEntityFirebaseStore.createUserIfNotExists"+userEntity.toString());
//                }
//                    emitter.onError(exception);
//
//
//                      }}});}












public Observable updateUser(UserEntity userEntity,String  successResponse) {
        DatabaseReference userReference  = database.getReference().child("users").child(userEntity.getUserId());
        return updateNotNewUser(userReference, userEntity, successResponse);
        }


public Single<List<UserEntity>> getUsers() {
        List<UserEntity> users = new ArrayList<>();
        //BiConsumer<DisposableSubscriber<UserEntity>, DataSnapshot> onNextAction = (subscriber, dataSnapshot) -> subscriber.onNext(extractList(dataSnapshot, UserEntity.class));
        return Single.create(emitter -> {
                ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                                UserEntity userEntity = childSnapshot.getValue(UserEntity.class);
                                                userEntity.setUserId(childSnapshot.getKey());
                                                users.add(userEntity);
                                                emitter.onSuccess(users);
                                        }
                                } else {
                                        emitter.onError(new IllegalArgumentException("Unable to find any shopping lists"));
                                }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                               // Timber.e(databaseError.toException(), "getItems:onCancelled.");
                                emitter.onError(databaseError.toException());
                        }
                };
                Query query = database.getReference().child("users").orderByKey();
                query.addValueEventListener(eventListener);
                emitter.setCancellable(()-> query.removeEventListener(eventListener));
        });
}





public Observable<UserEntity> getUserRealtime(String userId) {
        Query query = database.getReference().child("users").child(userId);
        return Observable.create(emitter -> {
                ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange( @NotNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                        UserEntity userEntity = snapshot.getValue(UserEntity.class);
                                        userEntity.setUserId(snapshot.getKey());
                                        emitter.onNext(userEntity);
                                }
                        }
                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                emitter.onError(error.toException());
                        }
                };
                query.addValueEventListener(valueEventListener);
                emitter.setCancellable(()->  query.removeEventListener(valueEventListener));

        });
}


private <R> Observable<R> updateNotNewUser(DatabaseReference databaseReference, UserEntity userEntity, R successResponse) {
                //return postQuery(databaseReference, value, false, successResponse);
                return Observable.create(new ObservableOnSubscribe() {
                        @Override
                        public void subscribe(ObservableEmitter e) throws Exception {
                                DatabaseReference reference = databaseReference;
                                reference.setValue(userEntity, (databaseError, databaseReference1) -> {
                                        if (databaseError == null) {
                                                e.onNext(successResponse);
                                        } else {
                                                e.onError(new FirebaseException(databaseError.getMessage()));
                                        }
                                });
                        }
                });
        }}


//myRef.child("someChild").child("name").setValue(name)




