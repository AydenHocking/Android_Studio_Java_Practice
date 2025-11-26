package edu.charlotte.assignment12.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.charlotte.assignment12.R;
import edu.charlotte.assignment12.databinding.FragmentVisualizeProgressBinding;
import edu.charlotte.assignment12.models.AppDatabase;
import edu.charlotte.assignment12.models.Wellness;

public class VisualizeProgressFragment extends Fragment {
    FragmentVisualizeProgressBinding binding;
    VisualizeProgressFragment.VisualizeProgressListener mListener;

    private LineChart chartSleepHours, chartSleepQuality, chartExerciseHours, chartWeight;
    private AppDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVisualizeProgressBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chartSleepHours = view.findViewById(R.id.chartSleepHours);
        chartSleepQuality = view.findViewById(R.id.chartSleepQuality);
        chartExerciseHours = view.findViewById(R.id.chartExerciseHours);
        chartWeight = view.findViewById(R.id.chartWeight);

        db = Room.databaseBuilder(requireContext(), AppDatabase.class, "wellness.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        populateCharts();

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelection();
            }
        });
    }

    // Reference: https://github.com/PhilJay/MPAndroidChart
    private void populateCharts() {
        // Retrieve sorted data from the database
        List<Wellness> logs = db.WellnessDao().getAllSortedByDateAsc();
        // If no data is present return
        if (logs.isEmpty()) return;
        // Create Entries for Each Chart
        List<Entry> sleepHoursEntries = new ArrayList<>();
        List<Entry> sleepQualityEntries = new ArrayList<>();
        List<Entry> exerciseEntries = new ArrayList<>();
        List<Entry> weightEntries = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); // Date String format used to convert to millis

        for (Wellness log : logs) {
            try {
                long time = sdf.parse(log.getDateTime()).getTime(); // Convert to millis
                // Add Entry for Each Chart
                sleepHoursEntries.add(new Entry(time, (float) log.getSleepHours()));
                sleepQualityEntries.add(new Entry(time, (float) log.getSleepQuality()));
                exerciseEntries.add(new Entry(time, (float) log.getExerciseHours()));
                weightEntries.add(new Entry(time, (float) log.getWeight()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Sleep Hours Chart Formatting (Includes X-Axis Label)
        LineDataSet sleepHoursSet = new LineDataSet(sleepHoursEntries, "Sleep Hours"); // Sets Entry Value and Label
        sleepHoursSet.setColor(Color.BLUE); // Sets Line Color
        sleepHoursSet.setCircleColor(Color.BLUE); // Sets Circle Color
        chartSleepHours.setData(new LineData(sleepHoursSet)); // Sets Dataset
        chartSleepHours.getAxisLeft().setAxisMinimum(0f); // Sets Minimum Value
        chartSleepHours.getAxisLeft().setAxisMaximum(16f); // Sets Maximum Value
        chartSleepHours.getAxisRight().setEnabled(false); // Disables Right Axis
        chartSleepHours.getDescription().setEnabled(false); // Disables Description

        ValueFormatter dateFormatter = new ValueFormatter() {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd", Locale.getDefault()); // Sets X-Axis Date Format
            @Override
            public String getFormattedValue(float value) {
                return sdf.format((long) value); // Returns X-Axis Date
            }
        };
        chartSleepHours.getXAxis().setValueFormatter(dateFormatter); // Sets X-Axis Formatter
        chartSleepHours.getXAxis().setGranularity(24 * 60 * 60 * 1000f); // Sets One Day Format
        chartSleepHours.invalidate(); // Refreshes Chart

        // Sleep Quality Chart Formatting
        LineDataSet sleepQualitySet = new LineDataSet(sleepQualityEntries, "Sleep Quality");
        sleepQualitySet.setColor(Color.MAGENTA);
        sleepQualitySet.setCircleColor(Color.MAGENTA);
        chartSleepQuality.setData(new LineData(sleepQualitySet));
        chartSleepQuality.getAxisLeft().setAxisMinimum(0f);
        chartSleepQuality.getAxisLeft().setAxisMaximum(6f);
        chartSleepQuality.getAxisRight().setEnabled(false);
        chartSleepQuality.getXAxis().setDrawGridLines(true); // Shows Vert. Lines
        chartSleepQuality.getXAxis().setDrawLabels(false); // Disables Labels (Already shown on top graph)
        chartSleepQuality.getDescription().setEnabled(false);
        chartSleepQuality.invalidate();

        // Exercise Hours Chart Formatting
        LineDataSet exerciseSet = new LineDataSet(exerciseEntries, "Exercise Hours");
        exerciseSet.setColor(Color.GREEN);
        exerciseSet.setCircleColor(Color.GREEN);
        chartExerciseHours.setData(new LineData(exerciseSet));
        chartExerciseHours.getAxisLeft().setAxisMinimum(0f);
        chartExerciseHours.getAxisLeft().setAxisMaximum(16f);
        chartExerciseHours.getAxisRight().setEnabled(false);
        chartExerciseHours.getXAxis().setDrawGridLines(true);
        chartExerciseHours.getXAxis().setDrawLabels(false);
        chartExerciseHours.getDescription().setEnabled(false);
        chartSleepHours.invalidate();

        // Weight Chart Formatting
        LineDataSet weightSet = new LineDataSet(weightEntries, "Weight");
        weightSet.setColor(Color.RED);
        weightSet.setCircleColor(Color.RED);
        chartWeight.setData(new LineData(weightSet));
        chartWeight.getAxisLeft().setAxisMinimum(0f);
        chartWeight.getAxisLeft().setAxisMaximum(300f);
        chartWeight.getAxisRight().setEnabled(false);
        chartWeight.getXAxis().setDrawGridLines(true);
        chartWeight.getXAxis().setDrawLabels(false);
        chartWeight.getDescription().setEnabled(false);
        chartWeight.invalidate();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof VisualizeProgressFragment.VisualizeProgressListener) {
            mListener = (VisualizeProgressFragment.VisualizeProgressListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement VisualizeProgressListener");
        }
    }
    public interface VisualizeProgressListener {
        void onCancelSelection();
    }
}
