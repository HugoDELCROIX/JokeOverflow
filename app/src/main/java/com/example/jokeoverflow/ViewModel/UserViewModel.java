package com.example.jokeoverflow.ViewModel;

import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.jokeoverflow.Model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class UserViewModel extends ViewModel {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    public UserViewModel(){

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://jokeoverflow-3db4b-default-rtdb.europe-west1.firebasedatabase.app");
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

    }

    public void init(){
        if(firebaseAuth == null){
            firebaseAuth = FirebaseAuth.getInstance();
        }

        if(firebaseDatabase == null){
            firebaseDatabase = FirebaseDatabase.getInstance();
        }

        if(firebaseStorage == null){
            firebaseStorage = FirebaseStorage.getInstance();
        }
    }

    public FirebaseAuth getFirebaseAuth(){
        return this.firebaseAuth;
    }

    public FirebaseUser getUser(){
        return this.firebaseUser;
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

    public void signOutUser(){
        firebaseAuth.signOut();
    }

}
