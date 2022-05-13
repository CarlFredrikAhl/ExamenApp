package com.example.examenapp;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Exercise {
    String name;
    String date;
    String id;

    private static ArrayList<MySet> sets;

    public Exercise(String name, ArrayList<MySet> sets, String date) {
        this.name = name;
        this.sets = sets;
        this.date = date;
        id = UUID.randomUUID().toString();
    }

    //If no sets is added yet
    public Exercise(String name, String date) {
        this.name = name;
        this.date = date;
        id = UUID.randomUUID().toString();
    }

    public void addSet(MySet set) {
        sets.add(set);
    }

    public void addSets(ArrayList<MySet> sets) {
        this.sets = sets;
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
