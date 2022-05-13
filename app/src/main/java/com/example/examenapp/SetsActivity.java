package com.example.examenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class SetsActivity extends AppCompatActivity {

    String exerciseId;
    String date;

    ArrayList<String> setsArrayList;

    ListView setsList;

    ImageView saveBtn;
    ImageView addBtn;

    boolean addedSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        addedSet = getIntent().getBooleanExtra("added_set", false);

        saveBtn = findViewById(R.id.saveSetsBtn);
        addBtn = findViewById(R.id.addSetBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSet();
            }
        });

        if(addedSet) {
            saveBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_save_24));
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exercises.saveData(getApplicationContext(), date);
                if(saveBtn.getDrawable() == getResources().getDrawable(R.drawable.ic_baseline_save_24)) {
                    saveBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_saved_24));
                }
            }
        });

        exerciseId = getIntent().getStringExtra("exercise_id");

        setsList = findViewById(R.id.setsList);

        setsArrayList = getSetsData();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, setsArrayList);
        setsList.setAdapter(arrayAdapter);
    }

    private Exercise getExercise() {
        //Default values
        Exercise exercise = new Exercise("null", "null", new ArrayList<>());
        date = exercise.date;

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

            setsData.add("Sets: " + nrOfSets + ", Reps: " + reps + ", Weight: " + weight + " Kg, Rest: " + restTime + " Sec");
        }

        HashSet uniqueSets = new HashSet();
        uniqueSets.addAll(setsData);
        setsData.clear();
        setsData.addAll(uniqueSets);

        return setsData;
    }

    private void addSet() {
        Intent intent = new Intent(SetsActivity.this, ExerciseDataActivity.class);
        intent.putExtra("exercise_id", exerciseId);
        intent.putExtra("flag", "fromSetsActivity");
        startActivity(intent);
    }
}