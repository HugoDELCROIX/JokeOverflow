package com.example.jokeoverflow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jokeoverflow.Adapter.JokeAdapter;
import com.example.jokeoverflow.Model.Joke;
import com.example.jokeoverflow.ViewModel.BestJokeViewModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;


public class BestjokesFragment extends Fragment {

    private RecyclerView recyclerView;
    private JokeAdapter jokeAdapter;

    private BestJokeViewModel bestJokeViewModel;


    public BestjokesFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bestjokes, container, false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());

        recyclerView = view.findViewById(R.id.bestJokesRecyclerView);

        bestJokeViewModel = new ViewModelProvider(this).get(BestJokeViewModel.class);
        bestJokeViewModel.init();


        Query query = bestJokeViewModel.retrieveJokesFromDatabase();
        FirebaseRecyclerOptions<Joke> options = new FirebaseRecyclerOptions.Builder<Joke>().setQuery(query, Joke.class).build();

        jokeAdapter = new JokeAdapter(options);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(jokeAdapter);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        jokeAdapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        jokeAdapter.startListening();
    }
}