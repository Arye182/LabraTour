package com.example.labratour.data.datasource;

import androidx.annotation.NonNull;

import com.example.labratour.data.Entity.UserEntity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;

public class CloudUserDataSource {
  //
  private final FirebaseAuth firebaseAuth;

  public CloudUserDataSource(FirebaseAuth firebaseAuth) {
    this.firebaseAuth = firebaseAuth;
  }

  public Observable<AuthResult> login(final UserEntity userEntity) {
    return Observable.create(
        emitter -> {
          Task<AuthResult> authResultTask =
              firebaseAuth.signInWithEmailAndPassword(
                  userEntity.getEmail(), userEntity.getPassword());
          if (authResultTask != null) {
            authResultTask.addOnFailureListener(
                new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull @NotNull Exception e) {
                    if (!(emitter.isDisposed())) {
                        emitter.onError(authResultTask.getException());
                    }
                  }
                });
            authResultTask.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    if(!emitter.isDisposed())
                    emitter.onComplete();
                    }});

                                                    }
                                                }
            );
          }
        }







//public class CloudUserDataSource {
//
//    private final FirebaseFirestore firestore;
//
//    @Autowired
//    public CloudUserDataSource(FirebaseFirestore firestore) {
//        this.firestore = firestore;
//    }
//
//    public String insert(UserEntity userEntity) throws ExecutionException, InterruptedException {
//        ApiFuture<DocumentReference> result =
//                firestore.collection("users")
//                        .add(userEntity);
//
//        return result.get().getId();
//    }
//
//    public List<UserEntity> getAll() throws ExecutionException, InterruptedException {
//        List<UserEntity> lstCourseEntity = new ArrayList<>();
//
//        ApiFuture<QuerySnapshot> query =
//                firestore.collection("users").get();
//
//        QuerySnapshot querySnapshot = query.get();
//
//        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
//
//        for (QueryDocumentSnapshot document : documents) {
//            lstCourseEntity.add(document.toObject(CourseEntity.class));
//        }
//
//        return lstCourseEntity;
//    }
