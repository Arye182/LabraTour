package com.example.labratour.data.datasource;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class UserAuth {
  //
  // private final FirebaseConteiner conteiner = new ;
  private final FirebaseAuth firebaseAuth;
  // private final FirebaseDatabase database;
  DatabaseReference usersDBref;

  // myRef.setValue("Hello, World!");
  public UserAuth(FirebaseAuth firebaseAuth) {
    this.firebaseAuth = firebaseAuth;
    // this.database = FirebaseDatabase.getInstance();
    this.usersDBref = FirebaseDatabase.getInstance().getReference("users");
  }

  //    public void writeNewUser(String userId, String password, String email) {
  //        UserEntity user = new UserEntity(email, password);
  //
  //        usersDBref.child("users").child(userId).setValue(user);
  //
  //    }
  public Observable<AuthResult> login(final String email, final String password) {
    return Observable.create(
        new ObservableOnSubscribe<AuthResult>() {

          @Override
          // happens when an observer subscribe(for example the observer in execute)
          public void subscribe(ObservableEmitter<AuthResult> emitter) throws Exception {
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(
                    new OnSuccessListener<AuthResult>() {

                      @Override
                      public void onSuccess(AuthResult authResult) {
                        if (!(emitter.isDisposed())) {
                          emitter.onNext(authResult);
                        }
                      }
                    })
                .addOnFailureListener(
                    new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull @NotNull Exception e) {
                        if (!emitter.isDisposed()) {
                          emitter.onError(e);
                        }
                      }
                    });
          }
        });
  }

  public Observable<AuthResult> register(final String email, final String password) {
    return Observable.create(
        new ObservableOnSubscribe<AuthResult>() {

          @Override
          public void subscribe(ObservableEmitter<AuthResult> emitter) throws Exception {
            try {
              firebaseAuth
                  .createUserWithEmailAndPassword(email, password)
                  .addOnCompleteListener(
                      new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                          if (task.isSuccessful()) {
                            if (task.getResult() != null) {
                              if (!(emitter.isDisposed())) {
                                emitter.onNext(task.getResult());
                              }
                            } else {
                              if (!(emitter.isDisposed())) {
                                emitter.onError(new Exception("register task result is null"));
                              }
                            }
                          } else {
                            emitter.onError(new Exception("Task of register not succesful"));
                          }
                        }
                      })
                  .addOnFailureListener(
                      new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                          if (!emitter.isDisposed()) {
                            emitter.onError(e);
                          }
                        }
                      });
            } catch (Exception e) {
              if (!(emitter.isDisposed())) {
                emitter.onError(e.getCause());
              }
            }
          }
        });
  }



}
//        if (!(user==null)){
//            user.updateProfile().

//
//    ValueEventListener postListener = new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            // Get Post object and use the values to update the UI
//            Post post = dataSnapshot.getValue(Post.class);
//            // ..
//        }
//
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//            // Getting Post failed, log a message
//            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//        }
//    };
//mPostReference.addValueEventListener(postListener);
//}}

