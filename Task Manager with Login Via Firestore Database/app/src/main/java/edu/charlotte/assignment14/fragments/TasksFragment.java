package edu.charlotte.assignment14.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import edu.charlotte.assignment14.R;
import edu.charlotte.assignment14.databinding.FragmentTasksBinding;
import edu.charlotte.assignment14.databinding.TaskListItemBinding;
import edu.charlotte.assignment14.models.Priority;
import edu.charlotte.assignment14.models.Task;

public class TasksFragment extends Fragment {
    private FirebaseAuth mAuth;

    public TasksFragment() {
        // Required empty public constructor
    }

    FragmentTasksBinding binding;
    TasksAdapter adapter;
    ArrayList<Task> tasks = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoAddTask();
            }
        });
        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSignout();
            }
        });
        adapter = new TasksAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        getData();
    }

    class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder>{
        @NonNull
        @Override
        public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TaskListItemBinding itemBinding = TaskListItemBinding.inflate(getLayoutInflater(), parent, false);
            return new TaskViewHolder(itemBinding);
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }

        @Override
        public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
            holder.bind(tasks.get(position));
        }

        class TaskViewHolder extends RecyclerView.ViewHolder{
            TaskListItemBinding itemBinding;
            public TaskViewHolder(TaskListItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void bind(Task task){
                itemBinding.textViewName.setText(task.getName());
                itemBinding.textViewCategory.setText(task.getCategory());
                if (task.getPriority() != null) {
                    itemBinding.textViewPriority.setText(task.getPriority().getName());
                } else {
                    itemBinding.textViewPriority.setText("");
                }                itemBinding.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.deleteTask(task);
                    }
                });
            }
        }
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
        void onSignout();
        void deleteTask(Task task);
    }
    private void getData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("tasks")
                .whereEqualTo("userID", userID)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        Log.d("TAG", "onSuccess: " + value.size());
                        tasks.clear();
                        for (QueryDocumentSnapshot document : value) {

                            Task task = document.toObject(Task.class);
                            task.setId(document.getId());
                            tasks.add(task);
                            Log.d("TAG", document.getId() + " => " + document.getData());
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}