package com.example.examenapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

public class ExercisesActivity extends AppCompatActivity {
    //The exercises
    private static ArrayList<Exercise> exercises;

    //This is used to store the name of the exercises
    private static ArrayList<String> exercisesArray;

    public static boolean addedExercise = false;
    public static boolean markedAsDone;

    public static ListView exercisesList;
    private static ArrayAdapter arrayAdapter;

    public static ImageView addButton;
    public static ImageView saveButton;

    TextView toolbarText;

    Toolbar toolbar;

    RelativeLayout layout;

    public static String date;

    private static Button markAsDoneBtn;

    ChooseExerciseFragment chooseExerciseFragment = new ChooseExerciseFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        markedAsDone = false;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setOnClickListener(view -> {
            if(chooseExerciseFragment.isVisible()) {
                closeChooseFragment();
            }
        });

        toolbarText = findViewById(R.id.toolbarText);
        markAsDoneBtn = findViewById(R.id.markAsDoneBtn);
        markAsDoneBtn.setOnClickListener(view -> markAsDone());

        date = getIntent().getStringExtra("date");

        toolbarText.setText(date);

        try {
            Exercises.loadData(getApplicationContext(), date);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Couldn't load data", Toast.LENGTH_SHORT).show();
        }

        exercises = Exercises.getExercises();

        layout = findViewById(R.id.exercisesLayout);
        layout.setOnClickListener(view -> {
            if(chooseExerciseFragment.isVisible()) {
                closeChooseFragment();
            }
        });

        saveButton = findViewById(R.id.saveExercisesBtn);
        saveButton.setOnClickListener(view -> {
            Exercises.saveData(getApplicationContext(), date);
            saveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_saved_24));
        });

        saveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_saved_24));

        addButton = findViewById(R.id.addExerciseBtn);
        addButton.setOnClickListener(view -> {
            if(chooseExerciseFragment.isVisible()) {
                closeChooseFragment();

            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.chooseContainer, chooseExerciseFragment).commit();
            }
        });

        exercisesList = findViewById(R.id.exercisesList);

        exercisesArray = getExercisesName();

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, exercisesArray);
        exercisesList.setAdapter(arrayAdapter);
        exercisesList.setOnItemClickListener((adapterView, view, i, l) -> {
            //Get the clicked exercise
            Exercise clickedExercise = exercises.get(i);

            if(chooseExerciseFragment.isVisible()) {
                closeChooseFragment();

            } else {
                if(clickedExercise.sets == null || clickedExercise.sets.size() == 0) {
                    //We haven't added sets to the exercise and we need to do it now

                    String exerciseName = adapterView.getItemAtPosition(i).toString();

                    Intent intent = new Intent(ExercisesActivity.this, ExerciseDataActivity.class);
                    intent.putExtra("exercise_name", exerciseName);
                    intent.putExtra("exercise_id", clickedExercise.id);
                    intent.putExtra("date", date);
                    startActivity(intent);

                } else {
                    //Start set activity
                    Intent intent = new Intent(ExercisesActivity.this, SetsActivity.class);
                    intent.putExtra("exercise_id", clickedExercise.id);
                    intent.putExtra("date", date);
                    intent.putExtra("markedAsDone", markedAsDone);
                    startActivity(intent);
                }
            }
        });

        exercisesList.setOnItemLongClickListener((adapterView, view, exerciseId, l) -> {
            //AlertDialog and able to remove exercise
            AlertDialog.Builder alert = new AlertDialog.Builder(ExercisesActivity.this);
            alert.setTitle("Delete Exercise");
            alert.setMessage("Are you sure you want to delete exercise?");
            alert.setPositiveButton("Yes", (dialogInterface, i1) -> {
                Exercises.removeExercise(getApplicationContext(), exercises.get(exerciseId).id, date);
                saveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_saved_24));

                //Need to restart this activity
               restartActivity();
            });
            alert.setNegativeButton("No", (dialogInterface, i12) -> { });
            alert.create().show();

            return true;
        });

        if(Exercises.markedAsDone(getApplicationContext(), date)) {
            markedAsDone(getResources().getDrawable(R.drawable.ic_baseline_saved_24),
                    getResources().getDrawable(R.drawable.ic_baseline_add_disabled_24));
            markedAsDone = true;
        }

        checkMarkStatus();
    }

    @Override
    public void onBackPressed() {
        Intent intent = NavUtils.getParentActivityIntent(this);
        startActivity(intent);
    }

    //Closes the choose fragment and reload the list data
    public void closeChooseFragment() {
        getSupportFragmentManager().beginTransaction().remove(chooseExerciseFragment).commit();

        if(addedExercise) {
            saveButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_save_24));
        }

        checkMarkStatus();
    }

    public void restartActivity() {
        Intent intent = new Intent(ExercisesActivity.this, ExercisesActivity.class);
        intent.putExtra("date", date);
        startActivity(intent);
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

        if(exercises != null) {
            for(Exercise exercise : exercises) {
                String name = exercise.name;
                names.add(name);
            }
        }

        return names;
    }

    void markAsDone() {
        //AlertDialog and able to mark as done exercises
        AlertDialog.Builder alert = new AlertDialog.Builder(ExercisesActivity.this);
        alert.setTitle("Mark Exercises As Done?");
        alert.setMessage("You have completed all the exercises and they will be added to statistics");
        alert.setPositiveButton("Yes", (dialogInterface, i) -> {
            //Mark as done
            Exercises.markAsDone(getApplicationContext(), date, exercises);
            markedAsDone = true;
            markedAsDone(getResources().getDrawable(R.drawable.ic_baseline_saved_24),
                    getResources().getDrawable(R.drawable.ic_baseline_add_disabled_24));
        });
        alert.setNegativeButton("No", (dialogInterface, i) -> { });
        alert.create().show();
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

    //We can only mark as done if all exercises have sets and the date is not in the future
    private static void checkMarkStatus() {
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

        if(clickedDate.after(curDate)) {
            canMark = false;
        }

        if(canMark && !markedAsDone) {
            markAsDoneBtn.setEnabled(true);

        } else {
            markAsDoneBtn.setEnabled(false);
        }
    }
}