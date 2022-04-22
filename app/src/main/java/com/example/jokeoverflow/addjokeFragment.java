package com.example.jokeoverflow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jokeoverflow.Model.Joke;
import com.example.jokeoverflow.ViewModel.JokesViewModel;
import com.example.jokeoverflow.ViewModel.UserViewModel;

public class addjokeFragment extends Fragment {

    private Button addJokeBtn;
    private EditText titleEditText;
    private EditText jokeEditText;

    private JokesViewModel jokesViewModel;

    private UserViewModel userViewModel;


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
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.init();

        initWidgets(view);

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        if(userViewModel.getFirebaseAuth().getCurrentUser() == null){
            navController.navigate(R.id.loginFragment);
        }

        addJokeBtn.setOnClickListener(v -> {

            String jokeTitle = titleEditText.getText().toString();
            String jokeContent = jokeEditText.getText().toString();

            jokesViewModel = new ViewModelProvider(this).get(JokesViewModel.class);
            jokesViewModel.init();

            jokesViewModel.addJoke(new Joke("title", "19/04/22", "jokeContent", (float) 5.0, 1));
            Toast.makeText(view.getContext(), "Joke added", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}