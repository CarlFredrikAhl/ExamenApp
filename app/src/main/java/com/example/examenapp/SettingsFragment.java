package com.example.examenapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsFragment extends Fragment {
    Button clearDataBtn;
    Button doneBtn;

    EditText weightPicker;

    //How we save the data (locally)
    static SharedPreferences sharedPreferences;

    //The weight that has already been inputted (if any)
    float startWeight;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        weightPicker = view.findViewById(R.id.weightPicker);
        setWeight();

        doneBtn = view.findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(view2 -> done());

        clearDataBtn = view.findViewById(R.id.clearDataBtn);
        clearDataBtn.setOnClickListener(view3 -> clearData());

        return view;
    }

    //Set the weight to the weight that has been inputted, otherwise set it to empty
    public void setWeight() {
        sharedPreferences = getActivity().getSharedPreferences("settings_data", Context.MODE_PRIVATE);

        float savedWeight = sharedPreferences.getFloat("weight", 0f);
        startWeight = savedWeight;

        if(savedWeight != 0f) {
            weightPicker.setText(String.valueOf(savedWeight));
        } else {
            weightPicker.setText("");
        }
    }

    private void done() {
        if(!weightPicker.getText().toString().isEmpty()) {
            float weight = Float.parseFloat(weightPicker.getText().toString());

            //Searched up the approximately lowest vs the highest weight of adult
            if(weight >= 5.9f && weight <= 635) {
                //The weight has changes from previous input
                if(weight != startWeight) {
                    //Save the weight
                    sharedPreferences = getActivity().getSharedPreferences("settings_data",
                            Context.MODE_PRIVATE);
                    SharedPreferences.Editor saveEditor = sharedPreferences.edit();
                    saveEditor.putFloat("weight", weight);
                    saveEditor.apply();
                }

                HomeFragment homeFragment = new HomeFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

            } else {
                Toast.makeText(getActivity(), "Enter your real weight", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void clearData() {
        //AlertDialog and able to mark as done exercises
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Clear data?");
        alert.setMessage("Are you sure? This will remove ALL the data");
        alert.setPositiveButton("Yes", (dialogInterface, i) -> {
            //Clear the data
            Exercises.removeAllData(getActivity());

            HomeFragment homeFragment = new HomeFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
        });
        alert.setNegativeButton("No", (dialogInterface, i) -> { });
        alert.create().show();
    }

    //For other classes to access the weight
    public static float getUserWeight(Context context) {
        sharedPreferences = context.getSharedPreferences("settings_data", Context.MODE_PRIVATE);
        float savedWeight = sharedPreferences.getFloat("weight", 0f);

        return savedWeight;
    }
}