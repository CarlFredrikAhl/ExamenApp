package com.example.examenapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public final class Exercises {
    private static ArrayList<Exercise> exercises = new ArrayList<>();

    private static ArrayList<String> allExercises = new ArrayList<>();

    private static int id = 0;

    //How we save the data (locally)
    private static SharedPreferences sharedPreferences;

    public static ArrayList<String> getAllExercises() {
        allExercises.add("Dumbbell Incline Benchpress");
        allExercises.add("Dumbbell Benchpress");
        allExercises.add("Dumbell Flies");
        allExercises.add("Cable Flies");
        allExercises.add("Barbell Curls");
        allExercises.add("Barbell Benchpress");
        allExercises.add("Preacher Curls");
        allExercises.add("Spider Curls");
        allExercises.add("Reverse Grip Curls");
        allExercises.add("Neck Curls");
        allExercises.add("Military Press");
        allExercises.add("Squat");
        allExercises.add("Leg Extensions");
        allExercises.add("Calf Raises");
        allExercises.add("Dips");
        allExercises.add("Tricep Extensions");
        allExercises.add("Tricep Overhead Press");
        allExercises.add("Pull Ups");
        allExercises.add("Chins");
        allExercises.add("Push Ups");
        allExercises.add("Lat Pulldowns");
        allExercises.add("Barbell Incline Benchpress");
        allExercises.add("Landmine Rows");
        allExercises.add("One arm rows");
        allExercises.add("Farmers Walk");
        allExercises.add("Shrugs");
        allExercises.add("Rack Pulls");
        allExercises.add("Dumbbell Curls");

        return allExercises;
    }

    public static ArrayList<String> getFirstFiveExercises() {
        allExercises.add("Barbell Benchpress");
        allExercises.add("Barbell Incline Benchpress");
        allExercises.add("Dumbbell Benchpress");
        allExercises.add("Dumbbell Incline Benchpress");
        allExercises.add("Dumbell Flies");

        return allExercises;
    }

    public static ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public static void addExercise(String name, String date) {
        Exercise exercise = new Exercise(name, date, id);
        exercises.add(exercise);
        id++;
    }

    public static void removeExercise(Context context, int id) {
        for(int i = 0; i < exercises.size(); i++) {
            if(exercises.get(i).id == id)
                exercises.remove(i);
        }
        saveData(context);
        ExercisesActivity.updateListview();
    }

    public static void saveData(Context context) {
        sharedPreferences = context.getSharedPreferences("exercise_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor saveEditor = sharedPreferences.edit();

        //Serialize to json and save
        Gson gson = new Gson();
        String json = gson.toJson(exercises);
        saveEditor.putString("exercises", json);
        saveEditor.apply();
    }

    public static void loadData(Context context) {
        sharedPreferences = context.getSharedPreferences("exercise_data", Context.MODE_PRIVATE);

        //Deserialize to json and load
        Gson gson = new Gson();
        String json = sharedPreferences.getString("exercises", null);
        Type type = new TypeToken<ArrayList<Exercise>>(){}.getType();
        exercises = gson.fromJson(json, type);

        if(exercises == null) {
            exercises = new ArrayList<>();
        }
    }
}
