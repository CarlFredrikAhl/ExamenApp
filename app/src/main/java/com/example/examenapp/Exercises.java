package com.example.examenapp;

import java.util.ArrayList;

public final class Exercises {
    private static ArrayList<String> exercises = new ArrayList<>();
    private static ArrayList<String> chosenExercises = new ArrayList<>();

    public static ArrayList<String> getAllExercises() {
        exercises.add("Dumbbell Incline Benchpress");
        exercises.add("Dumbbell Benchpress");
        exercises.add("Dumbell Flies");
        exercises.add("Cable Flies");
        exercises.add("Barbell Curls");
        exercises.add("Barbell Benchpress");
        exercises.add("Preacher Curls");
        exercises.add("Spider Curls");
        exercises.add("Reverse Grip Curls");
        exercises.add("Neck Curls");
        exercises.add("Military Press");
        exercises.add("Squat");
        exercises.add("Leg Extensions");
        exercises.add("Calf Raises");
        exercises.add("Dips");
        exercises.add("Tricep Extensions");
        exercises.add("Tricep Overhead Press");
        exercises.add("Pull Ups");
        exercises.add("Chins");
        exercises.add("Push Ups");
        exercises.add("Lat Pulldowns");
        exercises.add("Barbell Incline Benchpress");
        exercises.add("Landmine Rows");
        exercises.add("One arm rows");
        exercises.add("Farmers Walk");
        exercises.add("Shrugs");
        exercises.add("Rack Pulls");
        exercises.add("Dumbbell Curls");

        return exercises;
    }

    public static ArrayList<String> getFirstFiveExercises() {
        exercises.add("Barbell Benchpress");
        exercises.add("Barbell Incline Benchpress");
        exercises.add("Dumbbell Benchpress");
        exercises.add("Dumbbell Incline Benchpress");
        exercises.add("Dumbell Flies");

        return exercises;
    }

    public static ArrayList<String> getExercises() {
        return chosenExercises;
    }

    public static void addExercise(String exercise) {
        chosenExercises.add(exercise);
    }
}
