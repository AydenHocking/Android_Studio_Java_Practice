package edu.charlotte.assignment12.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import edu.charlotte.assignment12.models.Data;

import edu.charlotte.assignment12.databinding.FragmentSelectExerciseHoursBinding;


public class SelectExerciseHoursFragment extends Fragment {
    public SelectExerciseHoursFragment() {
        // Required empty public constructor
    }
    FragmentSelectExerciseHoursBinding binding;
    ArrayAdapter<String> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectExerciseHoursBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, Data.exercises);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onExerciseHoursSelected(adapter.getItem(position));
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelection();
            }
        });
    }
    SelectExerciseHoursListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectExerciseHoursListener) {
            mListener = (SelectExerciseHoursListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectExerciseHoursListener");
        }
    }

    public interface SelectExerciseHoursListener {
        void onExerciseHoursSelected(String exercises);
        void onCancelSelection();
    }

}