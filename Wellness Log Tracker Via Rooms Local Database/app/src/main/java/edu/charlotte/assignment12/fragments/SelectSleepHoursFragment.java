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

import edu.charlotte.assignment12.databinding.FragmentSelectSleepHoursBinding;


public class SelectSleepHoursFragment extends Fragment {
    public SelectSleepHoursFragment() {
        // Required empty public constructor
    }
    FragmentSelectSleepHoursBinding binding;
    ArrayAdapter<String> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectSleepHoursBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, Data.sleeps);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onSleepHoursSelected(adapter.getItem(position));
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelection();
            }
        });
    }
    SelectSleepHoursListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectSleepHoursListener) {
            mListener = (SelectSleepHoursListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectSleepHoursListener");
        }
    }

    public interface SelectSleepHoursListener {
        void onSleepHoursSelected(String sleeps);
        void onCancelSelection();
    }

}