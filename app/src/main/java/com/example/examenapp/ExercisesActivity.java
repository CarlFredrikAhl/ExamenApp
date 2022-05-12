package com.example.examenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExercisesActivity extends AppCompatActivity {

    private static ListView exercisesList;

    private static ArrayList<Exercise> exercises;
    private static ArrayList<String> exercisesArray;

    ImageView imgButton;

    TextView toolbarText;

    Toolbar toolbar;

    RelativeLayout layout;

    SharedPreferences sharedPreferences;

    private static ArrayAdapter arrayAdapter;

    public static String date;

    ChooseExerciseFragment chooseExerciseFragment = new ChooseExerciseFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        exercises = Exercises.getExercises();

        //How we save the data (locally)
        sharedPreferences = getSharedPreferences("ExercisesData", Context.MODE_PRIVATE);

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

        date = getIntent().getStringExtra("date");
        toolbarText.setText(date + " - Valda Övningar");


        exercisesList = findViewById(R.id.exercisesList);
        exercisesList.setBackgroundColor(Color.RED);

        //Change this later
        exercisesArray = getExercisesName();

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, exercisesArray);
        exercisesList.setAdapter(arrayAdapter);
        exercisesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //If choose fragment is visable
                if(chooseExerciseFragment.isVisible()) {
                    closeChooseFragment();

                } else {
                    //Start exercise activity and send the data

                    String exerciseName = adapterView.getItemAtPosition(i).toString();

                    Intent exerciseIntent = new Intent(ExercisesActivity.this, ExerciseDataActivity.class);
                    exerciseIntent.putExtra("exercise_name", exerciseName);
                    startActivity(exerciseIntent);
                }
            }
        });
    }

    //Closes the choose fragment and reload the list data
    public void closeChooseFragment() {
        getSupportFragmentManager().beginTransaction().remove(chooseExerciseFragment).commit();
        //arrayAdapter.notifyDataSetChanged();
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

    //Saving the data
    public void saveData() {
        SharedPreferences.Editor saveEditor = sharedPreferences.edit();

    }

    public void getData() {

    }
}