package com.example.examenapp;

import java.lang.reflect.Array;
import java.util.ArrayList;
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


    //Retrieves the personal record (most weight lifted)
    public float getPr() {
        float pr = sets.get(0).weight;

        if(sets.size() > 1) {
            for (int i = 0; i < sets.size(); i++) {
                for(int k = 1; k <= (sets.size() - i); k++) {
                    if(sets.get(k - 1).weight > sets.get(k).weight) {
                        pr = sets.get(k - 1).weight;
                    }
                }
            }
        }

        return pr;
    }

    //Retrieves the total weight lifted
    public float getTotalWeight() {
        float totalWeight = 0;

        for(MySet set : sets) {
            totalWeight+= set.weight;
        }

        return totalWeight;
    }
}
