package com.example.examenapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;

import java.sql.Array;
import java.util.ArrayList;

public class SettingsFragment extends Fragment {

    Button clearDataBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        clearDataBtn = view.findViewById(R.id.clearDataBtn);
        clearDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AlertDialog and able to mark as done exercises
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Clear data?");
                alert.setMessage("Are you sure you want to remove all your saved data? This also removes the statistics");
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
}