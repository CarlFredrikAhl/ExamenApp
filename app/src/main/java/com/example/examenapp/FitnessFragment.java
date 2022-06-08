package com.example.examenapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import java.util.ArrayList;
import java.util.HashMap;

public class FitnessFragment extends Fragment {
    ExpandableListView expandableListView;
    MyExpandableListAdapter myExpandableListAdapter;

    ArrayList<String> muscleGroupsList;
    HashMap<String, ArrayList<String>> exercisesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fitness, container, false);

        expandableListView = view.findViewById(R.id.allExercisesExpandableListView);

        showList();

        //Setting up the adapter and setting some layout-properties for the list
        myExpandableListAdapter = new MyExpandableListAdapter(getActivity(), muscleGroupsList, exercisesList);
        expandableListView.setAdapter(myExpandableListAdapter);
        expandableListView.setChildDivider(getResources().getDrawable(R.color.gray));
        expandableListView.setDividerHeight(2);

        return view;
    }

    public void showList() {
        muscleGroupsList = new ArrayList();
        exercisesList = new HashMap<>();

        muscleGroupsList.add("Bicep, Back & Abs");
        muscleGroupsList.add("Chest & Triceps");
        muscleGroupsList.add("Legs, Shoulders & Traps");
        muscleGroupsList.add("Neck & Forearms");
        muscleGroupsList.add("Cardio/Full Body");

        exercisesList.put(muscleGroupsList.get(0), Exercises.getBicepBackAbs());
        exercisesList.put(muscleGroupsList.get(1), Exercises.getChestTriceps());
        exercisesList.put(muscleGroupsList.get(2), Exercises.getLegsShouldersTraps());
        exercisesList.put(muscleGroupsList.get(3), Exercises.getNeckForearms());
        exercisesList.put(muscleGroupsList.get(4), Exercises.getCardioFullbody());
    }
}