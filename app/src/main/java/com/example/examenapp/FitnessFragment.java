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
import android.widget.ExpandableListView;
import android.widget.ListView;

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

        expandableListView = view.findViewById(R.id.expandableListView);

        showList();

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

        //region exercises1
        ArrayList<String> exercises1 = new ArrayList<>();
        exercises1.add("Dumbbell Curls");
        exercises1.add("Barbell Curls");
        exercises1.add("Hammer Curls");
        exercises1.add("Crossover Hammer Curls");
        exercises1.add("Preacher Curls");
        exercises1.add("Cable Curls");
        exercises1.add("Concentration Curls");
        exercises1.add("Standing Concentration Curls");
        exercises1.add("21 Curls");
        exercises1.add("Spider Curls");
        exercises1.add("Landmine Rows");
        exercises1.add("Inverted Rows");
        exercises1.add("Dumbbell Rows");
        exercises1.add("Barbell Rows");
        exercises1.add("Seated Cable Rows");
        exercises1.add("Chest-Supported Cable Rows");
        exercises1.add("Chest-Supported Dumbbell Rows");
        exercises1.add("Deadlifts");
        exercises1.add("Lat Pulldowns");
        exercises1.add("Bicycle Crunches");
        exercises1.add("Circle Crunches");
        exercises1.add("Leg Raises");
        exercises1.add("Leg Raises Bent Legs");
        exercises1.add("Pull Ups");
        exercises1.add("Close Pull Ups");
        exercises1.add("Chins");
        exercises1.add("Close Chins");
        exercises1.add("Sit Ups");
        exercises1.add("Plank");
        //endregion

        //region exercises2
        ArrayList<String> exercises2 = new ArrayList<>();
        exercises2.add("Barbell Benchpress");
        exercises2.add("Barbell Close Grip Benchpress");
        exercises2.add("Barbell Incline Benchpress");
        exercises2.add("Barbell Decline Benchpress");
        exercises2.add("Dumbbell Benchpress");
        exercises2.add("Dumbbell Incline Benchpress");
        exercises2.add("Dumbbell Decline Benchpress");
        exercises2.add("Cable Benchpress");
        exercises2.add("Cable Incline Benchpress");
        exercises2.add("Cable Decline Benchpress");
        exercises2.add("Dumbbell Flies");
        exercises2.add("Cable Flies");
        exercises2.add("Dips");
        exercises2.add("Tricep Extensions");
        exercises2.add("Tricep Overhead Press");
        exercises2.add("Push Ups");
        exercises2.add("Decline Push Ups");
        exercises2.add("Incline Push Ups");
        exercises2.add("Diamond Push Ups");
        exercises2.add("Decline Diamond Push Ups");
        exercises2.add("Incline Diamond Push Ups");
        //endregion

        //region exercises3
        ArrayList<String> exercises3 = new ArrayList<>();
        exercises3.add("Military Press");
        exercises3.add("Arnold Press");
        exercises3.add("Squats");
        exercises3.add("Air Squats");
        exercises3.add("Pistol Squats");
        exercises3.add("Leg Extensions");
        exercises3.add("Calf Raises");
        exercises3.add("Box Jump");
        exercises3.add("Kettlebell Swings");
        exercises3.add("Face Pulls Lying Down");
        exercises3.add("High Cable Rear Delt Fly");
        exercises3.add("Front Raises");
        exercises3.add("Overhead Trap Raises");
        exercises3.add("Lateral Raises");
        exercises3.add("Bent-Over Lateral Raises");
        exercises3.add("Cable Lateral Raises");
        exercises3.add("Bent-Over Cable Lateral Raises");
        exercises3.add("Farmers Walk Straps");
        exercises3.add("Barbell Shrugs");
        exercises3.add("Dumbbell Shrugs");
        exercises3.add("Rack Pulls");
        //endregion

        //region exercises4
        ArrayList<String> exercises4 = new ArrayList<>();
        exercises4.add("Front Neck Curl");
        exercises4.add("Back Neck Curl");
        exercises4.add("Right Neck Curl");
        exercises4.add("Left Neck Curl");
        exercises4.add("Reverse Grip Curls");
        exercises4.add("Behind-The-Back Barbell Wrist Curls");
        exercises4.add("Barbell Wrist Curls");
        exercises4.add("Barbell Reverse Wrist Curls");
        exercises4.add("Wrist Roller");
        exercises4.add("Adjustable Hand Grip");
        exercises4.add("Farmers Walk");
        //endregion

        //region exercises5
        ArrayList<String> exercises5 = new ArrayList<>();
        exercises5.add("Burpees");
        exercises5.add("Jumping Jacks");
        //endregion

        exercisesList.put(muscleGroupsList.get(0), exercises1);
        exercisesList.put(muscleGroupsList.get(1), exercises2);
        exercisesList.put(muscleGroupsList.get(2), exercises3);
        exercisesList.put(muscleGroupsList.get(3), exercises4);
        exercisesList.put(muscleGroupsList.get(4), exercises5);
    }
}