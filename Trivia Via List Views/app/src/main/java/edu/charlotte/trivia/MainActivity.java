package edu.charlotte.trivia;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import edu.charlotte.trivia.databinding.ActivityMainBinding;
import edu.charlotte.trivia.models.Trivia;

public class MainActivity extends AppCompatActivity implements SelectFragment.SelectTriviaListener, TriviaFragment.TriviaListener, StatsFragment.StatsListener {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main, SelectFragment.newInstance())
                .commit();
    }

    @Override
    public void onTriviaSelected(Trivia trivia) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("selected_trivia", trivia);

        TriviaFragment triviaFragment = new TriviaFragment();
        triviaFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, triviaFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onTriviaFinished(int totalQuestions, int correctFirstTry) {
        Bundle bundle = new Bundle();
        bundle.putInt("totalQuestions", totalQuestions);
        bundle.putInt("correctFirstTry", correctFirstTry);

        StatsFragment statsFragment = new StatsFragment();
        statsFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, statsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onCancelTrivia() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, SelectFragment.newInstance())
                .commit();
    }

    @Override
    public void onStartNewGame() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, SelectFragment.newInstance())
                .commit();
    }
}