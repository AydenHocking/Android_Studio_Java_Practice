package edu.charlotte.assignment08;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.charlotte.assignment08.databinding.ActivityMainBinding;
import edu.charlotte.assignment08.fragments.AddTaskFragment;
import edu.charlotte.assignment08.fragments.SelectCategoryFragment;
import edu.charlotte.assignment08.fragments.SelectPriorityFragment;
import edu.charlotte.assignment08.fragments.TaskDetailsFragment;
import edu.charlotte.assignment08.fragments.TasksFragment;
import edu.charlotte.assignment08.models.Data;
import edu.charlotte.assignment08.models.Task;

public class MainActivity extends AppCompatActivity implements TasksFragment.TasksFragmentListener, AddTaskFragment.AddTaskFragmentListener, TaskDetailsFragment.TaskDetailsFragmentListener, SelectCategoryFragment.SelectCategoryFragmentListener, SelectPriorityFragment.SelectPriorityFragmentListener {
    private ArrayList<Task> mTasks = new ArrayList<>();
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mTasks.addAll(Data.sampleTestTasks); //adding for testing

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main, TasksFragment.newInstance())
                .commit();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return mTasks;
    }

    @Override
    public void gotoAddTask() {

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main,new AddTaskFragment(), "task")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoTaskDetails(Task task) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, TaskDetailsFragment.newInstance(task))
                .addToBackStack(null)
                .commit();
    }

    public void deleteTask(Task task) {
        mTasks.remove(task);
    }

    public void clearAllTasks() {
        mTasks.clear();
    }

    public void sortTasksAscending() {
        mTasks.sort((t1, t2) -> t1.getPriority() - t2.getPriority());
    }

    public void sortTasksDescending() {
        mTasks.sort((t1, t2) -> t2.getPriority() - t1.getPriority());
    }

    @Override
    public void selectCategory() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main,new SelectCategoryFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void selectPriority() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main,new SelectPriorityFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addNewTask(Task task) {
        mTasks.add(task);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void back() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void prioritySelected(String priority) {
        AddTaskFragment addTaskFragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("task");
        if (addTaskFragment != null) {
            addTaskFragment.setSelectedPriority(priority);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void categorySelected(String category) {
        AddTaskFragment addTaskFragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("task");
        if (addTaskFragment != null) {
            addTaskFragment.setSelectedCategory(category);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void deleteTaskDetails(Task task) {
        mTasks.remove(task);
        getSupportFragmentManager().popBackStack();
    }
}