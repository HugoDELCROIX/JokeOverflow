package com.example.jokeoverflow.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.jokeoverflow.FirebaseQueryLiveData;
import com.example.jokeoverflow.Model.Joke;
import com.example.jokeoverflow.Model.User;
import com.example.jokeoverflow.Repository.JokeRepository;
import com.example.jokeoverflow.Repository.UserRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

public class AddjokeViewModel extends ViewModel {

    private JokeRepository jokeRepository;
    private UserRepository userRepository;
    private LiveData<DataSnapshot> liveData;

    public AddjokeViewModel(){

    }

    public void init(){
        jokeRepository = JokeRepository.getInstance();
        userRepository = UserRepository.getInstance();
    }

    public Task<Void> addJokeToDatabase(Joke joke){
        return jokeRepository.addJokeToDatabase(joke);
    }

    public LiveData<DataSnapshot> retrieveUserFromDatabase(String userId){
        liveData = new FirebaseQueryLiveData(userRepository.retrieveUserFromDatabase(userId));
        return liveData;
    }

    public void addScoreToUser(User user, String userId){
        userRepository.editScore(user, userId);
    }

}
