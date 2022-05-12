package com.example.jokeoverflow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jokeoverflow.Adapter.UserAdapter;
import com.example.jokeoverflow.Model.User;
import com.example.jokeoverflow.ViewModel.LeaderboardViewModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;


public class LeaderboardFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    private LeaderboardViewModel leaderboardViewModel;

    public LeaderboardFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        recyclerView = view.findViewById(R.id.ladderRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        leaderboardViewModel = new ViewModelProvider(this).get(LeaderboardViewModel.class);
        leaderboardViewModel.init();

        Query query = leaderboardViewModel.retrieveUsersFromDatabase();
        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>().setQuery(query, User.class).build();

        userAdapter = new UserAdapter(options);
        recyclerView.setAdapter(userAdapter);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        userAdapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        userAdapter.startListening();
    }
}