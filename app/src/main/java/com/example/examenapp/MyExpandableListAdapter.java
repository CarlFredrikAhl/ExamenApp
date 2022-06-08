package com.example.examenapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

//This is a custom class to use expandable list and implement my own functionality
public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    //The groups
    ArrayList<String> muscleGroupList;

    //The items in the groups
    HashMap<String, ArrayList<String>> exerciseList;

    Context context;

    String date = "";

    //This is for FitnessFragment
    public MyExpandableListAdapter(Context context, ArrayList<String> muscleGroupList,
                                   HashMap<String, ArrayList<String>> exerciseList) {
        this.context = context;
        this.muscleGroupList = muscleGroupList;
        this.exerciseList = exerciseList;
    }

    //This is for ChooseExerciseFragment
    public MyExpandableListAdapter(Context context, ArrayList<String> muscleGroupList,
                                   HashMap<String, ArrayList<String>> exerciseList, String date) {
        this.context = context;
        this.muscleGroupList = muscleGroupList;
        this.exerciseList = exerciseList;
        this.date = date;
    }

    @Override
    public int getGroupCount() {
        return muscleGroupList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return exerciseList.get(muscleGroupList.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return muscleGroupList.get(i);
    }

    @Override
    public String getChild(int i, int i1) {
        return exerciseList.get(muscleGroupList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPos, boolean b, View view, ViewGroup viewGroup) {
        if(view == null) {
            //Inflate the view
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.muscle_group_list, null);
        }

        String muscleGroupTitle = getGroup(groupPos).toString();

        TextView muscleGroupText = view.findViewById(R.id.muscleGroupText);
        muscleGroupText.setText(muscleGroupTitle);

        return view;
    }

    @Override
    public View getChildView(int groupPos, int childPos, boolean b, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.exercise_list, null);
        }

        String exerciseName = getChild(groupPos, childPos);

        TextView exerciseText = view.findViewById(R.id.exerciseListText);
        exerciseText.setText(exerciseName);

        exerciseText.setOnClickListener(view2 -> {
            //We are using ChooseExerciseFragment and we have a date
            if(!date.equals("")) {
                //Add exercise
                Exercises.addExercise(exerciseName, date);

                ExercisesActivity.addedExercise = true;
                ExercisesActivity.updateListview();
            } else {
                Intent intent = new Intent(context, StatisticsActivity.class);
                intent.putExtra("exercise_name", exerciseName);
                context.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
