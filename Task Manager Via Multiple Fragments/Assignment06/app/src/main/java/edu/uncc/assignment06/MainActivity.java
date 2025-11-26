package edu.uncc.assignment06;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.uncc.assignment06.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements CreateTaskFragment.CreateTaskFragmentListener, TasksFragment.TasksFragmentListener, SelectTaskDateFragment.SelectTaskDateFragmentListener {
    ActivityMainBinding binding;
    ArrayList<Task> tasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main, TasksFragment.newInstance(tasks))
                .commit();

    }
    @Override
    public void gotoTasks() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, TasksFragment.newInstance(tasks))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoCreateTask() {
        CreateTaskFragment fragment = (CreateTaskFragment) getSupportFragmentManager().findFragmentByTag("create-task-fragment");
        if(fragment == null) {
            fragment = new CreateTaskFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, fragment, "create-task-fragment")
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void gotoSelectTaskDate() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectTaskDateFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cancelDate() {
        getSupportFragmentManager().popBackStack();
    }
    @Override
    public void sendDate(String date) {
        CreateTaskFragment fragment = (CreateTaskFragment) getSupportFragmentManager().findFragmentByTag("create-task-fragment");
        if(fragment != null){
            fragment.setSelectedDate(date);
        }
        getSupportFragmentManager().popBackStack();
    }
    @Override
    public void addTask(Task task) {
        tasks.add(task);
        gotoTasks();
    }
}