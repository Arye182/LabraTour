package com.example.labratour.data.datasource;

import androidx.annotation.NonNull;

import com.example.labratour.data.Entity.UserEntity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.rpc.context.AttributeContext;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class CloudUserDataSource {
  //
  // private final FirebaseConteiner conteiner = new ;
  private final FirebaseAuth firebaseAuth;
  // private final FirebaseDatabase database;
  DatabaseReference usersDBref;

  // myRef.setValue("Hello, World!");
  public CloudUserDataSource(FirebaseAuth firebaseAuth) {
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
  public Observable<Void> login(final UserEntity userEntity) {
    return Observable.create(
        new ObservableOnSubscribe<AuthResult>() {


            @Override
          public void subscribe(ObservableEmitter<AuthResult> emitter) throws Exception {
            firebaseAuth
                .signInWithEmailAndPassword(userEntity.getEmail(), userEntity.getPassword())
                .addOnSuccessListener(
                    new OnSuccessListener<Void>() {




                        public void onSuccess(Void unused) {
                            if (!(emitter.isDisposed())) {
                                emitter.onNext();
                            }
                        }

                      }
                    )
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
  }}




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
