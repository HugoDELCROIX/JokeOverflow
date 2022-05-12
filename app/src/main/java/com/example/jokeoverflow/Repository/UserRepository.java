package com.example.jokeoverflow.Repository;

import android.net.Uri;

import com.example.jokeoverflow.Model.Joke;
import com.example.jokeoverflow.Model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UserRepository {
    public static UserRepository instance;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    public UserRepository(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://jokeoverflow-3db4b-default-rtdb.europe-west1.firebasedatabase.app");
        firebaseStorage = FirebaseStorage.getInstance();
    }

    public static UserRepository getInstance(){
        if(instance == null){
            instance = new UserRepository();
        }

        return instance;
    }

    public static FirebaseUser getLoggedUser(){
        return getInstance().getCurrentUser();
    }

    private FirebaseUser getCurrentUser(){
        return firebaseAuth.getCurrentUser();
    }

    public FirebaseAuth getFirebaseAuth(){
        return this.firebaseAuth;
    }

    public Task<AuthResult> createUser(String email, String password){
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }

    public UploadTask setUserProfilePicture(String userId, Uri imageUri){
        StorageReference storageReference = firebaseStorage.getReference("images/" + userId + "/picture.jpg");
        return storageReference.putFile(imageUri);
    }

    public Task<Uri> getUserProfilePicture(String userId){
        StorageReference storageReference = firebaseStorage.getReference().child("images/" + userId + "/picture.jpg");
        return storageReference.getDownloadUrl();
    }

    public Task<AuthResult> loginUser(String email, String password){
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }

    public Task<Void> addUserToDatabase(User user){
        return firebaseDatabase.getReference("Users").child(getFirebaseAuth().getCurrentUser().getUid()).setValue(user);
    }

    public DatabaseReference retrieveUserFromDatabase(String userId){
        return firebaseDatabase.getReference("Users").child(userId);
    }

    public DatabaseReference retrieveUsersFromDatabase(){
        return firebaseDatabase.getReference("Users");
    }

    public void signOutUser(){
        firebaseAuth.signOut();
    }

    public void editScore(User user, String userId) {
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users");
        databaseReference.child(userId).child("score").setValue(user.getScore());
    }
}
