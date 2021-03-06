package com.example.labratour.data.datasource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.labratour.data.Entity.UserEntity;
import com.example.labratour.data.Entity.mapper.UserDataMapper;
import com.example.labratour.domain.Entity.UserDomain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

// public static final String CHILD_USERS = "users";
public class UserEntityFirebaseStore {

  private DatabaseReference usersDBref;
  private FirebaseDatabase database;

  public UserEntityFirebaseStore(FirebaseDatabase firebaseDatabase) {
    // this.database.setPersistenceEnabled(true);
    //   firebaseDatabase.setPersistenceEnabled(true);
    this.usersDBref = FirebaseDatabase.getInstance().getReference("users");
    this.database = FirebaseDatabase.getInstance();
  }

  public Observable<String> createUserIfNotExists(UserDomain userDomain, String id) {

    UserEntity userEntity = UserDataMapper.transform(userDomain);
    return Observable.create(
        new ObservableOnSubscribe<String>() {
          @Override
          public void subscribe(ObservableEmitter<String> emitter) throws Exception {

            Task<Void> task =
                database
                    .getReference()
                    .child("users")
                    .push()
                    .setValue(userEntity)
                    .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                          @Override
                          public void onSuccess(Void unused) {

                            emitter.onNext("");
                          }
                        })
                    .addOnFailureListener(
                        new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull @NotNull Exception e) {
                            emitter.onError(e.getCause());
                          }
                        });
          }
        });
  }

  public Single<List<UserEntity>> getUsers() {
    List<UserEntity> users = new ArrayList<>();
    // BiConsumer<DisposableSubscriber<UserEntity>, DataSnapshot> onNextAction = (subscriber,
    // dataSnapshot) -> subscriber.onNext(extractList(dataSnapshot, UserEntity.class));
    return Single.create(
        emitter -> {
          ValueEventListener eventListener =
              new ValueEventListener() {
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
                    emitter.onError(
                        new IllegalArgumentException("Unable to find any shopping lists"));
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
          emitter.setCancellable(() -> query.removeEventListener(eventListener));
        });
  }

  public Single<UserDomain> getUser(String userId) {
    return Single.create(
        new SingleOnSubscribe<UserDomain>() {
          @Override
          public void subscribe(SingleEmitter<UserDomain> emitter) {
            try {
              usersDBref
                  .child(userId)
                  .get()
                  .addOnCompleteListener(
                      new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task task) {
                          if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                            emitter.onError(task.getException());
                          } else {
                            emitter.onSuccess((UserDomain) task.getResult());
                            Log.d("firebase", String.valueOf(task.getResult()));
                          }
                        }
                      })
                  .addOnFailureListener(
                      new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                          emitter.onError(e.getCause());
                        }
                      });
            } catch (Exception e) {
              emitter.onError(e.getCause());
            }
          }
        });
  }

  public Observable<UserEntity> getUserRealtime(String userId) {
    Query query = database.getReference().child("users").child(userId);
    return Observable.create(
        emitter -> {
          ValueEventListener valueEventListener =
              new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot snapshot) {
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
          emitter.setCancellable(() -> query.removeEventListener(valueEventListener));
        });
  }

  public Single<Void> updateUserAtributes(HashMap<String, Double> atributes, String id) {
    return Single.create(
        new SingleOnSubscribe<Void>() {
          @Override
          public void subscribe(SingleEmitter<Void> emitter) throws Exception {

            Log.i("UpdateUseCase", "before askjing from server");

            usersDBref
                .child(id)
                .child("atributes")
                .setValue(atributes)
                .addOnSuccessListener(
                    new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void unused) {
                        emitter.onSuccess(unused);
                      }
                    })
                .addOnFailureListener(
                    new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull @NotNull Exception e) {
                        Log.i("UpdateUseCase", "error: ");

                        emitter.onError(e.fillInStackTrace());
                      }
                    });
          }
        });
  }

  public Single<DataSnapshot> getUserAtributes(String userId) {
    Log.i("rate", "getuserAtr before subscribe " + userId);

    return Single.create(
        new SingleOnSubscribe<DataSnapshot>() {

          @Override
          public void subscribe(SingleEmitter<DataSnapshot> emitter) {
            usersDBref
                .child(userId)
                .get()
                .addOnCompleteListener(
                    new OnCompleteListener<DataSnapshot>() {
                      @Override
                      public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                        Log.i("rate", "getuserAtr before subscribe " + task);

                        emitter.onSuccess(task.getResult());
                      }
                    });
          }
        });
  }
                           }




                     // Log.i("rate", "getuserAtr current: " + currentUser.getUid()+currentUser.getEmail());







