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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.charlotte.assignment14.R;
import edu.charlotte.assignment14.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;

    public LoginFragment() {
        // Required empty public constructor
    }

    FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoCreateAccount();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.buttonSubmit.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        String emailStr = binding.editTextEmailAddress.getText().toString();
                        String passwordStr = binding.editTextPassword.getText().toString();
                        if (emailStr.isEmpty() || passwordStr.isEmpty()) {
                            Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                        }else{
                            mAuth = FirebaseAuth.getInstance();
                            mAuth.signInWithEmailAndPassword(emailStr,passwordStr)
                                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                                mListener.onLoginSuccess();
                                            } else {
                                                Toast.makeText(getContext(), "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                Log.d("TAG", "onComplete: "+ task.getException().getMessage());
                                            }
                                        }
                                    });
                        }


                    }
                });
            }
        });
    }

    LoginListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (LoginListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement LoginListener");
        }
    }

    public interface LoginListener {
        void onLoginSuccess();
        void gotoCreateAccount();
    }
}