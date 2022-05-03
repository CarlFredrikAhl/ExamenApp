package com.example.examenapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class FitnessFragment extends Fragment {

    ListView exercisesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fitness, container, false);

        exercisesList = view.findViewById(R.id.allExercisesList);
        exercisesList.setBackgroundColor(Color.RED);

        ArrayList<String> exercisesArray = new ArrayList<>();
        exercisesArray.add("Bänkpress");
        exercisesArray.add("Militärpress");
        exercisesArray.add("Lateral Raises");
        exercisesArray.add("Bicep Curls");
        exercisesArray.add("Benböj");
        exercisesArray.add("Pull Ups");
        exercisesArray.add("Chins");
        exercisesArray.add("Farmers Walk");
        exercisesArray.add("Dips");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, exercisesArray);
        exercisesList.setAdapter(arrayAdapter);

        return view;
    }
}