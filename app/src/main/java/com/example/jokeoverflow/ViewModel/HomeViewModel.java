package com.example.jokeoverflow.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.jokeoverflow.Repository.JokeRepository;

import com.google.firebase.database.Query;

public class HomeViewModel extends ViewModel {

    private JokeRepository jokeRepository;

    public HomeViewModel(){
    }

    public void init(){
        jokeRepository = JokeRepository.getInstance();
    }

    public Query retrieveJokesFromDatabase(){
        return jokeRepository.retrieveJokesFromDatabase().orderByChild("date");
    }

}
