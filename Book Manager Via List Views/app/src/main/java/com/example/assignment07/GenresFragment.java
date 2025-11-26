package com.example.assignment07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.assignment07.databinding.FragmentGenresBinding;

import java.util.ArrayList;


public class GenresFragment extends Fragment {
    ListView listView;
    ArrayAdapter<String> adapter;

    public GenresFragment() {
        // Required empty public constructor
    }


    public static GenresFragment newInstance() {
        GenresFragment fragment = new GenresFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FragmentGenresBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGenresBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        ArrayList<String> genres = Data.getAllGenres();
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, genres);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedGenre = genres.get(position);
            tListener.onGenreSelected(selectedGenre);
        });
    }
    GenreFragmentListener tListener;

    @Override
    public void onAttach(@NonNull android.content.Context context) {
        super.onAttach(context);
        tListener = (GenreFragmentListener) context;
    }

    public interface GenreFragmentListener{
        void onGenreSelected(String genre);
    }

}