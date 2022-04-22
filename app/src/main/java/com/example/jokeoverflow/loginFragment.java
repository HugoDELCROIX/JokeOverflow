package com.example.jokeoverflow;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jokeoverflow.ViewModel.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class loginFragment extends Fragment {

    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginBtn;
    private TextView loginToRegister;

    private UserViewModel userViewModel;

    private NavController navController;

    public loginFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initWidgets(View view){
        loginEmail = view.findViewById(R.id.loginEmail);
        loginPassword = view.findViewById(R.id.loginPassword);
        loginBtn = view.findViewById(R.id.loginButton);
        loginToRegister = view.findViewById(R.id.loginToRegister);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.init();

        initWidgets(view);

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        // Login the user
        loginBtn.setOnClickListener(v -> {
            loginUser();
        });


        // Redirect user to register fragment
        loginToRegister.setOnClickListener(v -> {
            navController.navigate(R.id.registerFragment);
        });

        return view;
    }

    private void loginUser(){
        String userEmail = loginEmail.getText().toString();
        String userPassword = loginPassword.getText().toString();

        if(TextUtils.isEmpty(userEmail)){
            loginEmail.setError("Email cannot be empty");
            loginEmail.requestFocus();
        } else if(TextUtils.isEmpty(userPassword)){
            loginPassword.setError("Password cannot be empty");
            loginPassword.requestFocus();
        }

        userViewModel.loginUser(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(), "User logged successfully", Toast.LENGTH_SHORT).show();
                    navController.navigate(R.id.homeFragment);
                } else {
                    Toast.makeText(getContext(), "User not logged", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}