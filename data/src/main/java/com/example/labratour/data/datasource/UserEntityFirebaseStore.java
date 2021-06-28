package com.example.labratour.data.datasource;

import androidx.annotation.NonNull;

import com.example.labratour.data.Entity.UserEntity;
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


        private final FirebaseDatabase database;

  public UserEntityFirebaseStore() {
// this.database.setPersistenceEnabled(true);
    this.database = FirebaseDatabase.getInstance();
   //     this.database.setPersistenceEnabled(true);
  }

public Observable createUserIfNotExists(UserEntity userEntity) {
DatabaseReference databaseReference = database.getReference().child("users").child(userEntity.getUserId());
return Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> e) throws Exception {
                DatabaseReference reference = databaseReference;

                if (userEntity.getUserId() == null) {
                        reference = reference.push();
                        userEntity.setUserId(reference.getKey());
                } else {
                        reference = databaseReference.child(userEntity.getUserId());
                }

                reference.setValue(userEntity, (databaseError, databaseReference1) -> {
                        if (databaseError == null) {
                                e.onNext(userEntity.getUserId());
                        } else {
                                e.onError(new FirebaseException(databaseError.getMessage()));
                        }
                });
        }
});
}



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





public Observable<UserEntity> getUser(String userId) {
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
        }



        }


