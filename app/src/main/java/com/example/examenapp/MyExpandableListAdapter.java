package com.example.examenapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    ArrayList<String> muscleGroupList;
    HashMap<String, ArrayList<String>> exerciseList;

    Context context;

    public MyExpandableListAdapter(Context context, ArrayList<String> muscleGroupList,
                                   HashMap<String, ArrayList<String>> exerciseList) {
        this.context = context;
        this.muscleGroupList = muscleGroupList;
        this.exerciseList = exerciseList;
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
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.muscle_group_list, null);
        }

        String muscleGroupTitle = getGroup(i).toString();

        TextView muscleGroupText = view.findViewById(R.id.muscleGroupText);
        muscleGroupText.setText(muscleGroupTitle);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.exercise_list, null);
        }

        String exerciseTitle = getChild(i, i1);

        TextView exerciseText = view.findViewById(R.id.exerciseListText);
        exerciseText.setText(exerciseTitle);

        exerciseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, exerciseTitle, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
