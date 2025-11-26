package edu.charlotte.assignment10.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import edu.charlotte.assignment10.R;
import edu.charlotte.assignment10.databinding.FragmentFilterBinding;
import edu.charlotte.assignment10.models.Mood;
import edu.charlotte.assignment10.models.User;

public class FilterFragment extends Fragment {
    public FilterFragment() {
        // Required empty public constructor
    }
    FilterListener mListener;

    FragmentFilterBinding binding;
    private ArrayList<User> mUsers = new ArrayList<>();
    private ArrayList<String> nameInitials = new ArrayList<>();
    private ArrayList<String> ageGroups = new ArrayList<>();
    private ArrayList<String> feelings = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle("Select Filter Criteria");

        nameInitials = new ArrayList<>();
        ageGroups = new ArrayList<>();
        feelings = new ArrayList<>();

        for (User u : mUsers) {
            String firstChar = u.getName().substring(0, 1).toUpperCase();
            if (!nameInitials.contains(firstChar)) nameInitials.add(firstChar);
            if (!ageGroups.contains(u.getAgeGroup())) ageGroups.add(u.getAgeGroup());
            String feeling = u.getMood().getName();
            if (!feelings.contains(feeling)) feelings.add(feeling);
        }

        java.util.Collections.sort(nameInitials);
        java.util.Collections.sort(ageGroups);
        java.util.Collections.sort(feelings);

        binding.recyclerViewName.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewAge.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewFeeling.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        FilterAdapter nameAdapter = new FilterAdapter(nameInitials, selected -> {
            if (mListener != null) mListener.onFilterSelected("NAME", selected);
        });
        FilterAdapter ageAdapter = new FilterAdapter(ageGroups, selected -> {
            if (mListener != null) mListener.onFilterSelected("AGE", selected);
        });
        ArrayList<Mood> presentMoods = new ArrayList<>();
        for (User u : mUsers) {
            Mood mood = u.getMood();
            if (mood != null && !presentMoods.contains(mood)) {
                presentMoods.add(mood);
            }
        }

        MoodAdapter moodAdapter = new MoodAdapter(presentMoods.toArray(new Mood[0]), mood -> {
            if (mListener != null) mListener.onFilterSelected("FEELING", mood.getName());
        });
        binding.recyclerViewFeeling.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewFeeling.setAdapter(moodAdapter);
        binding.recyclerViewFeeling.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerViewFeeling.setAdapter(moodAdapter);

        binding.recyclerViewName.setAdapter(nameAdapter);
        binding.recyclerViewAge.setAdapter(ageAdapter);
        binding.recyclerViewFeeling.setAdapter(moodAdapter);

        binding.buttonClearFilter.setOnClickListener(v -> {
            if (mListener != null) mListener.onFilterCleared();
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof FilterListener) {
            mListener = (FilterListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement FilterListener");
        }
    }
    public void setUsers(ArrayList<User> users) {
        this.mUsers = users;
    }


    public interface FilterListener {
        void onFilterSelected(String type, String value);
        void onFilterCleared();
    }


    private static class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {
        private final ArrayList<String> items;
        private final OnItemClickListener listener;

        interface OnItemClickListener { void onItemClick(String selected); }

        public FilterAdapter(ArrayList<String> items, OnItemClickListener listener) {
            this.items = items;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            android.widget.TextView textView = new android.widget.TextView(parent.getContext());
            textView.setPadding(40, 20, 40, 20);
            textView.setTextSize(16f);
            textView.setClickable(true);
            textView.setFocusable(true);
            return new ViewHolder(textView, listener);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.textView.setText(items.get(position));
        }

        @Override
        public int getItemCount() { return items.size(); }

        static class ViewHolder extends RecyclerView.ViewHolder {
            android.widget.TextView textView;
            public ViewHolder(@NonNull android.view.View itemView, OnItemClickListener listener) {
                super(itemView);
                textView = (android.widget.TextView) itemView;
                textView.setOnClickListener(v -> listener.onItemClick(textView.getText().toString()));
            }
        }
    }


    private static class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.MoodViewHolder> {
        private final Mood[] moods;
        private final OnMoodClickListener listener;

        interface OnMoodClickListener {
            void onMoodClick(Mood mood);
        }

        public MoodAdapter(Mood[] moods, OnMoodClickListener listener) {
            this.moods = moods;
            this.listener = listener;
        }

        @NonNull
        @Override
        public MoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image_filter, parent, false);
            return new MoodViewHolder(view, listener);
        }

        @Override
        public void onBindViewHolder(@NonNull MoodViewHolder holder, int position) {
            Mood mood = moods[position];
            holder.imageViewMood.setImageResource(mood.getImageResourceId());
            holder.itemView.setOnClickListener(v -> listener.onMoodClick(mood));
        }

        @Override
        public int getItemCount() {
            return moods.length;
        }

        static class MoodViewHolder extends RecyclerView.ViewHolder {
            ImageView imageViewMood;

            public MoodViewHolder(@NonNull View itemView, OnMoodClickListener listener) {
                super(itemView);
                imageViewMood = itemView.findViewById(R.id.imageView);
            }
        }
    }

}