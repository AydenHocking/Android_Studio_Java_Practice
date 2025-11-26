package edu.charlotte.trivia;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StatsFragment extends Fragment {

    private StatsListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textViewResponse = view.findViewById(R.id.textViewResponse);
        Button buttonNewGame = view.findViewById(R.id.buttonNewGame);

        if (getArguments() != null) {
            int totalQuestions = getArguments().getInt("totalQuestions", 0);
            int correctFirstTry = getArguments().getInt("correctFirstTry", 0);

            textViewResponse.setText(correctFirstTry + " out of " + totalQuestions + " questions were answered correctly from the first attempt");
        }

        buttonNewGame.setOnClickListener(v -> {
            if (listener != null) {
                listener.onStartNewGame();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (StatsListener) context;

    }

    public interface StatsListener {
        void onStartNewGame();
    }
}
