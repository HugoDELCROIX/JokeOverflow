package com.example.jokeoverflow.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.jokeoverflow.Model.User;
import com.example.jokeoverflow.Repository.UserRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class AuthViewModel extends ViewModel {

    private UserRepository userRepository;

    public AuthViewModel(){

    }

    public void init(){
        userRepository = UserRepository.getInstance();
    }

    public Task<AuthResult> loginUser(String email, String password){
        return userRepository.loginUser(email, password);
    }

    public Task<AuthResult> createUser(String email, String password){
        return userRepository.createUser(email, password);
    }

    public Task<Void> addUserToDatabase(User user){
        return userRepository.addUserToDatabase(user);
    }

}
