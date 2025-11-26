package com.example.assignment04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.assignment04.databinding.FragmentCreateUserBinding;
/*
Assignment 4
Ayden Hocking
CreateUserFragment.java
 */

public class CreateUserFragment extends Fragment {
    public CreateUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentCreateUserBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("CreateUser");


        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.editTextName.getText().toString();
                String email = binding.editTextTextEmailAddress.getText().toString();
                int checkedDept = binding.radioGroupRole.getCheckedRadioButtonId();

                if(name.isEmpty()){
                    Toast.makeText(getActivity(), "Invalid Name Setup", Toast.LENGTH_SHORT).show();
                }else if (email.isEmpty()) {
                    Toast.makeText(getActivity(), "Invalid Email Setup", Toast.LENGTH_SHORT).show();
                }else if(checkedDept == -1){
                    Toast.makeText(getActivity(), "Select a Role", Toast.LENGTH_SHORT).show();
                }else{
                    RadioButton selectedDept = binding.getRoot().findViewById(checkedDept);
                    String role = selectedDept.getText().toString();
                    User user = new User(name, email, role);
                    cListener.gotoProfile(user);
                }
            }
        });

    }

    CreateUserFragmentListener cListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        cListener = (CreateUserFragmentListener) context;
    }

    interface CreateUserFragmentListener{
        void gotoProfile(User user);
    }
}