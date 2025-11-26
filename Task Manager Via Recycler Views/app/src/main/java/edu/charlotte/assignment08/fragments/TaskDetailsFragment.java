package edu.charlotte.assignment08.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import edu.charlotte.assignment08.R;
import edu.charlotte.assignment08.models.Task;


public class TaskDetailsFragment extends Fragment {

    private static final String ARG_TASK = "task";
    private Task task;

    TextView textViewName, textViewCategory, textViewPriority;
    Button buttonBack;
    ImageView buttonDelete;
    TaskDetailsFragmentListener tListener;

    public TaskDetailsFragment() {
        // Required empty public constructor
    }
    public static TaskDetailsFragment newInstance(Task task) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            task = (Task) getArguments().getSerializable(ARG_TASK);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewName = view.findViewById(R.id.textViewName);
        textViewCategory = view.findViewById(R.id.textViewCategory);
        textViewPriority = view.findViewById(R.id.textViewPriority);
        textViewName.setText(task.getName());
        textViewCategory.setText(task.getCategory());
        textViewPriority.setText(task.getPriorityStr());
        buttonDelete = view.findViewById(R.id.imageViewDelete);
        buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(v -> {
            if (tListener != null) {
                tListener.back();
            }
        });
        buttonDelete.setOnClickListener(v -> {
            if (tListener != null) {
                tListener.deleteTaskDetails(task);
            }
        });

    }
    @Override
    public void onAttach(@NonNull android.content.Context context) {
        super.onAttach(context);
        tListener = (TaskDetailsFragmentListener) context;
    }

    public interface TaskDetailsFragmentListener{
        void back();
        void deleteTaskDetails(Task task);
    }
}