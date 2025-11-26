package com.example.assignment07;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment07.databinding.FragmentBookDetailsBinding;


public class BookDetailsFragment extends Fragment {
    private static final String ARG_BOOK = "book";
    private Book book;

    public BookDetailsFragment() {
        // Required empty public constructor
    }


    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, book);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = (Book) getArguments().getSerializable(ARG_BOOK);
        }
    }
    FragmentBookDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.textViewTitle.setText(book.getTitle());
        binding.textViewAuthor.setText(book.getAuthor());
        binding.textViewGenreDetail.setText(book.getGenre());
        binding.textViewYear.setText(String.valueOf(book.getYear()));
        binding.buttonBackDetail.setOnClickListener(v -> {
            tListener.back();
        });
    }
    BooksDetailsFragmentListener tListener;


    @Override
    public void onAttach(@NonNull android.content.Context context) {
        super.onAttach(context);
        tListener = (BooksDetailsFragmentListener) context;
    }

    public interface BooksDetailsFragmentListener{
        void back();
    }
}