package com.example.examenapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ExercisesActivity extends AppCompatActivity {

    public static ListView exercisesList;

    private static ArrayList<Exercise> exercises;
    private static ArrayList<String> exercisesArray;

    public static boolean addedExercise = false;

    ImageView imgButton;
    public static ImageView saveButton;

    TextView toolbarText;

    Toolbar toolbar;

    RelativeLayout layout;

    private static ArrayAdapter arrayAdapter;

    public static String date;

    ChooseExerciseFragment chooseExerciseFragment = new ChooseExerciseFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chooseExerciseFragment.isVisible()) {
                    closeChooseFragment();
                }
            }
        });

        toolbarText = findViewById(R.id.toolbarText);

        date = getIntent().getStringExtra("date");
        toolbarText.setText(date + " - Övningar");

        try {
            Exercises.loadData(getApplicationContext(), date);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Couldn't load data", Toast.LENGTH_SHORT).show();
        }

        exercises = Exercises.getExercises();

        layout = findViewById(R.id.exercisesLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If choose fragment is visable
                if(chooseExerciseFragment.isVisible()) {
                    closeChooseFragment();
                }
            }
        });

        saveButton = findViewById(R.id.saveExercisesBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exercises.saveData(getApplicationContext(), date);
                saveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_saved_24));

                if(saveButton.getDrawable() == getResources().getDrawable(R.drawable.ic_baseline_save_24)) {
                    Toast.makeText(getApplicationContext(), "Data saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_saved_24));

        imgButton = findViewById(R.id.addExerciseBtn);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If choose fragment is visable
                if(chooseExerciseFragment.isVisible()) {
                    closeChooseFragment();

                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.chooseContainer, chooseExerciseFragment).commit();
                }
            }
        });

        exercisesList = findViewById(R.id.exercisesList);
        //exercisesList.setBackgroundColor(Color.GRAY);

        //Change this later
        exercisesArray = getExercisesName();

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, exercisesArray);
        exercisesList.setAdapter(arrayAdapter);
        exercisesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Get the clicked exercise
                Exercise clickedExercise = exercises.get(i);

                //If choose fragment is visable
                if(chooseExerciseFragment.isVisible()) {
                    closeChooseFragment();

                } else {
                    if(clickedExercise.sets == null || clickedExercise.sets.size() == 0) {
                        //Start exercise data activity and send the data

                        //Save data
                        Exercises.saveData(getApplicationContext(), date);

                        String exerciseName = adapterView.getItemAtPosition(i).toString();

                        Intent exerciseIntent = new Intent(ExercisesActivity.this, ExerciseDataActivity.class);
                        exerciseIntent.putExtra("exercise_name", exerciseName);
                        exerciseIntent.putExtra("exercise_id", clickedExercise.id);
                        exerciseIntent.putExtra("date", date);
                        startActivity(exerciseIntent);

                    } else {
                        //Start set activity
                        Toast.makeText(getApplicationContext(), "Exercise has " + String.valueOf(clickedExercise.sets.size()),
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ExercisesActivity.this, SetsActivity.class);
                        intent.putExtra("exercise_id", clickedExercise.id);
                        startActivity(intent);
                    }
                }
            }
        });
        exercisesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int exerciseId = i;

                //AlertDialog and able to remove exercise
                AlertDialog.Builder alert = new AlertDialog.Builder(ExercisesActivity.this);
                alert.setTitle("Delete Exercise");
                alert.setMessage("Are you sure you want to delete exercise?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Delete exercises
                        Exercises.removeExercise(getApplicationContext(), exercises.get(exerciseId).id, date);
                        saveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_saved_24));
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

    //Closes the choose fragment and reload the list data
    public void closeChooseFragment() {
        getSupportFragmentManager().beginTransaction().remove(chooseExerciseFragment).commit();

        if(addedExercise) {
            saveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_save_24));
        }
    }

    public static void updateListview() {
        arrayAdapter.clear();
        exercises = Exercises.getExercises();
        exercisesArray = getExercisesName();
        arrayAdapter = new ArrayAdapter(arrayAdapter.getContext(), android.R.layout.simple_list_item_1, exercisesArray);
        exercisesList.setAdapter(arrayAdapter);
    }

    private static ArrayList<String> getExercisesName() {
        ArrayList<String> names = new ArrayList<>();

        if(exercises!=null) {
            for(Exercise exercise : exercises) {
                String name = exercise.name;
                names.add(name);
            }
        }

        return names;
    }
}