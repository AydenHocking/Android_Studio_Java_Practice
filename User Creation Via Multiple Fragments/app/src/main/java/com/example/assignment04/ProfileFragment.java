package com.example.assignment04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment04.databinding.FragmentProfileBinding;
/*
Assignment 4
Ayden Hocking
ProfileFragment.java
 */

public class ProfileFragment extends Fragment {
    private static final String ARG_USER = "user";
    private User user;
    ProfileFragmentListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }
    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable(ARG_USER);
        }
    }
    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");

        binding.textViewName.setText(user.getName());
        binding.textViewEmail.setText(user.getEmail());
        binding.textViewRole.setText(user.getRole());


        binding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoEditUser(user);
            }
        });
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ProfileFragment.ProfileFragmentListener) context;
    }
    public interface ProfileFragmentListener {
        void gotoEditUser(User user);
    }
    public void updateUser(User updatedUser) {
        this.user = updatedUser;
        binding.textViewName.setText(user.getName());
        binding.textViewEmail.setText(user.getEmail());
        binding.textViewRole.setText(user.getRole());
    }
}