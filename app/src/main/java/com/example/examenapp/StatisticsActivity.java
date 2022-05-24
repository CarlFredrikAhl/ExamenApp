package com.example.examenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    String exerciseName;

    TextView toolbarText;
    TextView maxWeightTextView;
    TextView totalWeightTextView;

    ArrayList<Exercise> statisticsExercises = new ArrayList<>();

    BarChart barChart;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> labelNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        barChart = findViewById(R.id.barChart);

        barEntryArrayList = new ArrayList<>();
        labelNames = new ArrayList<>();
        labelNames.add("Vecka 1");
        labelNames.add("Vecka 2");
        labelNames.add("Vecka 3");
        labelNames.add("Vecka 4");
        labelNames.add("Vecka 5");

        barEntryArrayList = fillTestData();
        BarDataSet  barDataSet = new BarDataSet(barEntryArrayList, "Test Data");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description desc = new Description();
        desc.setText("I will replace this with real data later");
        barChart.setDescription(desc);
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelNames.size());
        xAxis.setLabelRotationAngle(270);
        barChart.animateY(2000);
        barChart.invalidate();

        exerciseName = getIntent().getStringExtra("exercise_name");

        statisticsExercises = Exercises.getMarkedExercises(getApplicationContext(), exerciseName);

        if(statisticsExercises == null) {
            statisticsExercises = new ArrayList<>();
        }

        toolbarText = findViewById(R.id.toolbarText);
        maxWeightTextView = findViewById(R.id.maxWeightTextView);
        totalWeightTextView = findViewById(R.id.totalWeightTextView);

        maxWeightTextView.setText("Max Weight: " + String.valueOf(bestMaxWeight()) + " Kg");
        totalWeightTextView.setText("Total Weight: " + String.valueOf(bestTotalWeight()) + "Kg");
        toolbarText.setText("Statistics - " + exerciseName);
    }

    private ArrayList<BarEntry> fillTestData() {
        ArrayList<Float> testNumbers = new ArrayList<>();
        testNumbers.add(10f);
        testNumbers.add(20f);
        testNumbers.add(30f);
        testNumbers.add(40f);
        testNumbers.add(50f);

        ArrayList<BarEntry> testDataArray = new ArrayList<>();

        for (int i = 0; i < 5; i++)  {
            testDataArray.add(new BarEntry(i, testNumbers.get(i)));
        }

        return testDataArray;
    }

    private float bestMaxWeight() {
        float bestMaxWeight = 0;

        if(statisticsExercises.size() > 1) {
            for(int i = 0; i < statisticsExercises.size(); i++) {
                for(int j = -1; i < statisticsExercises.size() - 1; i++) {
                    if(statisticsExercises.get(i).getPr() > statisticsExercises.get(j).getPr()) {
                        bestMaxWeight = statisticsExercises.get(i).getPr();
                    }
                }
            }

        } else if(statisticsExercises.size() == 1) {
            bestMaxWeight = statisticsExercises.get(0).getPr();
        }

        return bestMaxWeight;
    }

    private float bestTotalWeight() {
        float bestTotalWeight = 0;

        if(statisticsExercises.size() > 1) {
            for(int i = 0; i < statisticsExercises.size(); i++) {
                for(int j = 1; i < statisticsExercises.size() - 1; i++) {
                    if(statisticsExercises.get(i).getTotalWeight() >
                            statisticsExercises.get(j).getTotalWeight()) {
                        bestTotalWeight = statisticsExercises.get(i).getPr();
                    }
                }
            }

        } else if(statisticsExercises.size() == 1){
            bestTotalWeight = statisticsExercises.get(0).getTotalWeight();
        }

        return bestTotalWeight;
    }
}