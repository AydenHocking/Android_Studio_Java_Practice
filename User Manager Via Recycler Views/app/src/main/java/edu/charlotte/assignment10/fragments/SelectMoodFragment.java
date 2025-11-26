package edu.charlotte.assignment10.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import edu.charlotte.assignment10.R;
import edu.charlotte.assignment10.databinding.FragmentSelectMoodBinding;
import edu.charlotte.assignment10.models.Data;
import edu.charlotte.assignment10.models.Mood;

public class SelectMoodFragment extends Fragment {
    public SelectMoodFragment() {
        // Required empty public constructor
    }

    FragmentSelectMoodBinding binding;
    private Mood[] mMoods = Data.getMoods();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectMoodBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Mood");
        MoodAdapter adapter = new MoodAdapter(getContext(), mMoods);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener((parent, view1, position, id) -> {
            Mood selectedMood = mMoods[position];
            if (mListener != null) {
                mListener.onMoodSelected(selectedMood);
            }
        });
        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMoodSelectionCancelled();
            }
        });
    }

    private static class MoodAdapter extends ArrayAdapter<Mood> {
        private final Mood[] moods;
        private final LayoutInflater inflater;

        public MoodAdapter(@NonNull Context context, Mood[] moods) {
            super(context, R.layout.list_item_mood, moods);
            this.moods = moods;
            this.inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_mood, parent, false);
            }

            Mood mood = moods[position];
            ImageView imageView = convertView.findViewById(R.id.imageViewMood);
            TextView textView = convertView.findViewById(R.id.textViewMood);

            imageView.setImageResource(mood.getImageResourceId());
            textView.setText(mood.getName());

            return convertView;
        }
    }

    SelectMoodListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectMoodListener) {
            mListener = (SelectMoodListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectMoodListener");
        }
    }

    public interface SelectMoodListener {
        void onMoodSelected(Mood mood);
        void onMoodSelectionCancelled();
    }
}