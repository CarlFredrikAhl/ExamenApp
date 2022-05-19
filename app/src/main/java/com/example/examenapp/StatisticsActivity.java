package com.example.examenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class StatisticsActivity extends AppCompatActivity {

    String exerciseName;

    TextView toolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        exerciseName = getIntent().getStringExtra("exercise_name");

        toolbarText = findViewById(R.id.toolbarText);
        toolbarText.setText("Statistics - " + exerciseName);
    }
}