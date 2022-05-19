package com.example.examenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    String exerciseName;

    TextView toolbarText;
    TextView maxWeightTextView;
    TextView totalWeightTextView;

    ArrayList<Exercise> statisticsExercises = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        exerciseName = getIntent().getStringExtra("exercise_name");

        statisticsExercises = Exercises.getMarkedExercises(getApplicationContext(), exerciseName);

        if(statisticsExercises == null) {
            statisticsExercises = new ArrayList<>();
        }

        toolbarText = findViewById(R.id.toolbarText);
        maxWeightTextView = findViewById(R.id.maxWeightTextView);
        totalWeightTextView = findViewById(R.id.totalWeightTextView);

        maxWeightTextView.setText("Max Weight: " + String.valueOf(bestMaxWeight()));
        totalWeightTextView.setText("Total Weight: " + String.valueOf(bestTotalWeight()));
        toolbarText.setText("Statistics - " + exerciseName);
    }

    private float bestMaxWeight() {
        float bestMaxWeight = 0;

        if(statisticsExercises.size() > 1) {
            for(int i = 0; i < statisticsExercises.size(); i++) {
                for(int j = -1; i < statisticsExercises.size() - 1; i++) {
                    if(statisticsExercises.get(i).getPr() > statisticsExercises.get(j).getPr()) {
                        bestMaxWeight = statisticsExercises.get(i).getPr();
                    }
                }
            }

        } else {
            bestMaxWeight = statisticsExercises.get(0).getPr();
        }

        return bestMaxWeight;
    }

    private float bestTotalWeight() {
        float bestTotalWeight = 0;

        if(statisticsExercises.size() > 1) {
            for(int i = 0; i < statisticsExercises.size(); i++) {
                for(int j = 1; i < statisticsExercises.size() - 1; i++) {
                    if(statisticsExercises.get(i).getTotalWeight() >
                            statisticsExercises.get(j).getTotalWeight()) {
                        bestTotalWeight = statisticsExercises.get(i).getPr();
                    }
                }
            }

        } else {
            bestTotalWeight = statisticsExercises.get(0).getTotalWeight();
        }

        return bestTotalWeight;
    }
}