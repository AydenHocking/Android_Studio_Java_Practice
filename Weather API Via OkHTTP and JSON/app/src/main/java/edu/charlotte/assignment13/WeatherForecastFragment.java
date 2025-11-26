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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.charlotte.assignment13.databinding.FragmentWeatherForecastBinding;
import edu.charlotte.assignment13.models.DataService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class WeatherForecastFragment extends Fragment {

    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private DataService.City mCity;
    FragmentWeatherForecastBinding binding;
    private final String API_KEY = "bbf8f37c1ce46a1e2cb900a344918da8";
    private ArrayList<Forecast> forecastList = new ArrayList<>();
    private ForecastAdapter adapter;

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    public static WeatherForecastFragment newInstance(DataService.City city) {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
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
        binding = FragmentWeatherForecastBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Weather Forecast");

        binding.textViewCityForecast.setText(mCity.getCity());

        adapter = new ForecastAdapter(requireContext(), forecastList);
        binding.listViewForecast.setAdapter(adapter);

        String url = "https://api.openweathermap.org/data/2.5/forecast?lat="
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
                        JSONArray listArray = json.getJSONArray("list");

                        forecastList.clear();
                        for (int i = 0; i < listArray.length(); i++) {
                            JSONObject obj = listArray.getJSONObject(i);
                            String dateTime = obj.getString("dt_txt");
                            JSONObject mainObj = obj.getJSONObject("main");
                            JSONArray weatherArray = obj.getJSONArray("weather");
                            JSONObject weatherObj = weatherArray.getJSONObject(0);

                            Forecast forecast = new Forecast(
                                    dateTime,
                                    mainObj.getDouble("temp"),
                                    mainObj.getDouble("temp_max"),
                                    mainObj.getDouble("temp_min"),
                                    mainObj.getInt("humidity"),
                                    weatherObj.getString("description"),
                                    weatherObj.getString("icon")
                            );
                            forecastList.add(forecast);
                        }

                        requireActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());

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

    private class ForecastAdapter extends ArrayAdapter<Forecast> {
        public ForecastAdapter(@NonNull Context context, ArrayList<Forecast> forecasts) {
            super(context, 0, forecasts);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Forecast forecast = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.forecast_list_item, parent, false);
            }

            TextView textViewDate = convertView.findViewById(R.id.textViewDateTimeForecast);
            TextView textViewDesc = convertView.findViewById(R.id.textViewDescriptionForecast);
            TextView textViewTemp = convertView.findViewById(R.id.textViewTempForecast);
            TextView textViewTempMax = convertView.findViewById(R.id.textViewTempMaxForecast);
            TextView textViewTempMin = convertView.findViewById(R.id.textViewTempMinForecast);
            TextView textViewHumidity = convertView.findViewById(R.id.textViewHumidityForecast);
            ImageView imageViewIcon = convertView.findViewById(R.id.imageViewIconForecast);

            textViewDate.setText(forecast.getDateTime());
            textViewDesc.setText(forecast.getDescription());
            textViewTemp.setText(String.format("%.2fF", forecast.getTemp()));
            textViewTempMax.setText(String.format("Max: %.2fF", forecast.getTemp_max()));
            textViewTempMin.setText(String.format("Min: %.2fF", forecast.getTemp_min()));
            textViewHumidity.setText("Humidity: " + forecast.getHumidity() + "%");

            String iconUrl = "https://openweathermap.org/img/wn/"
                    + forecast.getIcon()
                    + ".png";
            Picasso.get().load(iconUrl).into(imageViewIcon);

            return convertView;
        }

    }
}