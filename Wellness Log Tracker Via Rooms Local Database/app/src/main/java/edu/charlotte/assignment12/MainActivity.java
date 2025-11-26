package edu.charlotte.assignment12;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import edu.charlotte.assignment12.R;
import edu.charlotte.assignment12.fragments.AddLogFragment;
import edu.charlotte.assignment12.fragments.MainFragment;
import edu.charlotte.assignment12.fragments.SelectExerciseHoursFragment;
import edu.charlotte.assignment12.fragments.SelectSleepHoursFragment;
import edu.charlotte.assignment12.fragments.SelectSleepQualityFragment;
import edu.charlotte.assignment12.fragments.VisualizeProgressFragment;
import edu.charlotte.assignment12.models.AppDatabase;
import edu.charlotte.assignment12.models.Quality;
import edu.charlotte.assignment12.models.Wellness;

public class MainActivity extends AppCompatActivity implements MainFragment.MainListener, AddLogFragment.AddLogListener, SelectSleepHoursFragment.SelectSleepHoursListener, SelectExerciseHoursFragment.SelectExerciseHoursListener, SelectSleepQualityFragment.SelectSleepQualityListener, VisualizeProgressFragment.VisualizeProgressListener {

    private AppDatabase db;


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
        db = Room.databaseBuilder(this, AppDatabase.class, "wellness.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new MainFragment(), "mainFragment")
                .commit();
    }

    @Override
    public void gotoAddLog() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new AddLogFragment(), "addLogFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoVisualizeProgress() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new VisualizeProgressFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectSleepHours() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectSleepHoursFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectSleepQuality() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectSleepQualityFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectExerciseHours() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, new SelectExerciseHoursFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onLogAdded(Wellness log) {
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager()
                .findFragmentByTag("mainFragment");

        if (mainFragment != null) {
            mainFragment.onLogAdded(log);
        }
        getSupportFragmentManager().popBackStack();
    }


    @Override
    public void onSleepHoursSelected(String sleeps) {
        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("addLogFragment");
        if (fragment != null) {
            fragment.setSelectedSleepHours(sleeps);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onExerciseHoursSelected(String exercises) {
        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("addLogFragment");
        if (fragment != null) {
            fragment.setSelectedExerciseHours(exercises);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onQualitySelected(Quality quality) {
        AddLogFragment fragment = (AddLogFragment) getSupportFragmentManager().findFragmentByTag("addLogFragment");
        if (fragment != null) {
            fragment.setSelectedSleepQuality(quality);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onCancelSelection() {
        getSupportFragmentManager().popBackStack();
    }

}