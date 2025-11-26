package edu.charlotte.assignment08.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.charlotte.assignment08.R;
import edu.charlotte.assignment08.models.Task;


public class TasksFragment extends Fragment {

    TasksFragmentListener tListener;
    RecyclerView recyclerView;
    TasksRecyclerViewAdapter adapter;
    Button buttonClearAll, buttonAddNew;
    TextView textViewSortIndicator;
    ImageView imageViewSortAsc, imageViewSortDesc;
    LinearLayoutManager layoutManager;


    public TasksFragment() {
        // Required empty public constructor
    }

    public static TasksFragment newInstance() {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof TasksFragmentListener) {
            tListener = (TasksFragmentListener) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        buttonClearAll = view.findViewById(R.id.buttonClearAll);
        buttonAddNew = view.findViewById(R.id.buttonAddNew);
        textViewSortIndicator = view.findViewById(R.id.textViewSortIndicator);
        imageViewSortAsc = view.findViewById(R.id.imageViewSortAsc);
        imageViewSortDesc = view.findViewById(R.id.imageViewSortDesc);
        textViewSortIndicator.setText("Sort by Priority ()");

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Task> tasks = tListener != null ? tListener.getAllTasks() : new ArrayList<>();

        adapter = new TasksRecyclerViewAdapter(tasks, tListener);
        recyclerView.setAdapter(adapter);

        buttonClearAll.setOnClickListener(v -> {
            if (tListener != null) {
                tListener.clearAllTasks();
                // Found at https://stackoverflow.com/questions/3669325/notifydatasetchanged-example
                adapter.notifyDataSetChanged();
                //
            }
        });
        buttonAddNew.setOnClickListener(v -> {
            if (tListener != null) {
                tListener.gotoAddTask();
            }
        });
        imageViewSortAsc.setOnClickListener(v -> {
            if (tListener != null) {
                tListener.sortTasksAscending();
                // Found at https://stackoverflow.com/questions/3669325/notifydatasetchanged-example
                adapter.notifyDataSetChanged();
                //
                textViewSortIndicator.setText("Sort by Priority (ASC)");
            }
        });
        imageViewSortDesc.setOnClickListener(v -> {
            if (tListener != null) {
                tListener.sortTasksDescending();
                // Found at https://stackoverflow.com/questions/3669325/notifydatasetchanged-example
                adapter.notifyDataSetChanged();
                //
                textViewSortIndicator.setText("Sort by Priority (DESC)");
            }
        });

    }
    private class TasksRecyclerViewAdapter extends RecyclerView.Adapter<TasksRecyclerViewAdapter.TaskViewHolder> {

        ArrayList<Task> tasks;
        TasksFragmentListener tListener;

        public TasksRecyclerViewAdapter(ArrayList<Task> tasks, TasksFragmentListener tListener) {
            this.tasks = tasks;
            this.tListener = tListener;

        }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);
            TaskViewHolder taskViewHolder = new TaskViewHolder(view, tListener);
            return taskViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            Task task = tasks.get(position);
            holder.textViewName.setText(task.getName());
            holder.textViewCategory.setText(task.getCategory());
            holder.textViewPriority.setText(task.getPriorityStr());

            holder.itemView.setOnClickListener(v -> {
                if (tListener != null) {
                    tListener.gotoTaskDetails(task);
                }
            });
            holder.imageViewDelete.setOnClickListener(v -> {
                if (tListener != null) {
                    tListener.deleteTask(task);
                    tasks.remove(task);
                    // Found at https://stackoverflow.com/questions/3669325/notifydatasetchanged-example
                    notifyDataSetChanged();
                    //
                }

            });
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }
        public class TaskViewHolder extends RecyclerView.ViewHolder {
            TextView textViewName, textViewCategory, textViewPriority;
            View imageViewDelete;

            public TaskViewHolder(@NonNull View itemView, TasksFragmentListener tListener) {
                super(itemView);
                textViewName = itemView.findViewById(R.id.textViewName);
                textViewCategory = itemView.findViewById(R.id.textViewCategory);
                textViewPriority = itemView.findViewById(R.id.textViewPriority);
                imageViewDelete = itemView.findViewById(R.id.imageView);
            }
        }
    }

    public interface TasksFragmentListener {
        ArrayList<Task> getAllTasks();
        void clearAllTasks();
        void gotoAddTask();
        void gotoTaskDetails(Task task);
        void deleteTask(Task task);
        void sortTasksAscending();
        void sortTasksDescending();
    }
}