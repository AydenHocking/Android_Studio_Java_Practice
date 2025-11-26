package edu.charlotte.assignment13;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.charlotte.assignment13.databinding.FragmentCurrentWeatherBinding;
import edu.charlotte.assignment13.models.DataService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CurrentWeatherFragment extends Fragment {
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private DataService.City mCity;
    FragmentCurrentWeatherBinding binding;
    private final String API_KEY = "bbf8f37c1ce46a1e2cb900a344918da8";

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    public static CurrentWeatherFragment newInstance(DataService.City city) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (DataService.City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Current Weather");

        binding.buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoWeatherForecast(mCity);
            }
        });
        String url = "https://api.openweathermap.org/data/2.5/weather?lat="
                + mCity.getLatitude()
                + "&lon=" + mCity.getLongitude()
                + "&units=imperial"
                + "&appid=" + API_KEY;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String jsonData = response.body().string();
                        JSONObject json = new JSONObject(jsonData);
                        JSONArray weatherArray = json.getJSONArray("weather");//weather array
                        JSONObject weatherObj = weatherArray.getJSONObject(0);//weather object
                        JSONObject mainObj = json.getJSONObject("main");//temp,temp_min,temp_max,humidity
                        JSONObject windObj = json.getJSONObject("wind");//speed,deg
                        JSONObject cloudsObj = json.getJSONObject("clouds");//cloudiness(all)

                        Weather weather = new Weather(
                                mainObj.getDouble("temp"),
                                mainObj.getDouble("temp_max"),
                                mainObj.getDouble("temp_min"),
                                weatherObj.getString("description"),
                                mainObj.getInt("humidity"),
                                windObj.getDouble("speed"),
                                windObj.getInt("deg"),
                                cloudsObj.getInt("all"),
                                weatherObj.getString("icon")
                        );

                        requireActivity().runOnUiThread(() -> {
                            binding.textViewCity.setText(mCity.getCity());
                            binding.textViewTempVal.setText(weather.getTemp() + " F");
                            binding.textViewTempMaxVal.setText(weather.getTemp_max() + " F");
                            binding.textViewTempMinVal.setText(weather.getTemp_min() + " F");
                            binding.textViewDescriptionVal.setText(weather.getDescription());
                            binding.textViewHumidityVal.setText(weather.getHumidity() + "%");
                            binding.textViewWindSpeedVal.setText(weather.getSpeed() + " miles/hr");
                            binding.textViewWindDegreeVal.setText(weather.getDeg() + " degrees");
                            binding.textViewCloudinessVal.setText(weather.getAll() + "%");

                            String iconUrl = "https://openweathermap.org/img/wn/"
                                    + weather.getIcon()
                                    + ".png";
                            Picasso.get().load(iconUrl).into(binding.imageViewWeatherIcon);
                        });

                    } catch (JSONException e) {
                        Log.d("Error", "onResponse: " + e.getMessage());
                    }
                } else {
                    Log.d("Error", "onResponse: " + response.message());
                }
            }


            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                requireActivity().runOnUiThread(() -> {
                    Log.d("Error", "onResponse: " + e.getMessage());
                    Toast.makeText(getActivity(), "Failed To Load", Toast.LENGTH_SHORT).show();
                });
            }

        });
    }

    CurrentWeatherFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CurrentWeatherFragmentListener) context;
    }

    interface CurrentWeatherFragmentListener {
        void gotoWeatherForecast(DataService.City city);
    }
}