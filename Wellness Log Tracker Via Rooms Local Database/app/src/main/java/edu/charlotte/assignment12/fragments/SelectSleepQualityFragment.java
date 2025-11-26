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

import edu.charlotte.assignment12.R;
import edu.charlotte.assignment12.databinding.FragmentSelectSleepQualityBinding;
import edu.charlotte.assignment12.models.Data;
import edu.charlotte.assignment12.models.Quality;

public class SelectSleepQualityFragment extends Fragment {

    public SelectSleepQualityFragment() {
        // Required empty public constructor
    }

    FragmentSelectSleepQualityBinding binding;
    ArrayAdapter<Quality> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectSleepQualityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, Data.qualities);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onQualitySelected(adapter.getItem(position));
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelection();
            }
        });

    }

    SelectSleepQualityListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectSleepQualityListener) {
            mListener = (SelectSleepQualityListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectSleepQualityListener");
        }
    }

    public interface SelectSleepQualityListener {
        void onQualitySelected(Quality quality);
        void onCancelSelection();
    }

}