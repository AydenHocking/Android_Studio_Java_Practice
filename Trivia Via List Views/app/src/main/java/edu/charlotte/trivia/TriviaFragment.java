package edu.charlotte.trivia;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.charlotte.trivia.models.Question;
import edu.charlotte.trivia.models.Trivia;

public class TriviaFragment extends Fragment {

    TextView textViewQuestionNumber, textViewQuestion;
    ImageView imageViewImage;
    ListView listViewAnswers;
    Button buttonCancel;
    Trivia trivia;
    ArrayList<Question> questions;
    int currentIndex = 0;
    int correctFirstTry = 0;
    TriviaListener listener;
    boolean firstAttempt = true;

    public TriviaFragment() {
        // Required empty public constructor
    }

    public static TriviaFragment newInstance(Trivia trivia) {
        TriviaFragment fragment = new TriviaFragment();
        Bundle args = new Bundle();
        args.putSerializable("selected_trivia", trivia);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            trivia = (Trivia) getArguments().getSerializable("selected_trivia");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trivia, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewQuestionNumber = view.findViewById(R.id.textViewQuestionNumber);
        textViewQuestion = view.findViewById(R.id.textViewQuestion);
        imageViewImage = view.findViewById(R.id.imageViewImage);
        listViewAnswers = view.findViewById(R.id.listViewAnswers);
        buttonCancel = view.findViewById(R.id.buttonCancel);

        if (trivia != null) {
            questions = trivia.getQuestions();
            displayQuestion();
        }

        listViewAnswers.setOnItemClickListener((parent, view1, position, id) -> handleAnswerSelection(position));
        buttonCancel.setOnClickListener(v -> listener.onCancelTrivia());
    }

    private void displayQuestion() {
        if (currentIndex >= questions.size()) {
            listener.onTriviaFinished(questions.size(), correctFirstTry);
            return;
        }

        Question q = questions.get(currentIndex);

        textViewQuestionNumber.setText("Question " + (currentIndex + 1) + " of " + questions.size());
        textViewQuestion.setText(q.getQuestion());

        if (q.getImgUrl() != null && !q.getImgUrl().isEmpty()) {
            imageViewImage.setVisibility(View.VISIBLE);
            // Picasso Implementation found via AI
            Picasso.get()
                    .load(q.getImgUrl())
                    .placeholder(R.drawable.ic_no_image)
                    .error(R.drawable.ic_no_image)
                    .into(imageViewImage);
            //
        } else {
            imageViewImage.setVisibility(View.GONE);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                q.getAnswers()
        );
        listViewAnswers.setAdapter(adapter);

    }

    private void handleAnswerSelection(int selectedIndex) {
        Question currentQuestion = questions.get(currentIndex);

        if (selectedIndex == currentQuestion.getCorrectAnswerIndex()) {
            if (firstAttempt) {
                correctFirstTry++;
            }
            Toast.makeText(getContext(), "Correct", Toast.LENGTH_SHORT).show();
            currentIndex++;
            firstAttempt = true;
            displayQuestion();
        } else {
            Toast.makeText(getContext(), "Incorrect", Toast.LENGTH_SHORT).show();
            firstAttempt = false;
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (TriviaListener) context;
    }

    public interface TriviaListener {
        void onTriviaFinished(int totalQuestions, int correctFirstTry);
        void onCancelTrivia();
    }
}
