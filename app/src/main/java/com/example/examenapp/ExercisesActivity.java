package com.example.examenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
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

public class ExercisesActivity extends AppCompatActivity {

    ListView exercisesList;

    ImageView imgButton;

    TextView toolbarText;

    Toolbar toolbar;

    RelativeLayout layout;

    private static ArrayAdapter arrayAdapter;

    ChooseExerciseFragment chooseExerciseFragment = new ChooseExerciseFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

        String date = getIntent().getStringExtra("date");
        toolbarText.setText(date + " - Valda Övningar");


        exercisesList = findViewById(R.id.exercisesList);
        exercisesList.setBackgroundColor(Color.RED);

        ArrayList<String> exercisesArray = Exercises.getExercises();

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
        arrayAdapter.notifyDataSetChanged();
    }
}