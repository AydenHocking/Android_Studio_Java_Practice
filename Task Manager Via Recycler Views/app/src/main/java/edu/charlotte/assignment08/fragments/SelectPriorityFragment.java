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

import edu.charlotte.assignment08.R;
import edu.charlotte.assignment08.models.Data;


public class SelectPriorityFragment extends Fragment {

    RecyclerView recyclerView;
    SelectPriorityRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;
    Button buttonCancel;
    SelectPriorityFragmentListener tListener;

    public SelectPriorityFragment() {
        // Required empty public constructor
    }


    public static SelectPriorityFragment newInstance() {
        SelectPriorityFragment fragment = new SelectPriorityFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof SelectPriorityFragmentListener) {
            tListener = (SelectPriorityFragmentListener) getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_priority, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SelectPriorityRecyclerViewAdapter(Data.priorities, tListener);
        recyclerView.setAdapter(adapter);

        buttonCancel.setOnClickListener(v -> {
            if (tListener != null) {
                tListener.back();
            }
        });
    }

    private class SelectPriorityRecyclerViewAdapter extends RecyclerView.Adapter<SelectPriorityRecyclerViewAdapter.SelectPriorityViewHolder> {

        String[] priorities;
        SelectPriorityFragmentListener tListener;
        public SelectPriorityRecyclerViewAdapter(String[] priorities, SelectPriorityFragmentListener tListener) {
            this.priorities = priorities;
            this.tListener = tListener;
        }

        @NonNull
        @Override
        public SelectPriorityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selection_list_item, parent, false);
            SelectPriorityRecyclerViewAdapter.SelectPriorityViewHolder priorityViewHolder = new SelectPriorityRecyclerViewAdapter.SelectPriorityViewHolder(view, tListener);
            return priorityViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull SelectPriorityViewHolder holder, int position) {
            String priority = priorities[position];
            holder.textViewPriority.setText(priority);

            holder.itemView.setOnClickListener(v -> {
                if (tListener != null) {
                    tListener.prioritySelected(priority);
                }
            });
        }

        @Override
        public int getItemCount() {
            return priorities.length;
        }

        public class SelectPriorityViewHolder extends RecyclerView.ViewHolder {
            TextView textViewPriority;

            public SelectPriorityViewHolder(@NonNull View itemView, SelectPriorityFragmentListener tListener) {
                super(itemView);
                textViewPriority = itemView.findViewById(R.id.textView);
            }
        }
    }

    public interface SelectPriorityFragmentListener {
        void back();
        void prioritySelected(String priority);
    }
}