package com.example.jokeoverflow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.jokeoverflow.Adapter.JokeAdapter;
import com.example.jokeoverflow.Model.Joke;
import com.example.jokeoverflow.ViewModel.SearchViewModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;

public class searchFragment extends Fragment {

    private SearchViewModel searchViewModel;

    private Spinner spinner;
    private Button button;

    private RecyclerView recyclerView;
    private JokeAdapter jokeAdapter;

    Query query;

    public searchFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void initWidgets(View view) {
        spinner = view.findViewById(R.id.searchSpinner);
        button = view.findViewById(R.id.searchButton);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        initWidgets(view);

        recyclerView = view.findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.init();

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.spinnerItems, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        query = searchViewModel.retrieveJokes();

        FirebaseRecyclerOptions<Joke> options = new FirebaseRecyclerOptions.Builder<Joke>().setQuery(query, Joke.class).build();
        jokeAdapter = new JokeAdapter(options);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = spinner.getSelectedItem().toString();
                Query newQuery = searchViewModel.retrieveJokesByCategory(category);

                FirebaseRecyclerOptions<Joke> newOptions = new FirebaseRecyclerOptions.Builder<Joke>().setQuery(newQuery, Joke.class).build();
                jokeAdapter.updateOptions(newOptions);
                jokeAdapter.notifyDataSetChanged();
            }
        });

        recyclerView.setAdapter(jokeAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        jokeAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        jokeAdapter.startListening();
    }
}