package com.example.examenapp;

import java.util.ArrayList;
import java.util.List;

public class Exercise {
    String name;

    private static ArrayList<MySet> sets;

    String date;

    public Exercise(String name, ArrayList<MySet> sets, String date) {
        this.name = name;
        this.sets = sets;
        this.date = date;
    }

    //If no sets is added yet
    public Exercise(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public void addSet(MySet set) {
        sets.add(set);
    }

    //Retrieves the personal record (most weight lifted)
    public static float getPr() {
        float pr = 0;

        for (int i = -1; i < sets.size() - 1; i++) {
            for(int k = 0; k < sets.size(); i++) {
                if(sets.get(i).weight > sets.get(k).weight) {
                    pr = sets.get(i).weight;
                }
            }
        }

        return pr;
    }

    //Retrieves the total weight lifted
    public static float getTotalWeight() {
        float maxWeight = 0;

        for(MySet set : sets) {
            maxWeight+= set.weight;
        }

        return maxWeight;
    }
}
