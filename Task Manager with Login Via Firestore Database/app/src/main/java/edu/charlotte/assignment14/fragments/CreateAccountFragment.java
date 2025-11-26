package edu.charlotte.assignment14.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.charlotte.assignment14.R;
import edu.charlotte.assignment14.databinding.FragmentCreateAccountBinding;

public class CreateAccountFragment extends Fragment {
    private FirebaseAuth mAuth;

    public CreateAccountFragment() {
        // Required empty public constructor
    }


    FragmentCreateAccountBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoLoginAccount();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailStr = binding.editTextEmailAddress.getText().toString();
                String passwordStr = binding.editTextPassword.getText().toString();
                if (emailStr.isEmpty() || passwordStr.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(emailStr,passwordStr)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                        mListener.onCreateAccountSuccess();
                                    }else {
                                        Toast.makeText(getContext(), "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.d("TAG", "onComplete: "+ task.getException().getMessage());
                                    }
                                }
                            });
                }
            }
        });
    }

    RegisterListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (RegisterListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement RegisterListener");
        }
    }

    public interface RegisterListener {
        void onCreateAccountSuccess();
        void gotoLoginAccount();
    }
}