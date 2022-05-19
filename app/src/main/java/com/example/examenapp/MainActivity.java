package com.example.examenapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    FitnessFragment fitnessFragment = new FitnessFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    int backBtnCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Calender");

        backBtnCounter = 0;

        //Exercises.removeAllData(getApplicationContext());

        bottomNavigationView = findViewById(R.id.bottonNav);

        //Sets the container to home fragment which should be default
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        setTitle("Kalender");
                        return true;
                    case R.id.workout:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, fitnessFragment).commit();
                        setTitle("Alla Övningar");
                        return true;
                    case R.id.settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, settingsFragment).commit();
                        setTitle("Alla Övningar");
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(homeFragment.isVisible()) {

            backBtnCounter++;

            if(backBtnCounter == 2) {
                //Exit app
                super.onBackPressed();

            } else {
                Toast.makeText(getApplicationContext(), "Press again to exit app",Toast.LENGTH_SHORT).show();
            }

        } else if(fitnessFragment.isVisible()) {
            //Go back to home fragment
            getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.home);
            backBtnCounter = 0;

            //In settings fragment
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fitnessFragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.workout);
            backBtnCounter = 0;
        }
    }
}