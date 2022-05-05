package com.example.jokeoverflow.ViewModel;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.example.jokeoverflow.Model.User;
import com.example.jokeoverflow.Repository.JokeRepository;
import com.example.jokeoverflow.Repository.UserRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.UploadTask;

public class ProfileViewModel extends ViewModel {

    private UserRepository userRepository;
    private JokeRepository jokeRepository;

    public ProfileViewModel(){

    }

    public void init(){
        userRepository = UserRepository.getInstance();
        jokeRepository = JokeRepository.getInstance();
    }

    public FirebaseAuth getFirebaseAuth(){
        return userRepository.getFirebaseAuth();
    }

    public void signOutUser(){
        userRepository.signOutUser();
    }

    public DatabaseReference retrieveUserFromDatabase(String userId){
        return userRepository.retrieveUserFromDatabase(userId);
    }

    public Task<Uri> getUserProfilePicture(String userId){
        return userRepository.getUserProfilePicture(userId);
    }

    public UploadTask setUserProfilePicture(String userId, Uri imageUri){
        return userRepository.setUserProfilePicture(userId, imageUri);
    }

    public com.google.firebase.database.Query getJokesByUser(String userId){
        return jokeRepository.getJokesByUser(userId);
    }

}
