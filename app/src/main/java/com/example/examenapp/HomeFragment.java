package com.example.examenapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Date;

public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Get the view and inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button settingsBtn = view.findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsFragment settingsFragment = new SettingsFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, settingsFragment).commit();
            }
        });

        CalendarView calendarView = (CalendarView) view.findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                //Toast.makeText(getActivity(), "Clicked Date", Toast.LENGTH_SHORT).show();

                String date = i + "/" + (i1 + 1) + "/" + i2;

                //Launch Exersices Avtivity with the date information
                Intent launchExercisesAct = new Intent(getActivity(), ExercisesActivity.class);
                launchExercisesAct.putExtra("date", date);
                startActivity(launchExercisesAct);
            }
        });

        return view;
    }
}