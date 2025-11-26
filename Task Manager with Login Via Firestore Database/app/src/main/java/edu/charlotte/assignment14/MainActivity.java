package edu.charlotte.assignment14;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.charlotte.assignment14.fragments.AddTaskFragment;
import edu.charlotte.assignment14.fragments.CreateAccountFragment;
import edu.charlotte.assignment14.fragments.LoginFragment;
import edu.charlotte.assignment14.fragments.SelectCategoryFragment;
import edu.charlotte.assignment14.fragments.SelectPriorityFragment;
import edu.charlotte.assignment14.fragments.TasksFragment;
import edu.charlotte.assignment14.models.Priority;
import edu.charlotte.assignment14.models.Task;

public class MainActivity extends AppCompatActivity implements SelectPriorityFragment.SelectPriorityListener, SelectCategoryFragment.SelectCategoryListener,
        AddTaskFragment.AddTaskListener, TasksFragment.TasksListener, LoginFragment.LoginListener, CreateAccountFragment.RegisterListener {
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main, new TasksFragment())
                    .commit();
        } else{
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main, new LoginFragment())
                    .commit();

        }
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.main, new LoginFragment())
//                .commit();
    }

    @Override
    public void onCategorySelected(String category) {
        AddTaskFragment addTaskFragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("addTaskFragment");
        if (addTaskFragment != null) {
            addTaskFragment.setSelectedCategory(category);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onPrioritySelected(Priority priority) {
        AddTaskFragment addTaskFragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag("addTaskFragment");
        if (addTaskFragment != null) {
            addTaskFragment.setSelectedPriority(priority);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoSelectPriority() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectPriorityFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectCategory() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectCategoryFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onTaskAdded(Task task) {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCancelSelection() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoAddTask() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new AddTaskFragment(), "addTaskFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSignout() {
        FirebaseAuth.getInstance().signOut();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new LoginFragment())
                .commit();
    }

    @Override
    public void deleteTask(Task task) {
        FirebaseFirestore.getInstance()
                .collection("tasks")
                .document(task.getId())
                .delete();
    }

    @Override
    public void onLoginSuccess() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new TasksFragment())
                .commit();
    }

    @Override
    public void onCreateAccountSuccess() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new TasksFragment())
                .commit();
    }

    @Override
    public void gotoLoginAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new LoginFragment())
                .commit();
    }

    @Override
    public void gotoCreateAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new CreateAccountFragment())
                .commit();
    }
}