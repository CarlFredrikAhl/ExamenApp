package com.example.examenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
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
    ArrayList<BarEntry> barEntryArrayList1;
    ArrayList<BarEntry> barEntryArrayList2;
    ArrayList<String> labelNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        barChart = findViewById(R.id.barChart);

        barEntryArrayList1 = new ArrayList<>();
        barEntryArrayList2 = new ArrayList<>();

        labelNames = new ArrayList<>();
        labelNames.add("Vecka 1");
        labelNames.add("Vecka 2");
        labelNames.add("Vecka 3");
        labelNames.add("Vecka 4");
        labelNames.add("Vecka 5");

        barEntryArrayList1 = fillTestData1();
        barEntryArrayList2 = fillTestData2();

        //Setting the bar data

        BarDataSet  barDataSet1 = new BarDataSet(barEntryArrayList1, "Test Data 1");
        barDataSet1.setColor(Color.YELLOW);

        BarDataSet  barDataSet2 = new BarDataSet(barEntryArrayList2, "Test Data 2");
        barDataSet1.setColor(Color.BLUE);

        BarData barData = new BarData(barDataSet1, barDataSet2);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3f);

        float barSpace = 0.1f;
        float groupSpace = 0.5f;

        barData.setBarWidth(0.15f);
        barChart.getXAxis().setAxisMinimum(0f);
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace)*5);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.groupBars(0f, groupSpace, barSpace);
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

    private ArrayList<BarEntry> fillTestData1() {
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

    private ArrayList<BarEntry> fillTestData2() {
        ArrayList<Float> testNumbers = new ArrayList<>();
        testNumbers.add(6f);
        testNumbers.add(40f);
        testNumbers.add(10f);
        testNumbers.add(4f);
        testNumbers.add(100f);

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