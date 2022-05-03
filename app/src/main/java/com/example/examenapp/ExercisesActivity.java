package com.example.examenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ExercisesActivity extends AppCompatActivity {

    ListView exercisesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        exercisesList = findViewById(R.id.exercisesList);
        exercisesList.setBackgroundColor(Color.RED);

        ArrayList<String> exercisesArray = new ArrayList<>();
        exercisesArray.add("Bänkpress");
        exercisesArray.add("Militärpress");
        exercisesArray.add("Lateral Raises");
        exercisesArray.add("Bicep Curls");
        exercisesArray.add("Benböj");
        exercisesArray.add("Pull Ups");
        exercisesArray.add("Chins");
        exercisesArray.add("Farmers Walk");
        exercisesArray.add("Dips");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, exercisesArray);
        exercisesList.setAdapter(arrayAdapter);
    }
}