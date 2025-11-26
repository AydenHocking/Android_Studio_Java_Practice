package edu.charlotte.assignment12.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.charlotte.assignment12.R;
import edu.charlotte.assignment12.databinding.FragmentMainBinding;
import edu.charlotte.assignment12.models.AppDatabase;
import edu.charlotte.assignment12.models.Wellness;

public class MainFragment extends Fragment {
    public MainFragment() {
        // Required empty public constructor
    }

    FragmentMainBinding binding;
    MainListener mListener;
    private AppDatabase db;
    private MainAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = Room.databaseBuilder(requireContext(), AppDatabase.class, "wellness.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        adapter = new MainAdapter(new ArrayList<>());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);
        loadLogs();

        binding.buttonAddLog.setOnClickListener(v -> mListener.gotoAddLog());
        binding.buttonVisualizeProgress.setOnClickListener(v -> mListener.gotoVisualizeProgress());
    }

    private void loadLogs() {
        List<Wellness> logs = db.WellnessDao().getAll();
        adapter.setLogs(logs);
    }
    public void onLogAdded(Wellness log) {
        db.WellnessDao().insert(log);
        loadLogs();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainListener) {
            mListener = (MainListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement MainListener");
        }
    }
    public interface MainListener {
        void gotoAddLog();
        void gotoVisualizeProgress();
    }


    private class MainAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<MainAdapter.MainViewHolder> {
        private List<Wellness> logs;

        MainAdapter(List<Wellness> logs) {
            this.logs = logs;
        }

        void setLogs(List<Wellness> newLogs) {
            this.logs = newLogs != null ? newLogs : new ArrayList<>();
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_list_item, parent, false);
            return new MainViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
            Wellness log = logs.get(position);
            holder.bind(log);
        }

        @Override
        public int getItemCount() {
            return logs != null ? logs.size() : 0;
        }

        class MainViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            TextView txtDate, txtSleep, txtExercise, txtWeight;
            View btnDelete;

            MainViewHolder(@NonNull View itemView) {
                super(itemView);
                txtDate = itemView.findViewById(R.id.textViewDateTime);
                txtSleep = itemView.findViewById(R.id.textViewSleep);
                txtExercise = itemView.findViewById(R.id.textViewExercise);
                txtWeight = itemView.findViewById(R.id.textViewWeight);
                btnDelete = itemView.findViewById(R.id.imageView);
            }

            void bind(Wellness log) {
                txtDate.setText(log.getDateTime());
                txtSleep.setText("Sleep: " + log.getSleepHours() + " hours (Quality: " + log.getSleepQuality() + ")");
                txtExercise.setText("Exercise: " + log.getExerciseHours() + " hours");
                txtWeight.setText("Weight: " + log.getWeight() + " lbs");

                btnDelete.setOnClickListener(v -> {
                    db.WellnessDao().delete(log);
                    loadLogs();
                });
            }

        }
    }
}