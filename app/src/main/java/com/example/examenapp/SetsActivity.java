package com.example.examenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SetsActivity extends AppCompatActivity {

    String exerciseId;

    ArrayList<String> setsArrayList;

    ListView setsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        exerciseId = getIntent().getStringExtra("exercise_id");

        setsList = findViewById(R.id.setsList);

        setsArrayList = getSetsData();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, setsArrayList);
        setsList.setAdapter(arrayAdapter);
    }

    private Exercise getExercise() {
        //Default values
        Exercise exercise = new Exercise("null", "null", new ArrayList<>());

        for(int i = 0; i < Exercises.getExercises().size(); i++)  {
           if(Exercises.getExercises().get(i).id.equals(exerciseId)) {
               exercise = Exercises.getExercises().get(i);
           }
        }
        return exercise;
    }
    private ArrayList<String> getSetsData() {
        ArrayList<MySet> sets = getExercise().sets;
        ArrayList<String> setsData = new ArrayList<>();

        for(MySet set : sets) {
            String nrOfSets = String.valueOf(sets.size());
            String reps = set.reps;
            String weight = String.valueOf(set.weight);
            String restTime = String.valueOf(set.restTime);

            setsData.add("Sets: " + nrOfSets + ", Reps: " + reps + ", Weight: " + weight + " Kg, Rest: " + restTime);
        }

        return setsData;
    }
}