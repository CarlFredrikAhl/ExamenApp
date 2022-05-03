package com.example.examenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ExerciseActivity extends AppCompatActivity {

    TextView exerciseTextVeiw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        exerciseTextVeiw = findViewById(R.id.exerciseTextView);
        String exerciseName = getIntent().getStringExtra("exercise_name");
        exerciseTextVeiw.setText(exerciseName);

        setTitle(exerciseName);
    }
}