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
    private static ArrayList<Exercise> markedExercises = new ArrayList<>();
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

    public static Exercise getExercise(String exerciseId) {
        Exercise exercise = new Exercise("null", "null", new ArrayList<>());

        for(int i = 0; i < Exercises.getExercises().size(); i++)  {
            if(Exercises.getExercises().get(i).id.equals(exerciseId)) {
                exercise = Exercises.getExercises().get(i);
            }
        }
        return exercise;
    }

    public static void addExercise(String name, String date) {
        Exercise exercise = new Exercise(name, date, new ArrayList<>());
        exercises.add(exercise);
        id++;
    }

    public static void removeExercise(Context context, String exerciseId, String date) {
        //Loop through exercises and remove the right one
        for(int i = 0; i < exercises.size(); i++) {
            if(exercises.get(i).id.equals(exerciseId)) {
                exercises.remove(i);
                break;
            }
        }

        saveData(context, date);
        ExercisesActivity.updateListview();
    }

    public static void removeSet(Context context, int index, String exerciseId, String date) {
        for(int i = 0; i < exercises.size(); i++) {
            if(exercises.get(i).id.equals(exerciseId)) {
                exercises.get(i).sets.remove(index);
                break;
            }
        }

        saveData(context, date);
        SetsActivity.updateListView();
    }

    public static void saveData(Context context, String date) {
        sharedPreferences = context.getSharedPreferences("exercise_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor saveEditor = sharedPreferences.edit();

        //Serialize to json and save
        Gson gson = new Gson();
        String json = gson.toJson(exercises);
        saveEditor.putString(date + "_exercises", json);
        saveEditor.apply();
    }

    public static void clearData(Context context, String date) {
        sharedPreferences = context.getSharedPreferences("exercise_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor saveEditor = sharedPreferences.edit();
        saveEditor.remove(date + "_exercises");
        saveEditor.apply();
    }

    public static void loadData(Context context, String date) {
        sharedPreferences = context.getSharedPreferences("exercise_data", Context.MODE_PRIVATE);

        //Deserialize to json and load
        Gson gson = new Gson();
        String json = sharedPreferences.getString(date + "_exercises", null);
        Type type = new TypeToken<ArrayList<Exercise>>(){}.getType();
        exercises = gson.fromJson(json, type);

        if(exercises == null) {
            exercises = new ArrayList<>();
        }
    }

    public static void markAsDone(Context context, String date, ArrayList<Exercise> incomingExercises) {
        sharedPreferences = context.getSharedPreferences("exercise_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor saveEditor = sharedPreferences.edit();

        //Load marked exercises
        Gson gson = new Gson();
        String json = sharedPreferences.getString(date + "_marked_exercises", null);
        Type type = new TypeToken<ArrayList<Exercise>>(){}.getType();
        markedExercises = gson.fromJson(json, type);

        if(markedExercises == null) {
            markedExercises = new ArrayList<>();
        }

        //Adding the marked exercises to the array
        for(Exercise exercise : incomingExercises) {
            markedExercises.add(exercise);
        }

        //Serialize to json and save
        gson = new Gson();
        json = gson.toJson(markedExercises);
        saveEditor.putString(date + "_marked_exercises", json);
        saveEditor.apply();
    }

    public static boolean markedAsDone(Context context, String date) {
        sharedPreferences = context.getSharedPreferences("exercise_data", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(date + "_marked_exercises", null);

        if(json != null) {
            if(json.contains(date)) {
                return true;

            } else {
                return false;
            }

        } else {
            return false;
        }
    }
}
