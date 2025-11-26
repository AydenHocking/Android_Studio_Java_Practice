package com.example.assignment07;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.assignment07.databinding.FragmentBooksBinding;
import com.example.assignment07.databinding.FragmentGenresBinding;

import java.util.ArrayList;

public class BooksFragment extends Fragment {

    private static final String ARG_GENRE = "genre";
    private String genre;

    public BooksFragment() {
        // Required empty public constructor
    }


    public static BooksFragment newInstance(String genre) {
        BooksFragment fragment = new BooksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GENRE, genre);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            genre = getArguments().getString(ARG_GENRE);
        }
    }
    FragmentBooksBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBooksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Book> books = Data.getBooksByGenre(genre);
        ArrayList<String> bookDisplayList = new ArrayList<>();
        for (Book book : books) {
            bookDisplayList.add(book.getTitle() + "\n" + book.getAuthor() + "\n" + book.getGenre() + "\n" + book.getYear());
        }
        binding.textViewGenre.setText(genre);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, bookDisplayList);
        binding.listViewBooks.setAdapter(adapter);
        binding.listViewBooks.setOnItemClickListener((parent, view1, position, id) -> {
            Book selectedBook = books.get(position);
            if (tListener != null) {
                tListener.onBookSelected(selectedBook);
            }
        });
        binding.buttonBack.setOnClickListener(v -> {
            tListener.back();
        });
    }
    BooksFragmentListener tListener;

    @Override
    public void onAttach(@NonNull android.content.Context context) {
        super.onAttach(context);
        tListener = (BooksFragmentListener) context;
    }

    public interface BooksFragmentListener{
        void back();
        void onBookSelected(Book book);
    }
}