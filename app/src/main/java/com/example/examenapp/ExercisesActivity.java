package com.example.examenapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExercisesActivity extends AppCompatActivity {

    public static ListView exercisesList;

    private static ArrayList<Exercise> exercises;
    private static ArrayList<String> exercisesArray;

    public static boolean addedExercise = false;
    public static boolean markedAsDone;

    public static ImageView addButton;
    public static ImageView saveButton;

    TextView toolbarText;

    Toolbar toolbar;

    RelativeLayout layout;

    private static ArrayAdapter arrayAdapter;

    public static String date;

    private static Button markAsDoneBtn;

    ChooseExerciseFragment chooseExerciseFragment = new ChooseExerciseFragment();

    private boolean firstTimeLaunching = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        markedAsDone = false;
        firstTimeLaunching = true;

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chooseExerciseFragment.isVisible()) {
                    closeChooseFragment();
                }
            }
        });

        toolbarText = findViewById(R.id.toolbarText);
        markAsDoneBtn = findViewById(R.id.markAsDoneBtn);
        markAsDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AlertDialog and able to mark as done exercises
                AlertDialog.Builder alert = new AlertDialog.Builder(ExercisesActivity.this);
                alert.setTitle("Mark Exercise As Done?");
                alert.setMessage("You have completed all the exercise and they will be added to statistics");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Mark as done
                        Exercises.markAsDone(getApplicationContext(), date, exercises);
                        markedAsDone = true;
                        markedAsDone(getResources().getDrawable(R.drawable.ic_baseline_saved_24),
                                getResources().getDrawable(R.drawable.ic_baseline_add_disabled_24));
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

        date = getIntent().getStringExtra("date");

        toolbarText.setText(date);

        try {
            Exercises.loadData(getApplicationContext(), date);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Couldn't load data", Toast.LENGTH_SHORT).show();
        }

        exercises = Exercises.getExercises();

        layout = findViewById(R.id.exercisesLayout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If choose fragment is visable
                if(chooseExerciseFragment.isVisible()) {
                    closeChooseFragment();
                }
            }
        });

        saveButton = findViewById(R.id.saveExercisesBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exercises.saveData(getApplicationContext(), date);
                saveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_saved_24));
            }
        });

        saveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_saved_24));

        addButton = findViewById(R.id.addExerciseBtn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If choose fragment is visable
                if(chooseExerciseFragment.isVisible()) {
                    closeChooseFragment();

                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.chooseContainer, chooseExerciseFragment).commit();
                }
            }
        });

        exercisesList = findViewById(R.id.exercisesList);
        //exercisesList.setBackgroundColor(Color.GRAY);

        //Change this later
        exercisesArray = getExercisesName();

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, exercisesArray);
        exercisesList.setAdapter(arrayAdapter);
        exercisesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Get the clicked exercise
                Exercise clickedExercise = exercises.get(i);

                //If choose fragment is visable
                if(chooseExerciseFragment.isVisible()) {
                    closeChooseFragment();

                } else {
                    if(clickedExercise.sets == null || clickedExercise.sets.size() == 0) {
                        //Start exercise data activity and send the data

                        //Save data
                        //Data(getApplicationContext(), date);

                        String exerciseName = adapterView.getItemAtPosition(i).toString();

                        Intent exerciseIntent = new Intent(ExercisesActivity.this, ExerciseDataActivity.class);
                        exerciseIntent.putExtra("exercise_name", exerciseName);
                        exerciseIntent.putExtra("exercise_id", clickedExercise.id);
                        exerciseIntent.putExtra("date", date);
                        startActivity(exerciseIntent);

                    } else {
                        //Start set activity
                        Intent intent = new Intent(ExercisesActivity.this, SetsActivity.class);
                        intent.putExtra("exercise_id", clickedExercise.id);
                        intent.putExtra("date", date);
                        intent.putExtra("markedAsDone", markedAsDone);
                        startActivity(intent);
                    }
                }
            }
        });
        exercisesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int exerciseId = i;

                //AlertDialog and able to remove exercise
                AlertDialog.Builder alert = new AlertDialog.Builder(ExercisesActivity.this);
                alert.setTitle("Delete Exercise");
                alert.setMessage("Are you sure you want to delete exercise?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Delete exercises
                        Exercises.removeExercise(getApplicationContext(), exercises.get(exerciseId).id, date);
                        saveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_saved_24));

                        //Need to restart this activity
                       restartActivity();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.create().show();
                return true;
            }
        });

        if(Exercises.markedAsDone(getApplicationContext(), date)) {
            markedAsDone(getResources().getDrawable(R.drawable.ic_baseline_saved_24),
                    getResources().getDrawable(R.drawable.ic_baseline_add_disabled_24));
            markedAsDone = true;
        }

        checkMarkStatus(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        Intent backIntent = NavUtils.getParentActivityIntent(this);
        startActivity(backIntent);
    }

    //Dont need right now, remove later?
    @Override
    public void onResume() {
        super.onResume();

        if(!firstTimeLaunching)
            Toast.makeText(getApplicationContext(), "Resumed activity, needs to restart", Toast.LENGTH_SHORT).show();
    }

    //Closes the choose fragment and reload the list data
    public void closeChooseFragment() {
        getSupportFragmentManager().beginTransaction().remove(chooseExerciseFragment).commit();

        if(addedExercise) {
            saveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_save_24));
        }

        checkMarkStatus(getApplicationContext());
    }

    public void restartActivity() {
        Intent restartIntent = new Intent(ExercisesActivity.this, ExercisesActivity.class);
        restartIntent.putExtra("date", date);
        startActivity(restartIntent);
    }

    public static void updateListview() {
        arrayAdapter.clear();
        exercises = Exercises.getExercises();
        exercisesArray = getExercisesName();
        arrayAdapter = new ArrayAdapter(arrayAdapter.getContext(), android.R.layout.simple_list_item_1, exercisesArray);
        exercisesList.setAdapter(arrayAdapter);
    }

    private static ArrayList<String> getExercisesName() {
        ArrayList<String> names = new ArrayList<>();

        if(exercises!=null) {
            for(Exercise exercise : exercises) {
                String name = exercise.name;
                names.add(name);
            }
        }

        return names;
    }

    public static void markedAsDone(Drawable saveBtnDisabled, Drawable addBtnDisabled) {
        //Layout and interaction changes
        exercisesList.setBackgroundColor(Color.GREEN);
        exercisesList.setOnLongClickListener(null);
        exercisesList.setOnItemLongClickListener(null);
        addButton.setOnClickListener(null);
        addButton.setImageDrawable(addBtnDisabled);
        markAsDoneBtn.setOnClickListener(null);
        markAsDoneBtn.setEnabled(false);
        saveButton.setOnClickListener(null);
        saveButton.setImageDrawable(saveBtnDisabled);
    }

    private static void checkMarkStatus(Context context) {
        boolean canMark = false;
        int counter = 0;

        if(exercises.size() > 0) {
            for(Exercise exercise : exercises) {
                if(exercise.sets.size() > 0) {
                    counter++;
                }
            }

            if(counter == exercises.size())
                canMark = true;
            else
                canMark = false;
        }

        Calendar calendar = Calendar.getInstance();
        Date curDate = calendar.getTime();
        Date clickedDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            clickedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        Toast.makeText(context, "Clicked date is after current date: " + String.valueOf(clickedDate.after(curDate)),
//                Toast.LENGTH_LONG).show();

        if(clickedDate.after(curDate)) {
            //Cannot mark
            canMark = false;
        }

        if(canMark && !markedAsDone) {
            markAsDoneBtn.setEnabled(true);

        } else {
            markAsDoneBtn.setEnabled(false);
        }
    }
}