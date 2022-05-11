package com.example.examenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ExercisesActivity extends AppCompatActivity {

    ListView exercisesList;

    ImageView imgButton;

    RelativeLayout layout;

    ChooseExerciseFragment chooseExerciseFragment = new ChooseExerciseFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        layout = findViewById(R.id.exercisesLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If choose fragment is visable
                if(chooseExerciseFragment.isVisible()) {
                    getSupportFragmentManager().beginTransaction().remove(chooseExerciseFragment).commit();
                }
                Toast.makeText(getApplicationContext(), "Clicked outside fragment", Toast.LENGTH_SHORT).show();
            }
        });

        imgButton = findViewById(R.id.addExerciseBtn);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If choose fragment is visable
                if(chooseExerciseFragment.isVisible()) {
                    getSupportFragmentManager().beginTransaction().remove(chooseExerciseFragment).commit();

                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.chooseContainer, chooseExerciseFragment).commit();
                }
            }
        });

        String date = getIntent().getStringExtra("date");
        setTitle(date + " - Valda Övningar");

        exercisesList = findViewById(R.id.exercisesList);
        exercisesList.setBackgroundColor(Color.RED);

        ArrayList<String> exercisesArray = Exercises.getExercises();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, exercisesArray);
        exercisesList.setAdapter(arrayAdapter);
        exercisesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //If choose fragment is visable
                if(chooseExerciseFragment.isVisible()) {
                    getSupportFragmentManager().beginTransaction().remove(chooseExerciseFragment).commit();
                    arrayAdapter.notifyDataSetChanged();

                } else {
                    //Start exercise activity and send the data

                    String exerciseName = adapterView.getItemAtPosition(i).toString();

                    Intent exerciseIntent = new Intent(ExercisesActivity.this, ExerciseActivity.class);
                    exerciseIntent.putExtra("exercise_name", exerciseName);
                    startActivity(exerciseIntent);
                }
            }
        });
    }
}