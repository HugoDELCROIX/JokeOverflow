package com.example.jokeoverflow.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.jokeoverflow.Adapter.JokeAdapter;
import com.example.jokeoverflow.FirebaseQueryLiveData;
import com.example.jokeoverflow.Model.Joke;
import com.example.jokeoverflow.Repository.JokeRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private JokeRepository jokeRepository;
    private LiveData<DataSnapshot> liveData;


    public HomeViewModel(){
    }

    public void init(){
        jokeRepository = JokeRepository.getInstance();
    }

    public LiveData<DataSnapshot> retrieveJokesFromDatabase(){
        liveData = new FirebaseQueryLiveData(jokeRepository.retrieveJokesFromDatabase());
        return liveData;
    }

}
