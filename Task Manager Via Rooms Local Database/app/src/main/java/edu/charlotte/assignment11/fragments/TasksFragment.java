package edu.charlotte.assignment11.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.charlotte.assignment11.R;
import edu.charlotte.assignment11.databinding.FragmentTasksBinding;
import edu.charlotte.assignment11.models.AppDatabase;
import edu.charlotte.assignment11.models.Task;

public class TasksFragment extends Fragment {
    public TasksFragment() {
        // Required empty public constructor
    }

    FragmentTasksBinding binding;
    private AppDatabase db;
    private TaskAdapter adapter;
    private boolean sortedDesc = false;
    private boolean sortedAsc = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = Room.databaseBuilder(requireContext(), AppDatabase.class, "tasks.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        adapter = new TaskAdapter(new ArrayList<>());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        loadTasks();


        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddTask();
            }
        });

        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.taskDAO().deleteAll();
                loadTasks();
            }
        });

        binding.imageViewSortAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortedDesc = true;
                sortedAsc = false;
                binding.textViewSortIndicator.setText("Sort by Priority (DESC)");
                loadTasks();
            }
        });

        binding.imageViewSortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortedAsc = true;
                sortedDesc = false;
                binding.textViewSortIndicator.setText("Sort by Priority (ASC)");
                loadTasks();
            }
        });
    }
    public void loadTasks() {
        List<Task> tasks;
        if (sortedDesc) {
            tasks = db.taskDAO().getAllSortedDesc();
        } else if (sortedAsc) {
            tasks = db.taskDAO().getAllSortedAsc();
        } else {
            tasks = db.taskDAO().getAll();
        }
        adapter.setTasks(tasks);
    }
    TasksListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TasksListener) {
            mListener = (TasksListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement TasksListener");
        }
    }

    public interface TasksListener{
        void gotoAddTask();
        void gotoTaskDetails(Task task);
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
        private List<Task> tasks;

        TaskAdapter(List<Task> tasks) { this.tasks = tasks; }

        void setTasks(List<Task> newTasks) {
            this.tasks = newTasks != null ? newTasks : new ArrayList<>();
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, parent, false);
            return new TaskViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            Task mTask = tasks.get(position);
            holder.name.setText(mTask.getName());
            holder.category.setText(mTask.getCategory());
            holder.priority.setText(mTask.getPriorityName());

            holder.itemView.setOnClickListener(v -> mListener.gotoTaskDetails(mTask));

            holder.delete.setOnClickListener(v -> {
                db.taskDAO().delete(mTask);
                loadTasks();
            });
        }

        @Override
        public int getItemCount() { return tasks != null ? tasks.size() : 0; }

        class TaskViewHolder extends RecyclerView.ViewHolder {
            TextView name, category, priority;
            View delete;
            TaskViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.textViewName);
                category = itemView.findViewById(R.id.textViewCategory);
                priority = itemView.findViewById(R.id.textViewPriority);
                delete = itemView.findViewById(R.id.imageView);
            }
        }
    }

}