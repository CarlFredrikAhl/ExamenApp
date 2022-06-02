package com.example.examenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ExerciseDataActivity extends AppCompatActivity {

    Toolbar toolbar;

    EditText setsPicker;
    EditText repsPicker;
    EditText weightPicker;
    EditText restTimePicker;

    Switch failureSwitch;
    Switch multipleSetsSwitch;

    Button doneBtn;

    TextView toolbarText;
    TextView repsPickerTitle;

    String exerciseId;
    String date;
    String activityFlag;
    String exerciseName;

    boolean addedSets = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_data);

        activityFlag = getIntent().getStringExtra("flag");
        if(activityFlag == null)
            activityFlag = "null";

        exerciseId = getIntent().getStringExtra("exercise_id");
        date = getIntent().getStringExtra("date");

        setsPicker = findViewById(R.id.setsPicker);
        multipleSetsSwitch  = findViewById(R.id.multipleSetsSwitch);
        multipleSetsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    setsPicker.setVisibility(View.VISIBLE);
                else
                    setsPicker.setVisibility(View.INVISIBLE);
            }
        });

        failureSwitch = findViewById(R.id.failureSwitch);
        failureSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    repsPicker.setVisibility(View.INVISIBLE);
                else
                    repsPicker.setVisibility(View.VISIBLE);
            }
        });
        repsPicker = findViewById(R.id.repsPicker);
        repsPickerTitle = findViewById(R.id.repsPickerTitle);

        exerciseName = getIntent().getStringExtra("exercise_name");

        if(exerciseName.equals("Plank")) {
            repsPickerTitle.setText("Time (Sec)");
        }

        weightPicker = findViewById(R.id.weightPicker);
        restTimePicker = findViewById(R.id.restTimePicker);
        doneBtn = findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                done();
            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbarText = findViewById(R.id.toolbarText);
        toolbarText.setText(exerciseName);
    }

    @Override
    public void onBackPressed() {
        Intent backIntent = NavUtils.getParentActivityIntent(this);
        backIntent.putExtra("date", date);
        startActivity(backIntent);
    }

    public void done() {
        if(weightPicker.getText().length() > 0 && restTimePicker.getText().length() > 0) {
            if(failureSwitch.isChecked() || repsPicker.getText().length() > 0) {
                if(multipleSetsSwitch.isChecked() == false || setsPicker.getText().length() > 0) {
                    String reps;

                    if(failureSwitch.isChecked()) {
                        reps = "Till Failure/Forced Reps/Not Counting";

                    } else {
                        reps = repsPicker.getText().toString();
                    }

                    int setsAmt = 1;

                    if(multipleSetsSwitch.isChecked()) {
                        if(setsPicker.getText().toString() != "") {
                            setsAmt = Integer.parseInt(setsPicker.getText().toString());
                        }
                    }
                    float weightAmt = Float.parseFloat(weightPicker.getText().toString());
                    int restTimeAmt = Integer.parseInt(restTimePicker.getText().toString());

                    ArrayList<MySet> sets = new ArrayList<>();

                    //Create set and add it to the exercise
                    for(int i = 0; i < setsAmt; i++) {
                        sets.add(new MySet(reps, weightAmt, restTimeAmt));
                    }

                    //Add the set to the right exercise
                    for(int i = 0; i < Exercises.getExercises().size(); i++) {

                        if(Exercises.getExercises().get(i).id.equals(exerciseId)) {
                            for(MySet set : sets) {
                                Exercises.getExercises().get(i).addSet(set);
                            }
                            addedSets = true;
                            break;
                        }
                    }

                    //Go back to sets list
                    Intent intent = new Intent(ExerciseDataActivity.this, SetsActivity.class);
                    intent.putExtra("added_set", true);
                    intent.putExtra("exercise_id", exerciseId);
                    intent.putExtra("date", date);
                    intent.putExtra("addedSets", addedSets);

                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Fill in all data", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Fill in all data", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Fill in all data", Toast.LENGTH_SHORT).show();
        }
    }
}