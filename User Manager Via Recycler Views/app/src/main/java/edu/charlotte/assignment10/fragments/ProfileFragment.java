package edu.charlotte.assignment10.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.charlotte.assignment10.R;
import edu.charlotte.assignment10.databinding.FragmentProfileBinding;
import edu.charlotte.assignment10.models.User;

public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM_USER = "ARG_PARAM_USER";
    private User mUser;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = (User) getArguments().getSerializable(ARG_PARAM_USER);
        }
    }

    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Profile");
        binding.textViewUserName.setText(mUser.getName());
        binding.textViewUserAgeGroup.setText(mUser.getAgeGroup());
        binding.textViewUserMood.setText(mUser.getMood().getName());
        binding.imageViewMood.setImageResource(mUser.getMood().getImageResourceId());

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBackFromProfile();
            }
        });
    }

    ProfileListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ProfileListener) {
            mListener = (ProfileListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ProfileListener");
        }
    }

    public interface ProfileListener{
        void onBackFromProfile();
    }
}