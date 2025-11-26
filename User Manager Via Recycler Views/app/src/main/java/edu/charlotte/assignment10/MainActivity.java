package edu.charlotte.assignment10;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import edu.charlotte.assignment10.fragments.AddUserFragment;
import edu.charlotte.assignment10.fragments.FilterFragment;
import edu.charlotte.assignment10.fragments.ProfileFragment;
import edu.charlotte.assignment10.fragments.SelectAgeGroupFragment;
import edu.charlotte.assignment10.fragments.SelectMoodFragment;
import edu.charlotte.assignment10.fragments.SortFragment;
import edu.charlotte.assignment10.fragments.UsersFragment;
import edu.charlotte.assignment10.models.Mood;
import edu.charlotte.assignment10.models.User;

public class MainActivity extends AppCompatActivity implements UsersFragment.UsersListener, ProfileFragment.ProfileListener,
        AddUserFragment.AddUserListener, SelectAgeGroupFragment.SelectAgeGroupListener, SelectMoodFragment.SelectMoodListener, SortFragment.SortListener, FilterFragment.FilterListener {
    ArrayList<User> mUsers = new ArrayList<>();
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

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new UsersFragment(), "users")
                .commit();
    }

    @Override
    public void clearAll() {

        mUsers.clear();
        UsersFragment fragment = (UsersFragment) getSupportFragmentManager().findFragmentById(R.id.main);
        fragment.refreshUsers(mUsers);
    }

    @Override
    public void gotoAddNew() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new AddUserFragment(), "AddUserFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return mUsers;
    }

    @Override
    public void gotoUserProfile(User user) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, ProfileFragment.newInstance(user))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSortSelection() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new SortFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoFilterSelection() {
        FilterFragment filterFragment = new FilterFragment();
        filterFragment.setUsers(mUsers);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, filterFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteUser(User user) {
        mUsers.remove(user);
        UsersFragment fragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("users");
        if (fragment != null) {
            fragment.refreshUsers(mUsers);
        }
    }

    @Override
    public void onBackFromProfile() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void gotoSelectMood() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new SelectMoodFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoSelectAgeRange() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main, new SelectAgeGroupFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void submitUser(User user) {
        mUsers.add(user);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onAgeGroupSelected(String ageGroup) {
        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("AddUserFragment");
        if (fragment != null) {
            fragment.setSelectedAgeRange(ageGroup);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onAgeGroupSelectionCancelled() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onMoodSelected(Mood mood) {
        AddUserFragment fragment = (AddUserFragment) getSupportFragmentManager().findFragmentByTag("AddUserFragment");
        if(fragment != null) {
            fragment.setSelectedMood(mood);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onMoodSelectionCancelled() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSortSelected(String sortBy) {
        UsersFragment usersFragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("users");
        if (usersFragment != null) {
            usersFragment.applySort(sortBy);
        }
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onSortCancelled() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onFilterSelected(String type, String value) {
        FragmentManager fm = getSupportFragmentManager();

        fm.popBackStack();

        fm.executePendingTransactions();

        UsersFragment usersFragment = (UsersFragment) fm.findFragmentByTag("users");

        if (usersFragment == null) {
            usersFragment = new UsersFragment();
            fm.beginTransaction()
                    .replace(R.id.main, usersFragment, "users")
                    .commit();
            fm.executePendingTransactions();
        }

        usersFragment.applyFilter(type, value);
    }

    @Override
    public void onFilterCleared() {
        getSupportFragmentManager().popBackStack();

        UsersFragment usersFragment = (UsersFragment) getSupportFragmentManager().findFragmentByTag("users");

        if (usersFragment != null) {
            usersFragment.clearFilter();
            usersFragment.refreshUsers(mUsers);
        }
    }
}