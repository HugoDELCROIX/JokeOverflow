package com.example.jokeoverflow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jokeoverflow.Adapter.JokeAdapter;
import com.example.jokeoverflow.Model.Joke;
import com.example.jokeoverflow.ViewModel.JokesViewModel;
import com.example.jokeoverflow.ViewModel.UserViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    JokeAdapter jokeAdapter;

    ArrayList<Joke> jokes;
    JokesViewModel jokesViewModel;
    UserViewModel userViewModel;

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.homeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        jokesViewModel = new ViewModelProvider(this).get(JokesViewModel.class);
        jokesViewModel.init();

        jokesViewModel.retrieveJokesFromDatabase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jokes = new ArrayList<>();
                for(DataSnapshot jokesnap : snapshot.getChildren()){
                    Joke joke = jokesnap.getValue(Joke.class);
                    jokes.add(joke);
                }

                jokeAdapter = new JokeAdapter(jokes);
                recyclerView.setAdapter(jokeAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}