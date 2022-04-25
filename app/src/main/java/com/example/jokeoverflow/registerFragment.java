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

import com.example.jokeoverflow.Model.User;
import com.example.jokeoverflow.ViewModel.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class registerFragment extends Fragment {

    private EditText registerEmail;
    private EditText registerPassword;
    private EditText registerUsername;
    private EditText registerFullname;
    private EditText registerAge;
    private TextView registerToLogin;
    private Button registerButton;

    private NavController navController;
    private UserViewModel userViewModel;



    public registerFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initWidgets(View view){
        registerEmail = view.findViewById(R.id.registerEmail);
        registerUsername = view.findViewById(R.id.registerUsername);
        registerFullname = view.findViewById(R.id.registerFullname);
        registerPassword = view.findViewById(R.id.registerPassword);
        registerAge = view.findViewById(R.id.registerAge);
        registerButton = view.findViewById(R.id.registerButton);
        registerToLogin = view.findViewById(R.id.registerToLogin);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.init();


        initWidgets(view);

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();


        // Registering the user
        registerButton.setOnClickListener(v -> {
            registerUser();
        });

        // Redirecting the user to the login fragment
        registerToLogin.setOnClickListener(v -> {
            navController.navigate(R.id.loginFragment);
        });

        return view;
    }

    private void registerUser(){
        String userEmail = registerEmail.getText().toString();
        String userUsername = registerUsername.getText().toString();
        String userFullname = registerFullname.getText().toString();
        String userPassword = registerPassword.getText().toString();
        String userAge = registerAge.getText().toString();

        if(TextUtils.isEmpty(userEmail)){
            registerEmail.setError("Email cannot be empty");
            registerEmail.requestFocus();
        } else if(TextUtils.isEmpty(userPassword)){
            registerPassword.setError("Password cannot be empty");
            registerPassword.requestFocus();
        } else if (TextUtils.isEmpty(userFullname)){
            registerFullname.setError("You must enter a full name");
            registerFullname.requestFocus();
        } else if (TextUtils.isEmpty(userUsername)){
            registerUsername.setError("You must enter valid username");
            registerUsername.requestFocus();
        } else if (TextUtils.isEmpty(userAge)){
            registerAge.setError("You must enter your age");
            registerAge.requestFocus();
        }

        userViewModel.createUser(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    User user = new User(userEmail, userFullname, userUsername, Integer.parseInt(userAge));

                    userViewModel.addUserToDatabase(user).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "User registered", Toast.LENGTH_SHORT).show();
                                navController.navigate(R.id.loginFragment);
                            } else {
                                Toast.makeText(getContext(), "User not added to database", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                } else {
                    Toast.makeText(getContext(), "User not registered :(", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}