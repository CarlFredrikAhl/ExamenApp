package com.example.examenapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;

public class SetsActivity extends AppCompatActivity {

    private static String exerciseId;
    private static String exerciseName;
    String date;

    private static ArrayList<String> setsArrayList;
    private static ArrayAdapter arrayAdapter;

    private static ListView setsList;

    ImageView saveBtn;
    ImageView addBtn;

    boolean addedSet;
    private static boolean markedAsDone;

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

        setsArrayList = getSetsData();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, setsArrayList);
        setsList.setAdapter(arrayAdapter);

        if(!markedAsDone) {
            addBtn.setOnClickListener(view -> addSet());

            if(addedSet) {
                saveBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_save_24));
            }

            saveBtn.setOnClickListener(view -> {
                if(addedSet) {
                    Exercises.saveData(getApplicationContext(), date);
                    saveBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_saved_24));
                    addedSet = false;
                }
            });

            setsList.setOnItemLongClickListener((adapterView, view, i, l) -> {
                int setIndex = i;

                //AlertDialog and able to remove exercise
                AlertDialog.Builder alert = new AlertDialog.Builder(SetsActivity.this);
                alert.setTitle("Delete Set");
                alert.setMessage("Are you sure you want to delete set?");
                alert.setPositiveButton("Yes", (dialogInterface, i1) -> {
                    //Delete set
                    Exercises.removeSet(getApplicationContext(), setIndex, exerciseId, date);

                    saveBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_saved_24));
                });
                alert.setNegativeButton("No", (dialogInterface, i12) -> { });
                alert.create().show();

                return true;
            });
        } else {
            addBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_add_disabled_24));
            setsList.setBackgroundColor(Color.GREEN);
        }
    }

    //Set back button functionality
    @Override
    public void onBackPressed() {
        Intent intent = NavUtils.getParentActivityIntent(this);
        intent.putExtra("date", date);
        startActivity(intent);
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

    //This is used to be called within the class
    private ArrayList<String> getSetsData() {
        ArrayList<MySet> sets = getExercise().sets;
        ArrayList<String> setsData = new ArrayList<>();

        exerciseName = Exercises.getExercise(exerciseId).name;

        for(MySet set : sets) {
            String reps = set.reps;
            String weight = String.valueOf(set.weight);
            String restTime = String.valueOf(set.restTime);

            if(exerciseName.equals("Plank") == false) {
                setsData.add("Reps: " + reps + ", Weight: " + weight + " Kg, Rest: " + restTime + " Sec");

            } else {
                setsData.add("Time: " + reps + ", Weight: " + weight + " Kg, Rest: " + restTime + " Sec");
            }
        }

        return setsData;
    }

    //This is used to be called from other classes
    private static ArrayList<String> getSetsDataStatic() {
        ArrayList<MySet> sets = Exercises.getExercise(exerciseId).sets;
        ArrayList<String> setsData = new ArrayList<>();

        exerciseName = Exercises.getExercise(exerciseId).name;

        for(MySet set : sets) {
            String reps = set.reps;
            String weight = String.valueOf(set.weight);
            String restTime = String.valueOf(set.restTime);

            if(exerciseName.equals("Plank") == false) {
                setsData.add("Reps: " + reps + ", Weight: " + weight + " Kg, Rest: " + restTime + " Sec");

            } else {
                setsData.add("Time: " + reps + ", Weight: " + weight + " Kg, Rest: " + restTime + " Sec");
            }
        }

        return setsData;
    }

    private void addSet() {
        Intent intent = new Intent(SetsActivity.this, ExerciseDataActivity.class);
        intent.putExtra("exercise_id", exerciseId);
        intent.putExtra("exercise_name", exerciseName);
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