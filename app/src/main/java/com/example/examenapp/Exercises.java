package com.example.examenapp;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

//CLEANED
//This is like a helper class
public final class Exercises {
    private static ArrayList<Exercise> exercises = new ArrayList<>();
    private static ArrayList<Exercise> markedExercises = new ArrayList<>();

    //How we save the data (locally)
    private static SharedPreferences sharedPreferences;

    public static ArrayList<String> getBicepBackAbs() {
        ArrayList<String> exerciseList = new ArrayList<>();
        exerciseList.add("Dumbbell Curls");
        exerciseList.add("Barbell Curls");
        exerciseList.add("Hammer Curls");
        exerciseList.add("Crossover Hammer Curls");
        exerciseList.add("Preacher Curls");
        exerciseList.add("Cable Curls");
        exerciseList.add("Concentration Curls");
        exerciseList.add("Standing Concentration Curls");
        exerciseList.add("21 Curls");
        exerciseList.add("Spider Curls");
        exerciseList.add("Close Chin Ups");
        exerciseList.add("Chins Ups");
        exerciseList.add("Pull Ups");
        exerciseList.add("Close Pull Ups");
        exerciseList.add("Landmine Rows");
        exerciseList.add("Inverted Rows");
        exerciseList.add("Dumbbell Rows");
        exerciseList.add("Barbell Rows");
        exerciseList.add("Seated Cable Rows");
        exerciseList.add("Chest-Supported Cable Rows");
        exerciseList.add("Chest-Supported Dumbbell Rows");
        exerciseList.add("Deadlift");
        exerciseList.add("Lat Pulldowns");
        exerciseList.add("Leg Raises");
        exerciseList.add("Leg Raises Bent Legs");
        exerciseList.add("Sit Ups");
        exerciseList.add("Crunches");
        exerciseList.add("Bicycle Crunches");
        exerciseList.add("Circle Crunches");
        exerciseList.add("Plank");

        return exerciseList;
    }

    public static ArrayList<String> getChestTriceps() {
        ArrayList<String> exerciseList = new ArrayList<>();
        exerciseList.add("Barbell Benchpress");
        exerciseList.add("Barbell Close Grip Benchpress");
        exerciseList.add("Barbell Incline Benchpress");
        exerciseList.add("Barbell Decline Benchpress");
        exerciseList.add("Dumbbell Benchpress");
        exerciseList.add("Dumbbell Incline Benchpress");
        exerciseList.add("Dumbbell Decline Benchpress");
        exerciseList.add("Cable Benchpress");
        exerciseList.add("Cable Incline Benchpress");
        exerciseList.add("Cable Decline Benchpress");
        exerciseList.add("Dumbbell Flies");
        exerciseList.add("Cable Flies");
        exerciseList.add("Push Ups");
        exerciseList.add("Decline Push Ups");
        exerciseList.add("Incline Push Ups");
        exerciseList.add("Diamond Push Ups");
        exerciseList.add("Decline Diamond Push Ups");
        exerciseList.add("Incline Diamond Push Ups");
        exerciseList.add("Chest Focused Dips");
        exerciseList.add("Dips");
        exerciseList.add("Tricep Extensions");
        exerciseList.add("TRX Tricep Extensions");
        exerciseList.add("Tricep Overhead Press");

        return exerciseList;
    }

    public static ArrayList<String> getLegsShouldersTraps() {
        ArrayList<String> exerciseList = new ArrayList<>();
        exerciseList.add("Military Press");
        exerciseList.add("Arnold Press");
        exerciseList.add("Lateral Raises");
        exerciseList.add("Cable Lateral Raises");
        exerciseList.add("Bent-Over Lateral Raises");
        exerciseList.add("Bent-Over Cable Lateral Raises");
        exerciseList.add("Face Pulls Lying Down");
        exerciseList.add("High Cable Rear Delt Fly");
        exerciseList.add("Front Raises");
        exerciseList.add("Overhead Trap Raises");
        exerciseList.add("Farmers Walk Straps");
        exerciseList.add("Barbell Shrugs");
        exerciseList.add("Dumbbell Shrugs");
        exerciseList.add("Rack Pulls");
        exerciseList.add("Kettlebell Swings");
        exerciseList.add("Squats");
        exerciseList.add("Air Squats");
        exerciseList.add("Pistol Squats");
        exerciseList.add("Leg Extensions");
        exerciseList.add("Calf Raises");
        exerciseList.add("Box Jump");

        return exerciseList;
    }

    public static ArrayList<String> getNeckForearms() {
        ArrayList<String> exerciseList = new ArrayList<>();
        exerciseList.add("Front Neck Curl");
        exerciseList.add("Back Neck Curl");
        exerciseList.add("Right Neck Curl");
        exerciseList.add("Left Neck Curl");
        exerciseList.add("Reverse Grip Curls");
        exerciseList.add("Behind-The-Back Barbell Wrist Curls");
        exerciseList.add("Barbell Wrist Curls");
        exerciseList.add("Barbell Reverse Wrist Curls");
        exerciseList.add("Wrist Roller");
        exerciseList.add("Adjustable Hand Grip");
        exerciseList.add("Farmers Walk");

        return exerciseList;
    }

    public static ArrayList<String> getCardioFullbody() {
        ArrayList<String> exerciseList = new ArrayList();
        exerciseList.add("Burpees");
        exerciseList.add("Jumping Jacks");

        return exerciseList;
    }

    public static ArrayList<Exercise> getExercises() { return exercises; }

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
        bodyweightExercises.add("Chest Focused Dips");
        bodyweightExercises.add("Pull Ups");
        bodyweightExercises.add("Chin Ups");
        bodyweightExercises.add("Close Chin Ups");
        bodyweightExercises.add("Box Jump");
        bodyweightExercises.add("Push Ups");
        bodyweightExercises.add("Decline Push Ups");
        bodyweightExercises.add("Incline Push Ups");
        bodyweightExercises.add("Diamond Push Ups");
        bodyweightExercises.add("Decline Diamond Push Ups");
        bodyweightExercises.add("Incline Diamond Push Ups");
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

    //Check if this day in the calendar is checked as done or not
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
        ArrayList<Exercise> markedExercises;

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
                    } catch (Exception e) { }
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
