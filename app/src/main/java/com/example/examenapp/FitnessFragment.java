package com.example.examenapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FitnessFragment extends Fragment {

    ListView exercisesList;
    ArrayList<String> exercises;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fitness, container, false);

        exercisesList = view.findViewById(R.id.allExercisesList);
       //exercisesList.setBackgroundColor(Color.GRAY);

        exercises = Exercises.getAllExercises();

        ArrayList<String> exercisesArray = exercises;

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, exercisesArray);
        exercisesList.setAdapter(arrayAdapter);
        exercisesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String exerciseName = adapterView.getItemAtPosition(i).toString();

                Intent exerciseIntent = new Intent(getActivity(), StatisticsActivity.class);
                exerciseIntent.putExtra("exercise_name", exerciseName);
                startActivity(exerciseIntent);
            }
        });

        return view;
    }
}