package com.example.examenapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChooseExerciseFragment extends Fragment {

    ListView exercisesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_exercise, container, false);

        exercisesList = view.findViewById(R.id.chooseExerciseListView);
        exercisesList.setBackgroundColor(Color.BLUE);

        ArrayList<String> exercisesArray = Exercises.getAllExercises();

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, exercisesArray);
        exercisesList.setAdapter(arrayAdapter);
        exercisesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Pressed exercise", Toast.LENGTH_SHORT).show();

                String exerciseName = adapterView.getItemAtPosition(i).toString();

                Exercises.addExercise(exerciseName);
                ExercisesActivity.updateListview();
            }
        });

        return view;
    }
}