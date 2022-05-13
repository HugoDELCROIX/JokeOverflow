package com.example.jokeoverflow.Repository;

import com.example.jokeoverflow.ApiCall;
import com.example.jokeoverflow.Model.Joke;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JokeRepository {
    public static JokeRepository instance;

    private FirebaseDatabase firebaseDatabase;

    public JokeRepository() {
        firebaseDatabase = FirebaseDatabase.getInstance("https://jokeoverflow-3db4b-default-rtdb.europe-west1.firebasedatabase.app");
    }

    public static JokeRepository getInstance() {
        if (instance == null) {
            instance = new JokeRepository();
        }

        return instance;
    }


    public Task<Void> addJokeToDatabase(Joke joke) {
        DatabaseReference databaseReference = firebaseDatabase.getReference("Jokes").push();
        String keyString = databaseReference.getKey();

        joke.setKey(keyString);

        return databaseReference.setValue(joke);
    }

    public Query retrieveJokesFromDatabase() {
        return firebaseDatabase.getReference("Jokes");
    }

    public com.google.firebase.database.Query getJokesByUser(String userId) {
        DatabaseReference databaseReference = firebaseDatabase.getReference("Jokes");
        return databaseReference.orderByChild("userId").equalTo(userId);
    }

    public Joke rate(Joke joke) {
        DatabaseReference databaseReference = firebaseDatabase.getReference("Jokes");
        databaseReference.child(joke.getKey()).child("rating").setValue(joke.getRating());
        return joke;
    }

    public ApiCall getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://v2.jokeapi.dev/joke/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiCall apiCall = retrofit.create(ApiCall.class);
        return apiCall;
    }
}