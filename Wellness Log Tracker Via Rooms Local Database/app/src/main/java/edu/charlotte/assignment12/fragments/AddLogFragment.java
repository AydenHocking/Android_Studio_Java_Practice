package edu.charlotte.assignment12.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import edu.charlotte.assignment12.R;
import edu.charlotte.assignment12.databinding.FragmentAddLogBinding;
import edu.charlotte.assignment12.models.AppDatabase;
import edu.charlotte.assignment12.models.Quality;
import edu.charlotte.assignment12.models.Wellness;

public class AddLogFragment extends Fragment {
    public AddLogFragment() {
        // Required empty public constructor
    }
    AddLogListener mListener;
    FragmentAddLogBinding binding;
    private Calendar selectedDateTime = null;
    private Double selectedSleepHours;
    private Quality selectedSleepQuality;
    private Double selectedExerciseHours;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddLogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        binding.buttonPickDate.setOnClickListener(v -> showDateTimePicker());
        binding.buttonPickSleepHours.setOnClickListener(v -> mListener.gotoSelectSleepHours());
        binding.buttonPickSleepQuality.setOnClickListener(v -> mListener.gotoSelectSleepQuality());
        binding.buttonPickExerciseHours.setOnClickListener(v -> mListener.gotoSelectExerciseHours());
        binding.buttonCancelLog.setOnClickListener(v -> mListener.onCancelSelection());

        binding.buttonSaveLog.setOnClickListener(v -> {
            String weightStr = binding.editWeight.getText().toString();


            if (selectedSleepHours == null) {
                Toast.makeText(getActivity(), "Select sleep hours!", Toast.LENGTH_SHORT).show();
            } else if (selectedSleepQuality == null) {
                Toast.makeText(getActivity(), "Select sleep quality!", Toast.LENGTH_SHORT).show();
            } else if (selectedExerciseHours == null) {
                Toast.makeText(getActivity(), "Select exercise hours!", Toast.LENGTH_SHORT).show();
            } else if (weightStr.isEmpty()) {
                Toast.makeText(getActivity(), "Select weight!", Toast.LENGTH_SHORT).show();
            } else if (selectedDateTime == null) {
                Toast.makeText(getActivity(), "Select date/time!", Toast.LENGTH_SHORT).show();
            } else {
                double weight = Double.parseDouble(weightStr);
                String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(selectedDateTime.getTime());
                Wellness wellness = new Wellness(
                        dateStr,
                        selectedSleepHours,
                        selectedSleepQuality.getLevel(),
                        selectedExerciseHours,
                        weight
                );

                mListener.onLogAdded(wellness);
            }
        });

        if (selectedSleepHours != null)
            binding.textSleepHours.setText("Sleep Hours: " + selectedSleepHours);
        else
            binding.textSleepHours.setText("Sleep Hours: N/A");

        if (selectedSleepQuality != null)
            binding.textSleepQuality.setText("Sleep Quality: " + selectedSleepQuality);
        else
            binding.textSleepQuality.setText("Sleep Quality: N/A");

        if (selectedExerciseHours != null)
            binding.textExerciseHours.setText("Exercise Hours: " + selectedExerciseHours);
        else
            binding.textExerciseHours.setText("Exercise Hours: N/A");
        if (selectedDateTime != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            String dateStr = sdf.format(selectedDateTime.getTime());
            binding.textDateTime.setText("Date/Time: " + dateStr);
        } else {
            binding.textDateTime.setText("Date/Time: N/A");
        }
    }
    public void setSelectedSleepHours(String hours) {
        this.selectedSleepHours = Double.parseDouble(hours);
    }

    public void setSelectedSleepQuality(Quality quality) {
        this.selectedSleepQuality = quality;
    }

    public void setSelectedExerciseHours(String hours) {
        this.selectedExerciseHours = Double.parseDouble(hours);
    }
    private void showDateTimePicker() {
        final Calendar calendar = Calendar.getInstance();
        if (selectedDateTime == null) {
            selectedDateTime = Calendar.getInstance();
        }
        // Reference: https://developer.android.com/reference/android/app/DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    selectedDateTime.set(Calendar.YEAR, year);
                    selectedDateTime.set(Calendar.MONTH, month);
                    selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    // Reference: https://developer.android.com/reference/android/app/TimePickerDialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                            (timeView, hourOfDay, minute) -> {
                                selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                selectedDateTime.set(Calendar.MINUTE, minute);
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                                String dateStr = sdf.format(selectedDateTime.getTime());
                                binding.textDateTime.setText("Date/Time: " + dateStr);
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            false);
                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddLogListener) {
            mListener = (AddLogListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddLogListener");
        }
    }

    public interface AddLogListener {
        void gotoSelectSleepHours();
        void gotoSelectSleepQuality();
        void gotoSelectExerciseHours();
        void onLogAdded(Wellness log);
        void onCancelSelection();
    }
}