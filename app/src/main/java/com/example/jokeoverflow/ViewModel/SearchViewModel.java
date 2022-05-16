package com.example.jokeoverflow.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.jokeoverflow.Repository.JokeRepository;
import com.google.firebase.database.Query;

public class SearchViewModel extends ViewModel {

    private JokeRepository jokeRepository;

    public SearchViewModel(){

    }

    public void init(){
        jokeRepository = JokeRepository.getInstance();
    }

    public Query retrieveJokesByCategory(String category){
        return jokeRepository.retrieveJokesFromDatabase().orderByChild("category").equalTo(category);
    }

    public Query retrieveJokes(){
        return jokeRepository.retrieveJokesFromDatabase();
    }

}
