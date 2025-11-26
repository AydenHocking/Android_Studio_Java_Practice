package com.example.assignment16;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.graphics.Insets;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;

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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.googleMap = map;

        String url = "https://www.theappsdr.com/map/route";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.d("Error", "onResponse: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try{
                        String json = response.body().string();
                        JSONObject jsonObject = new JSONObject(json);
                        JSONArray pathArray = jsonObject.getJSONArray("path");
                        List<LatLng> points = new ArrayList<>();

                        for (int i = 0; i < pathArray.length(); i++) {
                            JSONObject point = pathArray.getJSONObject(i);
                            double lat = point.getDouble("latitude");
                            double lng = point.getDouble("longitude");
                            points.add(new LatLng(lat, lng));
                        }
                        runOnUiThread(() -> drawRoute(points));
                    } catch (JSONException e) {
                        Log.d("Error", "onResponse: " + e.getMessage());
                    }

                }else{
                    Log.d("Error", "onResponse: " + response.message());
                }


            }
        });
    }
    private void drawRoute(List<LatLng> points) {
        if (points.isEmpty() || googleMap == null) return;

        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(points)
                .clickable(false);
        googleMap.addPolyline(polylineOptions);

        googleMap.addMarker(new MarkerOptions().position(points.get(0)).title("Start"));
        googleMap.addMarker(new MarkerOptions().position(points.get(points.size() - 1)).title("End"));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng point : points) {
            builder.include(point);
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));

        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }
}

