package edu.uncc.assignment06;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

import edu.uncc.assignment06.databinding.FragmentTasksBinding;


public class TasksFragment extends Fragment {

    private static final String ARG_TASKS = "tasks";
    private ArrayList<Task> tasks;
    private int i = 0;


    public TasksFragment() {
        // Required empty public constructor
    }
    public static TasksFragment newInstance(ArrayList<Task> tasks){
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASKS, tasks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tasks = (ArrayList<Task>) getArguments().getSerializable(ARG_TASKS);
        }
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
    }

    FragmentTasksBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Tasks");

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
        binding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tasks.size() > 0){
                    tasks.remove(i);
                    if (i >= tasks.size()){
                        i = tasks.size() - 1;
                    }
                    refresh();
                }
            }
        });
        binding.buttonCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tListener.gotoCreateTask();
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
            Collections.sort(tasks, (taskA, taskB) -> taskB.date.compareTo(taskA.date));
            Task currentTask = tasks.get(i);
            binding.textViewTaskName.setText(currentTask.name);
            binding.textViewTaskDate.setText(currentTask.date.toString());
            binding.textViewTaskPriority.setText(currentTask.priority);
            binding.textViewTaskOutOf.setText("Task " + (i + 1) + " of " + tasks.size());
        }
    }
    TasksFragmentListener tListener;

    @Override
    public void onAttach(@NonNull android.content.Context context) {
        super.onAttach(context);
        tListener = (TasksFragmentListener) context;
    }

    interface TasksFragmentListener {
        void gotoCreateTask();
    }
}