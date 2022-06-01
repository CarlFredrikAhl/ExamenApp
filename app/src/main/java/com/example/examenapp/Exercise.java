package com.example.examenapp;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Exercise {
    String name;
    String date;
    String id;

    ArrayList<MySet> sets;

    public Exercise(String name, ArrayList<MySet> sets, String date) {
        this.name = name;
        this.sets = sets;
        this.date = date;
        id = UUID.randomUUID().toString();
    }

    //If no sets is added yet
    public Exercise(String name, String date, ArrayList<MySet> sets) {
        this.name = name;
        this.date = date;
        this.sets = sets;
        id = UUID.randomUUID().toString();
    }

    public void addSet(MySet set) {
        sets.add(set);
    }

    public void addSets(ArrayList<MySet> sets) {
        this.sets = sets;
    }

    public int getWeekNumber() {
        Date exerciseDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        try {
            exerciseDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(exerciseDate);
        int exerciseWeek = calendar.get(Calendar.WEEK_OF_YEAR) - 1;

        return exerciseWeek;
    }

    //Retrieves the personal record (most weight lifted)
    public float getPr() {
        float pr = sets.get(0).weight;

        if(sets.size() > 1) {
            for(int i = 0; i < sets.size(); i++) {
                if(sets.get(i).weight > pr) {
                    pr = sets.get(i).weight;
                }
            }
        }

        return pr;
    }

    //Retrieves the total weight lifted
    public float getTotalWeight() {
        float totalWeight = 0;

        for(MySet set : sets) {
            if(set.reps.equals("Till Failure/Not Counting")) {
                totalWeight += set.weight;

            } else {
                int reps = Integer.parseInt(set.reps);
                totalWeight += set.weight * reps;
            }
        }

        return totalWeight;
    }
}
