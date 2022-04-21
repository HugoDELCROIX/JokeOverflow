package com.example.jokeoverflow.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jokeoverflow.Model.Joke;
import com.example.jokeoverflow.Repository.JokeRepository;

import java.util.List;

public class JokesViewModel extends ViewModel {

    private MutableLiveData<List<Joke>> jokes;
    JokeRepository jokeRepository;

    public void init(){
        if(jokes != null){
            return;
        }

        jokeRepository = JokeRepository.getInstance();
        jokes = jokeRepository.getAllJokes();
    }

    public LiveData<List<Joke>> getJokesList(){
        this.retrieve();
        return jokeRepository.getAllJokes();
    }

    public void deleteAllJokes(){
        jokeRepository.deleteAllJokes();
    }

    public void retrieve(){
        jokeRepository.addJokes();
    }

    public void addJoke(Joke joke){
        jokeRepository.addJoke(joke);
    }
}
