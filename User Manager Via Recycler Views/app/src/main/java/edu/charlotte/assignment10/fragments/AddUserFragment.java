package edu.charlotte.assignment10.fragments;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.charlotte.assignment10.R;
import edu.charlotte.assignment10.databinding.FragmentAddUserBinding;
import edu.charlotte.assignment10.models.Mood;
import edu.charlotte.assignment10.models.User;

public class AddUserFragment extends Fragment {
    private Mood selectedMood;
    private String selectedAgeRange;

    public AddUserFragment() {
        // Required empty public constructor
    }

    public void setSelectedAgeRange(String selectedAgeRange) {
        this.selectedAgeRange = selectedAgeRange;
    }

    public void setSelectedMood(Mood selectedMood) {
        this.selectedMood = selectedMood;
    }

    FragmentAddUserBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add User");
        if(selectedMood == null) {
            binding.textViewMood.setText("N/A");
            binding.imageViewMood.setVisibility(INVISIBLE);
        } else {
            binding.imageViewMood.setVisibility(VISIBLE);
            binding.textViewMood.setText(selectedMood.getName());
            binding.imageViewMood.setImageResource(selectedMood.getImageResourceId());
        }

        if(selectedAgeRange != null) {
            binding.textViewAgeGroup.setText(selectedAgeRange);
        } else {
            binding.textViewAgeGroup.setText("N/A");
        }

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextText.getText().toString();
                if(name.isEmpty()) {
                    Toast.makeText(getContext(), "Enter valid name!", Toast.LENGTH_SHORT).show();
                } else if(selectedAgeRange == null) {
                    Toast.makeText(getContext(), "Select age range!", Toast.LENGTH_SHORT).show();
                } else if(selectedMood == null) {
                    Toast.makeText(getContext(), "Select mood!", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(name, selectedAgeRange, selectedMood);
                    mListener.submitUser(user);
                }
            }
        });

        binding.buttonSelectAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectAgeRange();
            }
        });

        binding.buttonSelectMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectMood();
            }
        });

    }

    AddUserListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddUserListener) {
            mListener = (AddUserListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AddUserListener");
        }
    }

    public interface AddUserListener {
        void gotoSelectMood();
        void gotoSelectAgeRange();
        void submitUser(User user);
    }
}