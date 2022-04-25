package com.example.jokeoverflow.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jokeoverflow.Model.Joke;
import com.example.jokeoverflow.Repository.JokeRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class JokesViewModel extends ViewModel {

    private MutableLiveData<List<Joke>> jokes;
    JokeRepository jokeRepository;

    private FirebaseDatabase firebaseDatabase;

    public void init(){
        if(jokes != null){
            return;
        }

        jokeRepository = JokeRepository.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance("https://jokeoverflow-3db4b-default-rtdb.europe-west1.firebasedatabase.app");
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
        //jokeRepository.addJokes();
    }

    public void addJoke(Joke joke){
        jokeRepository.addJoke(joke);
    }

    public Task<Void> addJokeToDatabase(Joke joke){
        DatabaseReference databaseReference = firebaseDatabase.getReference("Jokes").push();
        String keyString = databaseReference.getKey();

        joke.setKey(keyString);

        return databaseReference.setValue(joke);
    }

    public DatabaseReference retrieveJokesFromDatabase(){
        return firebaseDatabase.getReference("Jokes");
    }

    public com.google.firebase.database.Query nbJokesByUser(String userId){
        DatabaseReference databaseReference = firebaseDatabase.getReference("Jokes");
        return databaseReference.orderByChild("userId").equalTo(userId);
    }

    public Joke rate(Joke joke){
        DatabaseReference databaseReference = firebaseDatabase.getReference("Jokes");
        databaseReference.child(joke.getKey()).child("rating").setValue(joke.getRating());
        return joke;
    }

}
