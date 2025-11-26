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

import com.example.assignment04.databinding.FragmentEditUserBinding;
/*
Assignment 4
Ayden Hocking
EditUserFragment.java
 */

public class EditUserFragment extends Fragment {
    private static final String ARG_USER = "user";
    private User user;
    public EditUserFragment() {
        // Required empty public constructor
    }
    public static EditUserFragment newInstance(User user) {
        EditUserFragment fragment = new EditUserFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }
    FragmentEditUserBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("EditUser");
        user = (User) getArguments().getSerializable("user");
        binding.editTextName.setText(user.getName());
        binding.editTextTextEmailAddress.setText(user.getEmail());
        String role = user.getRole();
        if (role.equals("Student")) {
            binding.radioGroupRole.check(R.id.radioButtonStudent);
        } else if (role.equals("Employee")) {
            binding.radioGroupRole.check(R.id.radioButtonEmployee);
        } else {
            binding.radioGroupRole.check(R.id.radioButtonOther);
        }
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelEditUser();
            }
        });

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
                    User updateUser = new User(name, email, role);
                    mListener.updateProfileUser(updateUser);
                }
            }
        });

    }

    EditUserFragmentListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (EditUserFragmentListener) context;
    }

    interface EditUserFragmentListener{
        void updateProfileUser(User user);
        void cancelEditUser();
    }
}