package com.example.examenapp;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public final class Exercises {
    private static ArrayList<Exercise> exercises = new ArrayList<>();

    private static ArrayList<String> allExercises = new ArrayList<>();

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

    public static void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }
}
