package edu.uncc.assignment06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

import edu.uncc.assignment06.databinding.FragmentSelectTaskDateBinding;


public class SelectTaskDateFragment extends Fragment {
    private String selectedDate = null;

    public SelectTaskDateFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSelectTaskDateBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectTaskDateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    private int selectedYear, selectedMonth, selectedDay;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Task Date");

        binding.calendarView.setOnDateChangeListener((CalendarView calendarView, int year, int month, int day) ->{
            selectedYear = year;
            selectedMonth = month;
            selectedDay = day;
            selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", month + 1, day, year);
            binding.textViewDateSelection.setText(selectedDate);
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedDate != null) {
                    Calendar today = Calendar.getInstance();
                    int todayYear = today.get(Calendar.YEAR);
                    int todayMonth = today.get(Calendar.MONTH); // 0-based
                    int todayDay = today.get(Calendar.DAY_OF_MONTH);

                    if (selectedYear > todayYear || (selectedYear == todayYear && selectedMonth > todayMonth) || (selectedYear == todayYear && selectedMonth == todayMonth && selectedDay > todayDay)) {
                        Toast.makeText(getActivity(), "Select a date before today", Toast.LENGTH_SHORT).show();
                    } else {
                        mListener.sendDate(selectedDate);
                    }
                }
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelDate();
            }
        });
    }
    SelectTaskDateFragmentListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectTaskDateFragmentListener) context;
    }

    interface SelectTaskDateFragmentListener {
        void sendDate(String selectedDate);
        void cancelDate();
    }

}