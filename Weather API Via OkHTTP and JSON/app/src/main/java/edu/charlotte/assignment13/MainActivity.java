package edu.charlotte.assignment13;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.charlotte.assignment13.models.DataService;

public class MainActivity extends AppCompatActivity implements CitiesFragment.CitiesFragmentListener, CurrentWeatherFragment.CurrentWeatherFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main, new CitiesFragment())
                .commit();
    }

    @Override
    public void gotoCurrentWeather(DataService.City city) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, CurrentWeatherFragment.newInstance(city))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoWeatherForecast(DataService.City city) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, WeatherForecastFragment.newInstance(city))
                .addToBackStack(null)
                .commit();
    }
}