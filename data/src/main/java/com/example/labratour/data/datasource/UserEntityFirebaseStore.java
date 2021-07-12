package com.example.labratour.data.datasource;

import androidx.annotation.NonNull;

import com.example.labratour.data.Entity.UserEntity;
import com.example.labratour.data.Entity.mapper.EntityJsonMapper;
import com.example.labratour.data.Entity.mapper.UserHashMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
//import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
//public static final String CHILD_USERS = "users";
public class UserEntityFirebaseStore {


        private final DatabaseReference database;
private final EntityJsonMapper<UserEntity> JsonMapper;

  public UserEntityFirebaseStore(FirebaseDatabase firebaseDatabase) {
// this.database.setPersistenceEnabled(true);
       //   firebaseDatabase.setPersistenceEnabled(true);

    this.database = firebaseDatabase.getReference("users");
    this.JsonMapper = new UserHashMapper();
  }

public Observable<Void> createUserIfNotExists(UserEntity userEntity) {
DatabaseReference databaseReference = database.child((FirebaseAuth.getInstance().getCurrentUser().getUid()));
    return Observable.create(
        new ObservableOnSubscribe<Void>() {
          @Override
          public void subscribe(ObservableEmitter<Void> emitter) throws Exception {
            DatabaseReference reference = databaseReference;
            reference
                .setValue(userEntity)
                .addOnCompleteListener(
                    new OnCompleteListener<Void>() {

                      @Override
                      public void onComplete(@NotNull Task<Void> task) {
                        FirebaseAuth.getInstance().signOut();
                        emitter.onNext(task.getResult());
                        // redirect the user to the login screen

                      }
                    })
                .addOnFailureListener(
                    new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull @NotNull Exception e) {

                        FirebaseAuth.getInstance().signOut();
                        emitter.onError(e);
                      }
                    });
          }
        });
        }










public Observable updateUser(UserEntity userEntity,String  successResponse) {
        DatabaseReference userReference  = database.child(userEntity.getUserId());
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
                Query query = database.orderByKey();
                query.addValueEventListener(eventListener);
                emitter.setCancellable(()-> query.removeEventListener(eventListener));
        });
}





public Observable<UserEntity> getUser(String userId) {
        Query query = database.child(userId);
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

//
//        public Observable<Void> savenewUnupdatedRatesToCurrentUser(Map<String, Integer> newRates) {
//          DatabaseReference currentUserReference  = database.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//        }
}
//        private void writeNewPost(String userId, String username, String title, String body) {
//                // Create new post at /user-posts/$userid/$postid and at
//                // /posts/$postid simultaneously
//                String key = mDatabase.child("users").push().getKey();
//           //     Post user = new Post(userId, username, title, body);
//           //     Map<String, Object> userValues =
//
//                childUpdates.put("/posts/" + key, postValues);
//                childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
//
//                mDatabase.updateChildren(childUpdates);
//        }




