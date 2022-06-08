package com.example.examenapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    FitnessFragment fitnessFragment = new FitnessFragment();

    int backBtnCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Calender");

        backBtnCounter = 0;

        //These booleans are used in the expandable list-adapter when and exercise is clicked
        //This lets this activity know if it should be redirected to one of these fragments
        boolean goToFitness = getIntent().getBooleanExtra("go_to_fitness", false);
        boolean goToSettings = getIntent().getBooleanExtra("go_to_settings", false);

        bottomNavigationView = findViewById(R.id.bottonNav);

        //Sets the container to home fragment which should be default
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        if(goToFitness) {
            goToFitnessFragment();

        } else if(goToSettings) {
            goToSettingsFragment();
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    return true;
                case R.id.workout:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fitnessFragment).commit();
                    return true;
            }

            return false;
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
            getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
            bottomNavigationView.setSelectedItemId(R.id.home);
            backBtnCounter = 0;
        }
    }

    public void goToFitnessFragment() {
        FitnessFragment fitnessFragment = new FitnessFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fitnessFragment).commit();
        bottomNavigationView.setSelectedItemId(R.id.workout);
    }

    public void goToSettingsFragment() {
        SettingsFragment settingsFragment = new SettingsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, settingsFragment).commit();
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}