package com.example.jokeoverflow.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.jokeoverflow.Model.Joke;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class JokeRepository {
    public static JokeRepository instance;
    ArrayList<Joke> jokes;

    public JokeRepository(){
        this.jokes = new ArrayList<Joke>();
    }

    public static JokeRepository getInstance(){
        if(instance == null){
            instance = new JokeRepository();
        }

        return instance;
    }


    public MutableLiveData<List<Joke>> getAllJokes(){
        MutableLiveData<List<Joke>> liveData = new MutableLiveData<>();
        liveData.setValue(jokes);
        return liveData;
    }

    public void deleteAllJokes(){
        jokes.clear();
    }

    public void addJoke(Joke joke){
        jokes.add(joke);
    }
}
