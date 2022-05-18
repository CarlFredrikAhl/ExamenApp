package com.example.examenapp;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class SetsActivity extends AppCompatActivity {

    private static String exerciseId;
    String date;

    private static ArrayList<String> setsArrayList;

    private static ListView setsList;

    ImageView saveBtn;
    ImageView addBtn;

    boolean addedSet;
    private static boolean markedAsDone;

    private static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);

        addedSet = getIntent().getBooleanExtra("added_set", false);
        date = getIntent().getStringExtra("date");
        exerciseId = getIntent().getStringExtra("exercise_id");
        markedAsDone = getIntent().getBooleanExtra("markedAsDone", false);

        saveBtn = findViewById(R.id.saveSetsBtn);
        setsList = findViewById(R.id.setsList);
        addBtn = findViewById(R.id.addSetBtn);
        if(!markedAsDone) {
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
                    Toast.makeText(getApplicationContext(), "Pressed save", Toast.LENGTH_SHORT).show();

                    if(addedSet) {
                        Exercises.saveData(getApplicationContext(), date);
                        saveBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_saved_24));
                        addedSet = false;
                    }
                }
            });

        } else {
            addBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_add_disabled_24));
            setsList.setBackgroundColor(Color.GREEN);
        }
        setsArrayList = getSetsData();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, setsArrayList);
        setsList.setAdapter(arrayAdapter);
        if(!markedAsDone) {
            setsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int setIndex = i;

                    //AlertDialog and able to remove exercise
                    AlertDialog.Builder alert = new AlertDialog.Builder(SetsActivity.this);
                    alert.setTitle("Delete Set");
                    alert.setMessage("Are you sure you want to delete set?");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Delete set
                            Exercises.removeSet(getApplicationContext(), setIndex, exerciseId, date);

                            saveBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_saved_24));
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alert.create().show();
                    return true;
                }
            });
        }
    }

    //Set back button functionality

    @Override
    public void onBackPressed() {
        Intent backIntent = NavUtils.getParentActivityIntent(this);
        startActivity(backIntent);
    }

    private Exercise getExercise() {
        //Default values
        Exercise exercise = new Exercise("null", "null", new ArrayList<>());

        if(!addedSet) {
            Exercises.loadData(getApplicationContext(), date);
        }

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

            setsData.add("Reps: " + reps + ", Weight: " + weight + " Kg, Rest: " + restTime + " Sec");
        }

        /*
        HashSet uniqueSets = new HashSet();
        uniqueSets.addAll(setsData);
        setsData.clear();
        setsData.addAll(uniqueSets);
         */

        return setsData;
    }

    private static ArrayList<String> getSetsDataStatic() {
        ArrayList<MySet> sets = Exercises.getExercise(exerciseId).sets;
        ArrayList<String> setsData = new ArrayList<>();

        for(MySet set : sets) {
            String nrOfSets = String.valueOf(sets.size());
            String reps = set.reps;
            String weight = String.valueOf(set.weight);
            String restTime = String.valueOf(set.restTime);

            setsData.add("Reps: " + reps + ", Weight: " + weight + " Kg, Rest: " + restTime + " Sec");
        }

        /*
        HashSet uniqueSets = new HashSet();
        uniqueSets.addAll(setsData);
        setsData.clear();
        setsData.addAll(uniqueSets);
         */

        return setsData;
    }

    private void addSet() {
        Intent intent = new Intent(SetsActivity.this, ExerciseDataActivity.class);
        intent.putExtra("exercise_id", exerciseId);
        intent.putExtra("flag", "fromSetsActivity");
        intent.putExtra("date", date);
        startActivity(intent);
    }

    public static void updateListView() {
        arrayAdapter.clear();
        setsArrayList = getSetsDataStatic();
        arrayAdapter = new ArrayAdapter(arrayAdapter.getContext(), android.R.layout.simple_list_item_1, setsArrayList);
        setsList.setAdapter(arrayAdapter);
    }
}