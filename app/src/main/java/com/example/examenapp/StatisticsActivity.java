package com.example.examenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StatisticsActivity extends AppCompatActivity {

    String exerciseName;

    TextView toolbarText;

    Button howToBtn;

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
        howToBtn = findViewById(R.id.howToBtn);
        howToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Launch intent to googles AR Core model viewer
                Intent sceneViewerIntent = new Intent(Intent.ACTION_VIEW);
                Uri intentUri =
                        Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                                .appendQueryParameter("file", "https://github.com/CarlFredrikAhl/ExamenApp/blob/master/circle_crunch_compressed.glb?raw=true")
                                .build();
                sceneViewerIntent.setData(intentUri);
                sceneViewerIntent.setPackage("com.google.ar.core");
                startActivity(sceneViewerIntent);
            }
        });

        barEntryArrayList1 = new ArrayList<>();
        barEntryArrayList2 = new ArrayList<>();
        labelNames = new ArrayList<>();

        exerciseName = getIntent().getStringExtra("exercise_name");

        statisticsExercises = Exercises.getMarkedExercises(getApplicationContext(), exerciseName);

        if(statisticsExercises != null && bestMaxWeight() > 0 && bestTotalWeight() > 0) {
            setUpChart();
        }

        if(statisticsExercises == null) {
            statisticsExercises = new ArrayList<>();
        }

        toolbarText = findViewById(R.id.toolbarText);
        toolbarText.setText("Statistics - " + exerciseName);

        bestMaxWeightWeek();
    }

    private void setUpChart() {
        Calendar calendar = Calendar.getInstance();
        int curWeek = calendar.get(Calendar.WEEK_OF_YEAR) - 1;
        int firstWeek = 1;
        int lastWeek = 52;

        labelNames = new ArrayList<>();

        barEntryArrayList1 = bestMaxWeightWeek();
        barEntryArrayList2 = bestTotalWeightWeek();

        for(int i = firstWeek; i < lastWeek + 1; i++) {
            labelNames.add("Week " + i);
        }

        //Setting the bar data
        BarDataSet  barDataSet1 = new BarDataSet(barEntryArrayList1, "Max Weight Lifted");
        barDataSet1.setColor(Color.YELLOW);

        BarDataSet  barDataSet2 = new BarDataSet(barEntryArrayList2, "Total Weight Lifted");
        barDataSet1.setColor(Color.BLUE);

        BarData barData = new BarData(barDataSet1, barDataSet2);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //Makes the labels be in the right place
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        float groupSpace = 0.14f;
        float barSpace = 0.03f;

        barData.setBarWidth(0.4f);
        barData.setValueTextSize(10f);
        barData.setValueTextColor(Color.GRAY);
        barData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if(value > 0) {
                    return super.getFormattedValue(value);

                } else {
                    return "";
                }
            }
        });

        barChart.getXAxis().setAxisMinimum(0f);
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace)*labelNames.size());
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getAxisLeft().setTextSize(18f);
        barChart.getAxisLeft().setTextColor(Color.BLACK);
        barChart.getAxisLeft().setGridColor(Color.RED);
        barChart.getAxisLeft().setGridLineWidth(1f);
        barChart.getAxisLeft().enableGridDashedLine(10f, 10f, 0f);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setGridColor(Color.RED);

        Description desc = new Description();
        desc.setText("Current week: " + curWeek);

        barChart.setDescription(desc);
        //barChart.getDescription().setEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.groupBars(0f, groupSpace, barSpace);
        barChart.setDragEnabled(true);
        barChart.getData().setHighlightEnabled(false);
        barChart.setVisibleXRangeMaximum(4f);
        barChart.moveViewToX(curWeek - 3);
        barChart.invalidate();
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

    private ArrayList<BarEntry> bestMaxWeightWeek() {
        ArrayList<BarEntry> weeklyMaxWeight = new ArrayList<>();

        //Find which exercises comes from each week
        for(int i = 1; i < 53; i++) {

            ArrayList<Exercise> curWeekExercises = new ArrayList<>();

            for(Exercise exercise : statisticsExercises) {
                if(exercise.getWeekNumber() == i) {
                    curWeekExercises.add(exercise);
                }
            }

            if(curWeekExercises.size() > 0) {
                float maxWeight = curWeekExercises.get(0).getPr();

                for(int k = 0; k < curWeekExercises.size(); k++) {
                    if(curWeekExercises.get(k).getPr() > maxWeight) {
                        maxWeight = curWeekExercises.get(k).getPr();
                    }
                }

                weeklyMaxWeight.add(new BarEntry(i, maxWeight));

            } else {
                weeklyMaxWeight.add(new BarEntry(i, 0f));
            }
        }

        if(!weeklyMaxWeight.contains(1)) {
            weeklyMaxWeight.add(new BarEntry(1, 0f));
        }

        return weeklyMaxWeight;
    }

    private ArrayList<BarEntry> bestTotalWeightWeek() {
        ArrayList<BarEntry> weeklyBestTotalWeight = new ArrayList<>();

        //Find which exercises comes from each week
        for(int i = 1; i < 53; i++) {

            ArrayList<Exercise> curWeekExercises = new ArrayList<>();

            for(Exercise exercise : statisticsExercises) {
                if(exercise.getWeekNumber() == i) {
                    curWeekExercises.add(exercise);
                }
            }

            if(curWeekExercises.size() > 0) {
                float totalWeight = curWeekExercises.get(0).getTotalWeight();

                for(int k = 0; k < curWeekExercises.size(); k++) {
                    if(curWeekExercises.get(k).getTotalWeight() > totalWeight) {
                        totalWeight = curWeekExercises.get(k).getTotalWeight();
                    }
                }

                weeklyBestTotalWeight.add(new BarEntry(i, totalWeight));

            } else {
                weeklyBestTotalWeight.add(new BarEntry(i, 0f));
            }
        }

        if(!weeklyBestTotalWeight.contains(1)) {
            weeklyBestTotalWeight.add(new BarEntry(1, 0f));
        }

        return weeklyBestTotalWeight;
    }

    private float bestMaxWeight() {
        if(statisticsExercises != null) {
            if(statisticsExercises.size() > 0) {
                float bestMaxWeight = statisticsExercises.get(0).getPr();

                if(statisticsExercises.size() > 1) {
                    for(int i = 0; i < statisticsExercises.size(); i++) {
                        if(statisticsExercises.get(i).getPr() > bestMaxWeight) {
                            bestMaxWeight = statisticsExercises.get(i).getPr();
                        }
                    }

                } else if(statisticsExercises.size() == 1) {
                    bestMaxWeight = statisticsExercises.get(0).getPr();
                }

                return bestMaxWeight;

            } else {
                return 0f;
            }

        } else {
            return 0f;
        }
    }

    private float bestTotalWeight() {
        if(statisticsExercises != null) {
            if(statisticsExercises.size() > 0) {
                float bestTotalWeight = statisticsExercises.get(0).getTotalWeight();

                if(statisticsExercises.size() > 1) {
                    for(int i = 0; i < statisticsExercises.size(); i++) {
                        if(statisticsExercises.get(i).getTotalWeight() > bestTotalWeight) {
                            bestTotalWeight = statisticsExercises.get(i).getTotalWeight();
                        }
                    }

                } else if(statisticsExercises.size() == 1){
                    bestTotalWeight = statisticsExercises.get(0).getTotalWeight();
                }

                return bestTotalWeight;

            } else {
                return 0f;
            }

        } else {
            return 0f;
        }
    }
}