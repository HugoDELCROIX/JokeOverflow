package com.example.jokeoverflow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jokeoverflow.Model.User;
import com.example.jokeoverflow.ViewModel.UserViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Objects;

public class profileFragment extends Fragment {

    private TextView usernameTextView;
    private TextView fullnameTextView;
    private TextView emailTextView;
    private TextView ageTextView;
    private Button logoutButton;

    private NavController navController;
    private UserViewModel userViewModel;

    public profileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initWidgets(View view){
        usernameTextView = view.findViewById(R.id.userUsername);
        fullnameTextView = view.findViewById(R.id.userFullname);
        emailTextView = view.findViewById(R.id.userEmail);
        ageTextView = view.findViewById(R.id.userAge);
        logoutButton = view.findViewById(R.id.logoutButton);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.init();

        initWidgets(view);

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        // Redirect user if not logged.
        if(userViewModel.getFirebaseAuth().getCurrentUser() == null){
            navController.navigate(R.id.loginFragment);
        }

        // Display data from database
        userViewModel.retrieveUserFromDatabase().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String fullname = userProfile.getFullName();
                    String email = userProfile.getEmail();
                    String username = userProfile.getUsername();
                    int age = userProfile.getAge();

                    usernameTextView.setText(username);
                    fullnameTextView.setText(fullname);
                    emailTextView.setText(email);
                    ageTextView.setText(String.valueOf(age));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });


        logoutButton.setOnClickListener(v -> {

            userViewModel.signOutUser();
            navController.navigate(R.id.homeFragment);

        });

        return view;
    }
}