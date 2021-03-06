package com.example.jokeoverflow;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jokeoverflow.Model.User;
import com.example.jokeoverflow.ViewModel.ProfileViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;

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


        getUserInformation();


        return view;
    }

    private void getUserInformation() {
        LiveData<DataSnapshot> liveData = profileViewModel.retrieveUserFromDatabase(userId);

        liveData.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                profileViewModel.getUserProfilePicture(userId).addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(requireActivity()).load(uri).into(profilePicture);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Glide.with(requireActivity()).load(R.drawable.resource_default).into(profilePicture);
                    }
                });

                username.setText(user.getUsername());
            }
        });
    }
}