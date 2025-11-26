package com.example.assignment04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment04.databinding.FragmentWelcomeBinding;
/*
Assignment 4
Ayden Hocking
WelcomeFragment.java
 */
public class WelcomeFragment extends Fragment {

    public WelcomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentWelcomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWelcomeBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wListener.gotoCreateUser();
            }
        });
    }
    WelcomeFragmentListener wListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        wListener = (WelcomeFragmentListener) context;
    }

    interface WelcomeFragmentListener {
        void gotoCreateUser();
    }
}