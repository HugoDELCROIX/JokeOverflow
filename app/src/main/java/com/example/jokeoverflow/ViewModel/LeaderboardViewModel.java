package com.example.jokeoverflow.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.jokeoverflow.Repository.UserRepository;
import com.google.firebase.database.Query;

public class LeaderboardViewModel extends ViewModel {

    UserRepository userRepository;

    public LeaderboardViewModel(){

    }

    public void init(){
        userRepository = UserRepository.getInstance();
    }

    public Query retrieveUsersFromDatabase(){
        return userRepository.retrieveUsersFromDatabase().orderByChild("score");
    }
}
