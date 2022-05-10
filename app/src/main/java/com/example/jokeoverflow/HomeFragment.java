package com.example.jokeoverflow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jokeoverflow.Adapter.JokeAdapter;
import com.example.jokeoverflow.Model.Joke;
import com.example.jokeoverflow.ViewModel.HomeViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private JokeAdapter jokeAdapter;

    private ArrayList<Joke> jokes;

    private HomeViewModel homeViewModel;

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

        homeViewModel= new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.init();

        LiveData<DataSnapshot> liveData = homeViewModel.retrieveJokesFromDatabase();
        jokes = new ArrayList<>();

        liveData.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for(DataSnapshot jokesnap : dataSnapshot.getChildren()){
                    Joke joke = jokesnap.getValue(Joke.class);
                    jokes.add(joke);
                }

                jokeAdapter = new JokeAdapter(jokes);
                recyclerView.setAdapter(jokeAdapter);
            }

        });

        return view;
    }
}