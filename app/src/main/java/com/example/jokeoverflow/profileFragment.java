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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profileFragment extends Fragment {

    private TextView usernameTextView;
    private TextView fullnameTextView;
    private TextView emailTextView;
    private TextView ageTextView;
    private Button logoutButton;

    private UserViewModel userViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static profileFragment newInstance(String param1, String param2) {
        profileFragment fragment = new profileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        userViewModel.init();

        usernameTextView = view.findViewById(R.id.userUsername);
        fullnameTextView = view.findViewById(R.id.userFullname);
        emailTextView = view.findViewById(R.id.userEmail);
        ageTextView = view.findViewById(R.id.userAge);

        logoutButton = view.findViewById(R.id.logoutButton);

        NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment);
        NavController navController = navHostFragment.getNavController();

        if(userViewModel.getFirebaseAuth().getCurrentUser() == null){
            navController.navigate(R.id.loginFragment);
            Toast.makeText(requireActivity(), "no logged", Toast.LENGTH_SHORT).show();
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
            // Redirect here

        });




        return view;
    }
}