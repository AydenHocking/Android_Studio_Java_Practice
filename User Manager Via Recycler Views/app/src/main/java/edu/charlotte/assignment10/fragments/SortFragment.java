package edu.charlotte.assignment10.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.charlotte.assignment10.databinding.FragmentSortBinding;

public class SortFragment extends Fragment {
    FragmentSortBinding binding;

    SortListener mListener;

    public SortFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSortBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle("Select Sort Criteria");

        binding.imageViewNameAsc.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onSortSelected("NAME_ASC");
            }
        });

        binding.imageViewNameDesc.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onSortSelected("NAME_DESC");
            }
        });

        binding.imageViewAgeAsc.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onSortSelected("AGE_ASC");
            }
        });

        binding.imageViewAgeDesc.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onSortSelected("AGE_DESC");
            }
        });

        binding.imageViewFeelingAsc.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onSortSelected("FEELING_ASC");
            }
        });

        binding.imageViewFeelingDesc.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onSortSelected("FEELING_DESC");
            }
        });

        binding.buttonCancel.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onSortCancelled();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SortListener) {
            mListener = (SortListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SortListener");
        }
    }

    public interface SortListener {
        void onSortSelected(String criteria);
        void onSortCancelled();
    }
}