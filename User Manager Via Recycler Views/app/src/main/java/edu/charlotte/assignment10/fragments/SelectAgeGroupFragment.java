package edu.charlotte.assignment10.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import edu.charlotte.assignment10.R;
import edu.charlotte.assignment10.databinding.FragmentSelectAgeGroupBinding;
import edu.charlotte.assignment10.databinding.FragmentSelectMoodBinding;
import edu.charlotte.assignment10.models.Data;

public class SelectAgeGroupFragment extends Fragment {
    String[] ageGroups = Data.getAgeGroups();

    public SelectAgeGroupFragment() {
        // Required empty public constructor
    }

    FragmentSelectAgeGroupBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectAgeGroupBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Age Group");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, ageGroups);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedAge = ageGroups[position];
            if (mListener != null) {
                mListener.onAgeGroupSelected(selectedAge);
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAgeGroupSelectionCancelled();
            }
        });
    }


    SelectAgeGroupListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectAgeGroupListener) {
            mListener = (SelectAgeGroupListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectAgeGroupListener");
        }
    }

    public interface SelectAgeGroupListener {
        void onAgeGroupSelected(String ageGroup);
        void onAgeGroupSelectionCancelled();
    }


}