package com.example.jokeoverflow.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.jokeoverflow.Model.Joke;
import com.example.jokeoverflow.Repository.JokeRepository;
import com.google.android.gms.tasks.Task;

public class AddjokeViewModel extends ViewModel {

    private JokeRepository jokeRepository;

    public AddjokeViewModel(){

    }

    public void init(){
        jokeRepository = JokeRepository.getInstance();
    }

    public Task<Void> addJokeToDatabase(Joke joke){
        return jokeRepository.addJokeToDatabase(joke);
    }

}
