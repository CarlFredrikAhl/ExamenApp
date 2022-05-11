package com.example.examenapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class ExerciseDataActivity extends AppCompatActivity {

    Toolbar toolbar;

    EditText setsPicker;
    EditText repsPicker;
    EditText weightPicker;
    EditText restTimePicker;

    Switch failureSwitch;

    Button doneBtn;

    TextView toolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_data);

        setsPicker = findViewById(R.id.setsPicker);
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

        toolbarText = findViewById(R.id.toolbarText);
        String exerciseName = getIntent().getStringExtra("exercise_name");
        toolbarText.setText(exerciseName);
    }

    public void done() {
        if(setsPicker.getText().length() > 0 && weightPicker.getText().length() > 0 && restTimePicker.getText().length() > 0) {
            if(failureSwitch.isChecked() || repsPicker.getText().length() > 0) {
                //Go back to exercises list
                Intent intent = new Intent(ExerciseDataActivity.this, ExercisesActivity.class);
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), "Fill in all data", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "Fill in all data", Toast.LENGTH_SHORT).show();
        }
    }
}