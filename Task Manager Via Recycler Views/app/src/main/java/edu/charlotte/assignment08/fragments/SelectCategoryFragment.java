package edu.charlotte.assignment08.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import edu.charlotte.assignment08.R;
import edu.charlotte.assignment08.models.Data;
import edu.charlotte.assignment08.models.Task;


public class SelectCategoryFragment extends Fragment {

    RecyclerView recyclerView;
    SelectCategoryRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;
    Button buttonCancel;
    SelectCategoryFragmentListener tListener;


    public SelectCategoryFragment() {
        // Required empty public constructor
    }


    public static SelectCategoryFragment newInstance() {
        SelectCategoryFragment fragment = new SelectCategoryFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof SelectCategoryFragmentListener) {
            tListener = (SelectCategoryFragmentListener) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SelectCategoryRecyclerViewAdapter(Data.categories, tListener);
        recyclerView.setAdapter(adapter);

        buttonCancel.setOnClickListener(v -> {
            if (tListener != null) {
                tListener.back();
            }
        });
    }
    private class SelectCategoryRecyclerViewAdapter extends RecyclerView.Adapter<SelectCategoryRecyclerViewAdapter.SelectCategoryViewHolder> {
        String[] categories;
        SelectCategoryFragmentListener tListener;
        public SelectCategoryRecyclerViewAdapter(String[] categories, SelectCategoryFragmentListener tListener) {
            this.categories = categories;
            this.tListener = tListener;
        }

        @NonNull
        @Override
        public SelectCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selection_list_item, parent, false);
            SelectCategoryRecyclerViewAdapter.SelectCategoryViewHolder categoryViewHolder = new SelectCategoryRecyclerViewAdapter.SelectCategoryViewHolder(view, tListener);
            return categoryViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SelectCategoryViewHolder holder, int position) {
            String category = categories[position];
            holder.textViewCategory.setText(category);

            holder.itemView.setOnClickListener(v -> {
                if (tListener != null) {
                    tListener.categorySelected(category);
                }
            });
        }

        @Override
        public int getItemCount() {
            return categories.length;
        }
        public class SelectCategoryViewHolder extends RecyclerView.ViewHolder {
            TextView textViewCategory;

            public SelectCategoryViewHolder(@NonNull View itemView, SelectCategoryFragmentListener tListener) {
                super(itemView);
                textViewCategory = itemView.findViewById(R.id.textView);
            }
        }
    }

    public interface SelectCategoryFragmentListener {
        void back();
        void categorySelected(String category);

    }
}