package com.example.examenapp;

import android.content.Context;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Exercise {
    String name;
    String date;
    String id;

    ArrayList<MySet> sets;

    public Exercise(String name, String date, ArrayList<MySet> sets) {
        this.name = name;
        this.date = date;
        this.sets = sets;
        id = UUID.randomUUID().toString();
    }

    public void addSet(MySet set) {
        sets.add(set);
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

    //Retrieves the max weight lifted
    public float getMaxWeight(Context context, boolean useBodyweight) {
        float maxWeight = sets.get(0).weight;
        if(useBodyweight) {
            maxWeight = sets.get(0).weight + SettingsFragment.getUserWeight(context);
        }

        if(sets.size() > 1) {
            for(int i = 0; i < sets.size(); i++) {
                if(sets.get(i).weight > maxWeight) {
                    if(!useBodyweight) {
                        maxWeight = sets.get(i).weight;
                    } else {
                        maxWeight = sets.get(i).weight + SettingsFragment.getUserWeight(context);
                    }
                }
            }
        }

        return maxWeight;
    }

    //Retrieves the total weight lifted
    public float getTotalWeight(Context context, boolean useBodyweight) {
        float totalWeight = 0;

        for(MySet set : sets) {
            if(!useBodyweight) {
                if(set.reps.equals("Till Failure/Forced Reps/Not Counting")) {
                    totalWeight += set.weight;
                } else {
                    int reps = Integer.parseInt(set.reps);
                    totalWeight += set.weight * reps;
                }
            } else {
                if(set.reps.equals("Till Failure/Forced Reps/Not Counting")) {
                    totalWeight += set.weight + SettingsFragment.getUserWeight(context);
                } else {
                    int reps = Integer.parseInt(set.reps);
                    totalWeight += (set.weight + SettingsFragment.getUserWeight(context)) * reps;
                }
            }
        }

        return totalWeight;
    }
}
