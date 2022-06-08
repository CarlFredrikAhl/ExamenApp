package com.example.examenapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

//CLEANED
public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Get the view and inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button settingsBtn = view.findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(view2 -> {
            SettingsFragment settingsFragment = new SettingsFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, settingsFragment).commit();
        });

        CalendarView calendarView = (CalendarView) view.findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener((calendarView1, i, i1, i2) -> {
            //i = year, (i1 + 1) = month, i2 = day
            String date = i + "/" + (i1 + 1) + "/" + i2;

            //Launch Exersices Avtivity with the date information
            Intent intent = new Intent(getActivity(), ExercisesActivity.class);
            intent.putExtra("date", date);
            startActivity(intent);
        });

        return view;
    }
}