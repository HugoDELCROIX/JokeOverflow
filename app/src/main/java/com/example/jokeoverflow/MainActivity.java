package com.example.jokeoverflow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceManager;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton floatingActionButton;
    private NavController navController;
    private NavHostFragment navHostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        navController = getNavController();

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.homeFragment){
                    navController.navigate(R.id.homeFragment);
                } else if(item.getItemId() == R.id.leaderboardFragment){
                    navController.navigate(R.id.leaderboardFragment);
                } else if(item.getItemId() == R.id.bestjokesFragment){
                    navController.navigate(R.id.bestjokesFragment);
                } else if(item.getItemId() == R.id.searchFragment){
                    navController.navigate(R.id.searchFragment);
                }

                return true;
            }
        });

        floatingActionButton = findViewById(R.id.addJokeFab);
        floatingActionButton.setOnClickListener(v -> {
            navController.navigate(R.id.addjokeFragment);
        });

    }

    public NavController getNavController(){
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        return navHostFragment.getNavController();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.mybutton){
            getNavController().navigate(R.id.profileFragment);
        }

        return super.onOptionsItemSelected(item);
    }
}