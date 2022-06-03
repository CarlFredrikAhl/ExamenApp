package com.example.examenapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Array;
import java.util.ArrayList;

public class SettingsFragment extends Fragment {
    Button clearDataBtn;
    Button doneBtn;

    EditText weightPicker;

    float startWeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        weightPicker = view.findViewById(R.id.weightPicker);
        setWeight();

        doneBtn = view.findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done();
            }
        });

        clearDataBtn = view.findViewById(R.id.clearDataBtn);
        clearDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AlertDialog and able to mark as done exercises
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Clear data?");
                alert.setMessage("Are you sure? This will remove ALL the data");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Clear the data
                        Exercises.removeAllData(getActivity());
                        Toast.makeText(getActivity(), "Data cleared", Toast.LENGTH_SHORT).show();

                        HomeFragment homeFragment = new HomeFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.create().show();
            }
        });

        return view;
    }

    public void setWeight() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings_data", Context.MODE_PRIVATE);

        float savedWeight = sharedPreferences.getFloat("weight", 0f);
        startWeight = savedWeight;

        if(savedWeight != 0f) {
            weightPicker.setText(String.valueOf(savedWeight));

        } else {
            weightPicker.setText("");
        }
    }

    public void done() {
        if(!weightPicker.getText().toString().isEmpty()) {
            float weight = Float.parseFloat(weightPicker.getText().toString());

            //The weight has changes from previous input
            if(weight != startWeight) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("settings_data", Context.MODE_PRIVATE);
                SharedPreferences.Editor saveEditor = sharedPreferences.edit();
                saveEditor.putFloat("weight", weight);
                saveEditor.apply();
                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
            }
        }

        HomeFragment homeFragment = new HomeFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
    }

    public static float getUserWeight(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings_data", Context.MODE_PRIVATE);
        float savedWeight = sharedPreferences.getFloat("weight", 0f);

        return savedWeight;
    }
}