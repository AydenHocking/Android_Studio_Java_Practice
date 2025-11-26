package edu.charlotte.assignment15.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import edu.charlotte.assignment15.Forum;
import edu.charlotte.assignment15.databinding.FragmentCreateForumBinding;

public class CreateForumFragment extends Fragment {

    public CreateForumFragment() {
        // Required empty public constructor
    }

    FragmentCreateForumBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateForumBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonCancelNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelNewForum();
            }
        });

        binding.buttonSubmit.setOnClickListener(v -> {
            String title = binding.editTextTitle.getText().toString();
            String description = binding.editTextDescription.getText().toString();

            if (title.isEmpty()) {
                Toast.makeText(getActivity(), "Enter Name !!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (description.isEmpty()) {
                Toast.makeText(getActivity(), "Enter Description !!", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

            String createdBy = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            String date = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date());
            Forum forum = new Forum(title, createdBy, description, date, userID, 0, new ArrayList<>());

            db.collection("forums")
                    .add(forum)
                    .addOnSuccessListener(documentReference -> {
                        forum.setId(documentReference.getId());
                        mListener.onForumAdded();

                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(getActivity(), "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        });
    }
    AddTaskListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddTaskListener) {
            mListener = (AddTaskListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddTaskListener");
        }
    }

    public interface AddTaskListener {
        void cancelNewForum();
        void onForumAdded();
    }
}