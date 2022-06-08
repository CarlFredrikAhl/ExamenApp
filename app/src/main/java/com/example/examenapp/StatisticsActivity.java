package com.example.examenapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class StatisticsActivity extends AppCompatActivity {
    final String START_LINK = "https://github.com/CarlFredrikAhl/ExamenApp/blob/master/";
    final String END_LINK = "_compressed.glb?raw=true";
    String modelLink = "";
    String fullLink = "";
    String exerciseName;

    ArrayList<Exercise> statisticsExercises = new ArrayList<>();
    ArrayList<BarEntry> barEntryArrayList1;
    ArrayList<BarEntry> barEntryArrayList2;
    ArrayList<String> labelNames;

    boolean hasModel = true;
    boolean useBodyweight = false;
    boolean clickedNotNow = false;

    TextView toolbarText;

    Button howToBtn;

    CheckBox useBodyweightCheck;

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        exerciseName = getIntent().getStringExtra("exercise_name");
        useBodyweight = getIntent().getBooleanExtra("use_bodyweight", false);

        chooseModel();

        useBodyweightCheck = findViewById(R.id.useBodyWeightCheck);
        useBodyweightCheck.setChecked(useBodyweight);
        useBodyweightCheck.setOnCheckedChangeListener((compoundButton, checked) -> {
            //If user has entered a weight
            if(SettingsFragment.getUserWeight(getApplicationContext()) > 0) {
                //Reload the activity
                Intent intent = new Intent(StatisticsActivity.this, StatisticsActivity.class);
                intent.putExtra("exercise_name", exerciseName);
                if(checked) {
                    intent.putExtra("use_bodyweight", true);
                } else {
                    intent.putExtra("use_bodyweight", false);
                }
                startActivity(intent);

            } else {
                if(!clickedNotNow) {
                    //AlertDialog and able to mark as done exercises
                    AlertDialog.Builder alert = new AlertDialog.Builder(StatisticsActivity.this);
                    alert.setTitle("You have not entered your weight");
                    alert.setMessage("In order to see this data you need to enter a weight in the settings");
                    alert.setPositiveButton("Go to settings", (dialogInterface, i) -> {
                        Intent intent = new Intent(StatisticsActivity.this, MainActivity.class);
                        intent.putExtra("go_to_settings", true);
                        startActivity(intent);
                    });
                    alert.setNegativeButton("Not now", (dialogInterface, i) -> {
                        clickedNotNow = true;
                        useBodyweightCheck.setChecked(false);
                        clickedNotNow = false;
                    });
                    alert.create().show();
                }
            }
        });

        barChart = findViewById(R.id.barChart);
        howToBtn = findViewById(R.id.howToBtn);
        howToBtn.setOnClickListener(view -> {
            //Launch intent to googles AR Core model viewer
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri intentUri =
                    Uri.parse("https://arvr.google.com/scene-viewer/1.0").buildUpon()
                            .appendQueryParameter("file", fullLink)
                            .build();
            intent.setData(intentUri);
            intent.setPackage("com.google.ar.core");
            startActivity(intent);
        });

        if(!hasModel) {
            howToBtn.setEnabled(false);
            howToBtn.setVisibility(View.INVISIBLE);
        }

        barEntryArrayList1 = new ArrayList<>();
        barEntryArrayList2 = new ArrayList<>();
        labelNames = new ArrayList<>();

        statisticsExercises = Exercises.getMarkedExercises(getApplicationContext(), exerciseName);

        useBodyweightData();

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StatisticsActivity.this, MainActivity.class);
        intent.putExtra("go_to_fitness", true);
        startActivity(intent);
    }

    private String chooseModel() {
        switch (exerciseName) {
            case "Air Squats":
                modelLink = "air_squats";
                break;
            case "Squats":
                modelLink = "back_squats";
                break;
            case "Dumbbell Curls":
                modelLink = "bicep_curls";
                break;
            case "Bicycle Crunches":
                modelLink = "bicycle_crunch";
                break;
            case "Box Jump":
                modelLink = "box_jump";
                break;
            case "Burpees":
                modelLink = "burpees";
                break;
            case "Circle Crunches":
                modelLink = "circle_crunch";
                break;
            case "Front Raises":
                modelLink = "front_raises";
                break;
            case "Jumping Jacks":
                modelLink = "jumping_jacks";
                break;
            case "Kettlebell Swings":
                modelLink = "kettlebell_swing";
                break;
            case "Pistol Squats":
                modelLink = "pistol_squat";
                break;
            case "Plank":
                modelLink = "plank";
                break;
            case "Push Ups":
                modelLink = "push_ups";
                break;
            case "Sit Ups":
                modelLink = "situps";
                break;
            default:
                modelLink = "null";
        }

        if(modelLink.equals("null")) {
            hasModel = false;
        }

        fullLink = START_LINK + modelLink + END_LINK;
        return fullLink;
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

        BarDataSet barDataSet1 = new BarDataSet(barEntryArrayList1, "Max Weight Lifted");
        barDataSet1.setColor(Color.YELLOW);

        BarDataSet  barDataSet2;
        if(exerciseName.equals("Plank") == false) {
            barDataSet2 = new BarDataSet(barEntryArrayList2, "Total Weight Lifted");

        } else {
            barDataSet2 = new BarDataSet(barEntryArrayList2, "Total Volume (Weight x Time)");
        }
        barDataSet1.setColor(Color.BLUE);

        BarData barData = new BarData(barDataSet1, barDataSet2);
        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelNames));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //Puts the labels be in the right place
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        float groupSpace = 0.14f;
        float barSpace = 0.03f;

        barData.setBarWidth(0.4f);
        barData.setValueTextSize(10f);
        barData.setValueTextColor(Color.GRAY);

        //This makes sure that the value 0 wont be written out
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
        barChart.setScaleEnabled(false);
        barChart.groupBars(0f, groupSpace, barSpace);
        barChart.setDragEnabled(true);
        barChart.getData().setHighlightEnabled(false);
        barChart.setVisibleXRangeMaximum(4f);
        barChart.moveViewToX(curWeek - 3);
        barChart.invalidate();
    }

    //This calculates the max weight of the specific exercise for each week
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
                if(!useBodyweight) {
                    float maxWeight = curWeekExercises.get(0).getMaxWeight(getApplicationContext(), false);

                    for(int k = 0; k < curWeekExercises.size(); k++) {
                        if(curWeekExercises.get(k).getMaxWeight(getApplicationContext(), false) > maxWeight) {
                            maxWeight = curWeekExercises.get(k).getMaxWeight(getApplicationContext(), false);
                        }
                    }

                    weeklyMaxWeight.add(new BarEntry(i, maxWeight));
                } else {
                    float maxWeight = curWeekExercises.get(0).getMaxWeight(getApplicationContext(), true);

                    for(int k = 0; k < curWeekExercises.size(); k++) {
                        if(curWeekExercises.get(k).getMaxWeight(getApplicationContext(), true) > maxWeight) {
                            maxWeight = curWeekExercises.get(k).getMaxWeight(getApplicationContext(), true);
                        }
                    }

                    weeklyMaxWeight.add(new BarEntry(i, maxWeight));
                }
            } else {
                weeklyMaxWeight.add(new BarEntry(i, 0f));
            }
        }

        if(!weeklyMaxWeight.contains(1)) {
            weeklyMaxWeight.add(new BarEntry(1, 0f));
        }

        return weeklyMaxWeight;
    }

    //This calculates the best total weight of the specific exercise for each week
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
                if(!useBodyweight) {
                    float totalWeight = curWeekExercises.get(0).getTotalWeight(getApplicationContext(), false);

                    for(int k = 0; k < curWeekExercises.size(); k++) {
                        if(curWeekExercises.get(k).getTotalWeight(getApplicationContext(),
                                false) > totalWeight) {
                            totalWeight = curWeekExercises.get(k).getTotalWeight(getApplicationContext(),
                                    false);
                        }
                    }

                    weeklyBestTotalWeight.add(new BarEntry(i, totalWeight));
                } else {
                    float totalWeight = curWeekExercises.get(0).getTotalWeight(getApplicationContext(), true);

                    for(int k = 0; k < curWeekExercises.size(); k++) {
                        if(curWeekExercises.get(k).getTotalWeight(getApplicationContext(),
                                true) > totalWeight) {
                            totalWeight = curWeekExercises.get(k).getTotalWeight(getApplicationContext(),
                                    true);
                        }
                    }

                    weeklyBestTotalWeight.add(new BarEntry(i, totalWeight));
                }
            } else {
                weeklyBestTotalWeight.add(new BarEntry(i, 0f));
            }
        }

        if(!weeklyBestTotalWeight.contains(1)) {
            weeklyBestTotalWeight.add(new BarEntry(1, 0f));
        }

        return weeklyBestTotalWeight;
    }

    //This calculates the max weight of the specific exercise
    private float bestMaxWeight() {
        if(statisticsExercises != null) {
            if(statisticsExercises.size() > 0) {
                if(!useBodyweight) {
                    float bestMaxWeight = statisticsExercises.get(0).getMaxWeight(getApplicationContext(),
                            false);

                    if(statisticsExercises.size() > 1) {
                        for(int i = 0; i < statisticsExercises.size(); i++) {
                            if(statisticsExercises.get(i).getMaxWeight(getApplicationContext(),
                                    false) > bestMaxWeight) {
                                bestMaxWeight = statisticsExercises.get(i).getMaxWeight(getApplicationContext(),
                                        false);
                            }
                        }
                    } else if(statisticsExercises.size() == 1) {
                        bestMaxWeight = statisticsExercises.get(0).getMaxWeight(getApplicationContext(), false);
                    }

                    return bestMaxWeight;
                } else {
                    float bestMaxWeight = statisticsExercises.get(0).getMaxWeight(getApplicationContext(),
                            true);

                    if(statisticsExercises.size() > 1) {
                        for(int i = 0; i < statisticsExercises.size(); i++) {
                            if(statisticsExercises.get(i).getMaxWeight(getApplicationContext(),
                                    true) > bestMaxWeight) {
                                bestMaxWeight = statisticsExercises.get(i).getMaxWeight(getApplicationContext(),
                                        true);
                            }
                        }
                    } else if(statisticsExercises.size() == 1) {
                        bestMaxWeight = statisticsExercises.get(0).getMaxWeight(getApplicationContext(), true);
                    }

                    return bestMaxWeight;
                }
            } else {
                return 0f;
            }
        } else {
            return 0f;
        }
    }

    //This calculates the best total weight of the specific exercise
    private float bestTotalWeight() {
        if(statisticsExercises != null) {
            if(statisticsExercises.size() > 0) {
                if(!useBodyweight) {
                    float bestTotalWeight = statisticsExercises.get(0).getTotalWeight(getApplicationContext(),
                            false);

                    if(statisticsExercises.size() > 1) {
                        for(int i = 0; i < statisticsExercises.size(); i++) {
                            if(statisticsExercises.get(i).getTotalWeight(getApplicationContext(),
                                    false) > bestTotalWeight) {
                                bestTotalWeight = statisticsExercises.get(i).getTotalWeight(getApplicationContext(),
                                        false);
                            }
                        }
                    } else if(statisticsExercises.size() == 1){
                        bestTotalWeight = statisticsExercises.get(0).getTotalWeight(getApplicationContext(),
                                false);
                    }

                    return bestTotalWeight;
                } else {
                    float bestTotalWeight = statisticsExercises.get(0).getTotalWeight(getApplicationContext(),
                            true);

                    if(statisticsExercises.size() > 1) {
                        for(int i = 0; i < statisticsExercises.size(); i++) {
                            if(statisticsExercises.get(i).getTotalWeight(getApplicationContext(),
                                    true) > bestTotalWeight) {
                                bestTotalWeight = statisticsExercises.get(i).getTotalWeight(getApplicationContext(),
                                        true);
                            }
                        }
                    } else if(statisticsExercises.size() == 1){
                        bestTotalWeight = statisticsExercises.get(0).getTotalWeight(getApplicationContext(),
                                true);
                    }

                    return bestTotalWeight;
                }
            } else {
                return 0f;
            }

        } else {
            return 0f;
        }
    }

    private void useBodyweightData() {
        if(statisticsExercises != null) {
            for(String name : Exercises.getBodyweightExercises()) {
                if(exerciseName.equals(name)) {
                    //The users weight matters in this exercise and there is statistics data
                    if(statisticsExercises.size() > 0) {
                        useBodyweightCheck.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }
}