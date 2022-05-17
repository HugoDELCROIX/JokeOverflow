package com.example.jokeoverflow;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jokeoverflow.Model.User;
import com.example.jokeoverflow.Repository.UserRepository;
import com.example.jokeoverflow.ViewModel.ProfileViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.jokeoverflow.Model.ApiJoke;

public class profileFragment extends Fragment {

    private TextView usernameTextView;
    private TextView fullnameTextView;
    private TextView emailTextView;
    private TextView ageTextView;
    private TextView nbJokes;
    private TextView score;
    private ImageView profilePicture;
    private Button logoutButton;

    private Uri imageUri;

    private NavController navController;

    private ProfileViewModel profileViewModel;

    private FirebaseUser currentUser;

    private TextView Result;
    private Button getData;

    private Switch toastMode;
    private SharedPreferences sp;

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
        nbJokes = view.findViewById(R.id.userNbJokes);
        profilePicture = view.findViewById(R.id.userProfilePicture);
        score = view.findViewById(R.id.userScore);
        Result = view.findViewById(R.id.Result);
        getData = view.findViewById(R.id.getData);
        toastMode = view.findViewById(R.id.toastMode);
        sp = requireContext().getSharedPreferences("MyUserPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initWidgets(view);

//        We initialize the toggle button to the shared preference value
        if (sp.getString("toastMode","false").equals("true")) {
            toastMode.setChecked(true);
        } else {
            toastMode.setChecked(false);
        }

        toastMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean switchState = toastMode.isChecked();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("toastMode",switchState.toString());
                editor.commit();
//                Toast.makeText(requireActivity(),sp.getString("toastMode","false"), Toast.LENGTH_LONG).show();
            }
        });

        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiCall apiCall = profileViewModel.getApiCall();
                Call<ApiJoke> call = apiCall.getAllData();

                call.enqueue(new Callback<ApiJoke>() {
                    @Override
                    public void onResponse(Call<ApiJoke> call, Response<ApiJoke> response) {
                        if (response.code() != 200) {
                            Result.setText(getText(R.string.profileApiNoConnection));
                            return;
                        }

                        String answer = "";
                        if (sp.getString("toastMode","false").equals("false")) {
                            answer = response.body().getJoke();
                            Result.setText(answer);
                        } else {
                            Toast.makeText(requireActivity(),response.body().getJoke(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiJoke> call, Throwable t) {
                        Result.setText(t.getMessage());
                    }
                });

            }
        });


        currentUser = UserRepository.getLoggedUser();

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.init();


        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        if(currentUser == null){
            navController.navigate(R.id.loginFragment);
        } else {

            getUserInfo();
            getUserScore();

        }

        profilePicture.setOnClickListener(v -> {

            selectPicture();

        });

        logoutButton.setOnClickListener(v -> {

            profileViewModel.signOutUser();
            navController.navigate(R.id.homeFragment);

        });

        return view;
    }

    private void getUserScore() {
        // Aggregation
    }

    private void getUserInfo() {
        // Display data from database
        LiveData<DataSnapshot> liveData = profileViewModel.retrieveUserFromDatabase(currentUser.getUid());

        liveData.observe(getViewLifecycleOwner(), new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                User userProfile = dataSnapshot.getValue(User.class);
                profileViewModel.getUserProfilePicture(Objects.requireNonNull(currentUser.getUid())).addOnSuccessListener(new OnSuccessListener<Uri>() {
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

                String fullname = userProfile.getFullName();
                String email = userProfile.getEmail();
                String username = userProfile.getUsername();
                int age = userProfile.getAge();

                usernameTextView.setText(username);
                fullnameTextView.setText(fullname);
                emailTextView.setText(email);
                ageTextView.setText(String.valueOf(age));
            }
        });


        // Numbers of joke made the currently connected user
        profileViewModel.getJokesByUser(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nbJokes.setText(snapshot.getChildrenCount() + " " + getText(R.string.profileNbJokes));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireActivity(), getText(R.string.profileErrorNbPosts), Toast.LENGTH_SHORT).show();
            }
        });
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                Intent data = result.getData();
                imageUri = data.getData();
                profileViewModel.setUserProfilePicture(currentUser.getUid(), imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        profilePicture.setImageURI(imageUri);
                        Toast.makeText(requireActivity(), getText(R.string.profileImageUploaded), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    });

    private void selectPicture(){

        /* permissions ? */
        if(ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
                && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

            ArrayList<String> permissionsToRequest = new ArrayList<>();
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            ActivityCompat.requestPermissions(requireActivity(), permissionsToRequest.toArray(new String[0]), 0);

        }

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(intent);
    }

}