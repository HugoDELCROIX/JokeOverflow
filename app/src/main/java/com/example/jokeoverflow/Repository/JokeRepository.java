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

    public void addJokes() {
        jokes.add(new Joke("This is a super title", "03/03/03", "I'am a super super cool joke lol, you can't belive it", (float) 8.2, 1));
        jokes.add(new Joke("This second super title", "04/05/02", "I'am some sort of joke but not really cool you know aha", (float) 4.6, 2));
        jokes.add(new Joke("This not a cool title tho", "06/11/10", "I'am a terrible joke and don't even know how i'am not banned tbh", (float) 1.3, 3));
    }

    public void addJoke(Joke joke){
        jokes.add(joke);
    }
}
