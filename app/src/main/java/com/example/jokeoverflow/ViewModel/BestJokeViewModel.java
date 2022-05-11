package com.example.jokeoverflow.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.jokeoverflow.Repository.JokeRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

public class BestJokeViewModel extends ViewModel {

    private JokeRepository jokeRepository;
    private LiveData<DataSnapshot> liveData;

    public BestJokeViewModel(){

    }

    public void init(){
        jokeRepository = JokeRepository.getInstance();
    }

    public Query retrieveJokesFromDatabase(){
        return jokeRepository.retrieveJokesFromDatabase().orderByChild("rating");
    }
}
