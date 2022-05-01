package com.example.jokeoverflow;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jokeoverflow.Model.User;
import com.example.jokeoverflow.ViewModel.ProfileViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class userProfileFragment extends Fragment {

    private ImageView profilePicture;
    private TextView username;

    private ProfileViewModel profileViewModel;

    private String userId;

    public userProfileFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userId = requireArguments().getString("userId");
    }

    private void initWidgets(View view){
        profilePicture = view.findViewById(R.id.profilePicture);
        username = view.findViewById(R.id.profileUsername);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        initWidgets(view);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.init();


        getUserInformations();


        return view;
    }

    private void getUserInformations() {
        profileViewModel.retrieveUserFromDatabase(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                profileViewModel.getUserProfilePicture(userId).addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Glide.with(requireActivity()).load(uri).into(profilePicture);
                        username.setText(user.getUsername());

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireActivity(), "Couldn't retrieve requested user", Toast.LENGTH_SHORT).show();
            }
        });
    }
}