package edu.charlotte.taskstabapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import edu.charlotte.taskstabapp.databinding.ActivityMainBinding;
import edu.charlotte.taskstabapp.models.DataStore;
import edu.charlotte.taskstabapp.models.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    //Tasks pre-populated from DataStore
    ArrayList<Task> mTasks = DataStore.getTasks();
    ActivityMainBinding binding;
    ViewPager2 viewPager;
    TabLayout tabLayout;
    ViewPageAdapter viewPageAdapter;
    String[] tabTitles = {"LOW", "MEDIUM", "HIGH", "ALL"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        viewPageAdapter = new ViewPageAdapter(this);
        viewPager.setAdapter(viewPageAdapter);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int i) {
                tab.setText(tabTitles[i]);
            }
        }).attach();


        Log.d("demo", "onCreate: " + DataStore.getTasks());
    }
    public class ViewPageAdapter extends FragmentStateAdapter{
        public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            String priority = tabTitles[position];
            ArrayList<Task> tasks = sortByPriority(priority);
            return TasksFragment.newInstance(tasks, priority);
        }

        @Override
        public int getItemCount() {
            return tabTitles.length;
        }
    }

    private ArrayList<Task> sortByPriority(String priority){
        if (priority.equals("ALL")){
            return mTasks;
        }
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : mTasks){
            if (task.getPriority().equals(priority)){
                tasks.add(task);
            }
        }
        return tasks;
    }
}