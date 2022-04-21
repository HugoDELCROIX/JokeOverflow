package com.example.jokeoverflow.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.jokeoverflow.Model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class UserViewModel extends ViewModel {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;

    public UserViewModel(){

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://jokeoverflow-3db4b-default-rtdb.europe-west1.firebasedatabase.app");
        firebaseUser = firebaseAuth.getCurrentUser();

    }

    public void init(){
        if(firebaseAuth == null){
            firebaseAuth = FirebaseAuth.getInstance();
        }

        if(firebaseDatabase == null){
            firebaseDatabase = FirebaseDatabase.getInstance();
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

    public Task<AuthResult> loginUser(String email, String password){
        return firebaseAuth.signInWithEmailAndPassword(email, password);
    }

    public Task<Void> addUserToDatabase(User user){
        return firebaseDatabase.getReference("Users").child(getFirebaseAuth().getCurrentUser().getUid()).setValue(user);
    }

    public void signOutUser(){
        firebaseAuth.signOut();
    }

}
