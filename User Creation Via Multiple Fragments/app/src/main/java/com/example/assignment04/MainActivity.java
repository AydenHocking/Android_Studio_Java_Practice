package com.example.assignment04;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignment04.databinding.ActivityMainBinding;
/*
Assignment 4
Ayden Hocking
MainActivity.java
 */
public class MainActivity extends AppCompatActivity implements WelcomeFragment.WelcomeFragmentListener, CreateUserFragment.CreateUserFragmentListener, EditUserFragment.EditUserFragmentListener, ProfileFragment.ProfileFragmentListener {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.contentView, new WelcomeFragment())
                .commit();
    }
    @Override
    public void gotoCreateUser() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, new CreateUserFragment())
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void gotoProfile(User user) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, ProfileFragment.newInstance(user),"profile-fragment")
                .addToBackStack(null)
                .commit();
    }

    public void gotoEditUser(User user) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentView, EditUserFragment.newInstance(user))
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void cancelEditUser() {
        getSupportFragmentManager().popBackStack();
    }
    @Override
    public void updateProfileUser(User user) {
        ProfileFragment profileFragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("profile-fragment");
        profileFragment.updateUser(user);
        getSupportFragmentManager().popBackStack();
    }
}