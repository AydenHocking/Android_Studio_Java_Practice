package com.example.assignment07;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignment07.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements GenresFragment.GenreFragmentListener, BooksFragment.BooksFragmentListener, BookDetailsFragment.BooksDetailsFragmentListener {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main, GenresFragment.newInstance())
                .commit();
    }
    @Override
    public void onGenreSelected(String genre) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, BooksFragment.newInstance(genre))
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void back() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBookSelected(Book selectedBook) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, BookDetailsFragment.newInstance(selectedBook))
                .addToBackStack(null)
                .commit();
    }
}