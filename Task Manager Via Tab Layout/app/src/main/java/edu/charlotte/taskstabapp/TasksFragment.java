package edu.charlotte.taskstabapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;

import edu.charlotte.taskstabapp.databinding.FragmentTasksBinding;
import edu.charlotte.taskstabapp.models.DataStore;
import edu.charlotte.taskstabapp.models.Task;

public class TasksFragment extends Fragment implements Serializable {

    private static final String ARG_TASKS = "tasks";
    private static final String ARG_PRIORITY = "priority";

    private ArrayList<Task> tasks;
    private String priority;
    private int i = 0;

    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment newInstance(ArrayList<Task> tasks, String priority) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASKS, tasks);
        args.putString(ARG_PRIORITY, priority);
        fragment.setArguments(args);
        return fragment;
    }
    FragmentTasksBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.tasks = (ArrayList<Task>) getArguments().getSerializable(ARG_TASKS);
            this.priority = getArguments().getString(ARG_PRIORITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refresh();
        binding.imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tasks.size() > 0) {
                    i++;
                    if (i >= tasks.size()) {
                        i = 0;
                    }
                    refresh();
                }
            }
        });
        binding.imageViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tasks.size() > 0) {
                    i--;
                    if (i < 0) {
                        i = tasks.size() - 1;
                    }
                    refresh();
                }
            }
        });
    }

    private void refresh(){
        if (tasks.size() == 0) {
            binding.textViewTasksCount.setText("You have 0 tasks");
            binding.cardViewTask.setVisibility(View.INVISIBLE);
        } else {
            binding.textViewTasksCount.setText("You have " + tasks.size() + " tasks");
            binding.cardViewTask.setVisibility(View.VISIBLE);
            Collections.sort(tasks, (taskA, taskB) -> taskB.getDate().compareTo(taskA.getDate()));
            Task currentTask = tasks.get(i);
            binding.textViewTaskName.setText(currentTask.getTitle());
            binding.textViewTaskDate.setText(currentTask.getDate().toString());
            binding.textViewTaskPriority.setText(currentTask.getPriority());
            binding.textViewTaskOutOf.setText("Task " + (i + 1) + " of " + tasks.size());
        }
    }

}
