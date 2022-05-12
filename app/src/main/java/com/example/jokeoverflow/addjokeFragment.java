package com.example.jokeoverflow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jokeoverflow.Model.Joke;
import com.example.jokeoverflow.Model.User;
import com.example.jokeoverflow.Repository.UserRepository;
import com.example.jokeoverflow.ViewModel.AddjokeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class addjokeFragment extends Fragment {

    private Button addJokeBtn;
    private EditText titleEditText;
    private EditText jokeEditText;

    private AddjokeViewModel addjokeViewModel;

    private FirebaseUser currentUser;


    public addjokeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initWidgets(View view){
        addJokeBtn = view.findViewById(R.id.addJokeBtn);
        titleEditText = view.findViewById(R.id.jokeTitleEditText);
        jokeEditText = view.findViewById(R.id.jokeContentEditText);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_addjoke, container, false);
        initWidgets(view);

        currentUser = UserRepository.getLoggedUser();

        addjokeViewModel = new ViewModelProvider(this).get(AddjokeViewModel.class);
        addjokeViewModel.init();

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();


        if(currentUser == null){
            navController.navigate(R.id.loginFragment);
        }

        addJokeBtn.setOnClickListener(v -> {

            String jokeTitle = titleEditText.getText().toString();
            String jokeContent = jokeEditText.getText().toString();


            Joke joke = new Joke(jokeTitle, System.currentTimeMillis(), jokeContent, 5.0, currentUser.getUid());

            addjokeViewModel.addJokeToDatabase(joke).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(view.getContext(), "Joke added", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.homeFragment);
                    } else {
                        Toast.makeText(view.getContext(), "Joke couldn't be added", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            LiveData<DataSnapshot> liveData = addjokeViewModel.retrieveUserFromDatabase(currentUser.getUid());

            liveData.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
                @Override
                public void onChanged(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    user.upScore(1);
                    addjokeViewModel.addScoreToUser(user, currentUser.getUid());
                }
            });

        });

        return view;
    }
}