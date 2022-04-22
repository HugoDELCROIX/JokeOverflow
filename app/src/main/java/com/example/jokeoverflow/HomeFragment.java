package com.example.jokeoverflow;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jokeoverflow.Adapter.JokeAdapter;
import com.example.jokeoverflow.Model.Joke;
import com.example.jokeoverflow.ViewModel.JokesViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    JokeAdapter jokeAdapter;

    ArrayList<Joke> jokes;
    JokesViewModel jokesViewModel;

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


        jokeAdapter = new JokeAdapter(jokes);
        jokesViewModel.getJokesList().observe(getViewLifecycleOwner(), new Observer<List<Joke>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Joke> jokes) {
                jokeAdapter.setJokes((ArrayList<Joke>) jokes);
                jokeAdapter.notifyDataSetChanged();
            }
        });

        recyclerView.setAdapter(jokeAdapter);

        return view;
    }
}