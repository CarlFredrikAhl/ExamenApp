package com.example.examenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        String date = getIntent().getStringExtra("date");
        setTitle(date + " - Valda Övningar");

        exercisesList = findViewById(R.id.exercisesList);
        exercisesList.setBackgroundColor(Color.RED);

        ArrayList<String> exercisesArray = new ArrayList<>();
        exercisesArray.add("Bänkpress");
        exercisesArray.add("Armhävningar");
        exercisesArray.add("Dips");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, exercisesArray);
        exercisesList.setAdapter(arrayAdapter);
        exercisesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Start exercise activity and send the data

                String exerciseName = adapterView.getItemAtPosition(i).toString();

                Intent exerciseIntent = new Intent(ExercisesActivity.this, ExerciseActivity.class);
                exerciseIntent.putExtra("exercise_name", exerciseName);
                startActivity(exerciseIntent);
            }
        });
    }
}