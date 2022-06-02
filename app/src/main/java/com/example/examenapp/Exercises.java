package com.example.examenapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//This is like a helper class
public final class Exercises {
    private static ArrayList<Exercise> exercises = new ArrayList<>();
    private static ArrayList<Exercise> markedExercises = new ArrayList<>();
    private static ArrayList<String> allExercises = new ArrayList<>();

    //How we save the data (locally)
    private static SharedPreferences sharedPreferences;

    public static ArrayList<String> getAllExercises() {
        //Bicep, Back & Abs
        allExercises.add("Barbell Curls");
        allExercises.add("Barbell Preacher Curls");
        allExercises.add("Dumbbell Preacher Curls");
        allExercises.add("Dumbbell Curls");
        allExercises.add("Cable Curls");
        allExercises.add("Concentration Curls");
        allExercises.add("Standing Concentration Curls");
        allExercises.add("21 Curls");
        allExercises.add("Hammer Curls");
        allExercises.add("Crossover Hammer Curls");
        allExercises.add("Barbell Spider Curls");
        allExercises.add("Dumbbell Spider Curls");
        allExercises.add("Reverse Grip Curls");
        allExercises.add("Landmine Rows");
        allExercises.add("Inverted Rows");
        allExercises.add("Dumbell Rows");
        allExercises.add("Barbell Rows");
        allExercises.add("Seated Cable Rows");
        allExercises.add("Chest-Supported Cable Rows");
        allExercises.add("Chest-Supported Dumbell Rows");
        allExercises.add("Lat Pulldowns");
        allExercises.add("Bicycle Crunches");
        allExercises.add("Circle Crunches");
        allExercises.add("Leg Raises");
        allExercises.add("Leg Raises Bent Legs");
        allExercises.add("Pull Ups");
        allExercises.add("Close Pull Ups");
        allExercises.add("Chins");
        allExercises.add("Close Chins");
        allExercises.add("Sit Ups");
        allExercises.add("Plank");

        //Chest & Triceps
        allExercises.add("Barbell Benchpress");
        allExercises.add("Barbell Close Grip Benchpress");
        allExercises.add("Barbell Incline Benchpress");
        allExercises.add("Barbell Decline Benchpress");
        allExercises.add("Dumbbell Benchpress");
        allExercises.add("Dumbbell Incline Benchpress");
        allExercises.add("Dumbbell Decline Benchpress");
        allExercises.add("Cable Benchpress");
        allExercises.add("Cable Incline Benchpress");
        allExercises.add("Cable Decline Benchpress");
        allExercises.add("Dumbbell Flies");
        allExercises.add("Cable Flies");
        allExercises.add("Dips");
        allExercises.add("Tricep Extensions");
        allExercises.add("Tricep Overhead Press");
        allExercises.add("Push Ups");
        allExercises.add("Decline Push Ups");
        allExercises.add("Incline Push Ups");
        allExercises.add("Diamond Push Ups");
        allExercises.add("Decline Diamond Push Ups");
        allExercises.add("Incline Diamond Push Ups");

        //Legs, Shoulders & Traps
        allExercises.add("Military Press");
        allExercises.add("Squats");
        allExercises.add("Air Squats");
        allExercises.add("Pistol Squats");
        allExercises.add("Leg Extensions");
        allExercises.add("Calf Raises");
        allExercises.add("Box Jump");
        allExercises.add("Kettlebell Swings");
        allExercises.add("Front Raises");
        allExercises.add("Overhead Trap Raises");
        allExercises.add("Lateral Raises");
        allExercises.add("Cable Lateral Raises");
        allExercises.add("Cable Lateral Raises");
        allExercises.add("Farmers Walk Straps");
        allExercises.add("Barbell Shrugs");
        allExercises.add("Dumbell Shrugs");
        allExercises.add("Rack Pulls");

        //Neck (not including traps) & Forearms
        allExercises.add("Front Neck Curl");
        allExercises.add("Back Neck Curl");
        allExercises.add("Right Neck Curl");
        allExercises.add("Left Neck Curl");
        allExercises.add("Farmers Walk");

        //Cardio/full body
        allExercises.add("Burpees");
        allExercises.add("Jumping Jacks");

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

    //Returns the names of all the exercises where users weight matters most
    public static ArrayList<String> getBodyweightExercises() {
        ArrayList<String> bodyweightExercises = new ArrayList<>();
        bodyweightExercises.add("Squats");
        bodyweightExercises.add("Air Squats");
        bodyweightExercises.add("Pistol Squats");
        bodyweightExercises.add("Calf Raises");
        bodyweightExercises.add("Dips");
        bodyweightExercises.add("Pull Ups");
        bodyweightExercises.add("Box Jump");
        bodyweightExercises.add("Chin Ups");
        bodyweightExercises.add("Push Ups");
        bodyweightExercises.add("Burpees");
        bodyweightExercises.add("Sit Ups");
        bodyweightExercises.add("Plank");

        return bodyweightExercises;
    }

    public static void addExercise(String name, String date) {
        Exercise exercise = new Exercise(name, date, new ArrayList<>());
        exercises.add(exercise);
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

        saveToStatistics(context, markedExercises);
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

    private static void saveToStatistics(Context context, ArrayList<Exercise> statisticsExercises) {
        sharedPreferences = context.getSharedPreferences("exercise_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor saveEditor = sharedPreferences.edit();

        String json = sharedPreferences.getString("all_marked_exercises", null);

        //Deserialize and get, then serialize and save
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Exercise>>(){}.getType();

        ArrayList<Exercise> alreadyInList = gson.fromJson(json, type);

        //No exercises has been marked as done
        if(alreadyInList == null) {
            alreadyInList = new ArrayList<>();
        }

        for(Exercise exercise : statisticsExercises) {
            alreadyInList.add(exercise);
        }

        json = gson.toJson(alreadyInList);
        saveEditor.putString("all_marked_exercises", json);
        saveEditor.apply();
    }

    public static ArrayList<Exercise> getMarkedExercises(Context context, String name) {
        ArrayList<Exercise> markedExercises = new ArrayList<>();

        sharedPreferences = context.getSharedPreferences("exercise_data", Context.MODE_PRIVATE);

        String json = sharedPreferences.getString("all_marked_exercises", null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Exercise>>(){}.getType();
        markedExercises = gson.fromJson(json, type);

        if(markedExercises != null) {
            //Need to do this because it decreases otherwise
            int size = markedExercises.size();

            for(int i = 0; i < size; i++) {
                if(markedExercises.size() > 0) {
                    try {
                        if(!markedExercises.get(i).name.contains(name)) {
                            markedExercises.remove(i);
                            i--;
                        }

                    } catch (Exception e) {

                    }
                }
            }
        }

        return markedExercises;
    }

    public static void removeAllData(Context context) {
        sharedPreferences = context.getSharedPreferences("exercise_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor saveEditor = sharedPreferences.edit();
        saveEditor.clear().apply();

        sharedPreferences = context.getSharedPreferences("settings_data", Context.MODE_PRIVATE);
        saveEditor = sharedPreferences.edit();
        saveEditor.clear().apply();
    }
}
