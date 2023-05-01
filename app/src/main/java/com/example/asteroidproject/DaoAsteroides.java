package com.example.asteroidproject;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DaoAsteroides extends AsyncTask<Void, Void, String> {
    DaoUsuario dao;
    private static final String URL = "https://api.nasa.gov/neo/rest/v1/feed?start_date=2023-04-27&end_date=2023-05-04&api_key=lINjJNUxNmBc3nsp2I8FNY9Czmf6fujIrGb75wPa";

    private Exception exception;

    @Override
    protected String doInBackground(Void... voids) {
            try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
            .url(URL)
            .method("GET", null)
            .build();
            Response response = client.newCall(request).execute();

            return response.body().string();
            } catch (Exception e) {
            this.exception = e;
            return null;
            }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (this.exception != null) {
            // Handle the exception
        } else {
            try {
                JSONObject root = new JSONObject(result);
                JSONObject nearEarthObjects = root.getJSONObject("near_earth_objects");
                Iterator<String> dateKeys = nearEarthObjects.keys();
                List<Asteroide> asteroides = new ArrayList<>();
                while (dateKeys.hasNext()) {
                    String date = dateKeys.next();
                    JSONArray asteroidsArray = nearEarthObjects.getJSONArray(date);
                    // procesar los asteroides del día actual
                    for (int i = 0; i < asteroidsArray.length(); i++) {
                        JSONObject asteroidObj = asteroidsArray.getJSONObject(i);
                        Asteroide asteroide = new Asteroide();
                        asteroide.setNeo_reference_id(asteroidObj.getString("neo_reference_id"));
                        asteroide.setName(asteroidObj.getString("name"));
                        asteroide.setNasa_jpl_url(asteroidObj.getString("nasa_jpl_url"));
                        asteroide.setAbsolute_magnitude(asteroidObj.getDouble("absolute_magnitude_h"));
                        asteroide.setEstimated_diameter_min(asteroidObj.getDouble("estimated_diameter_min"));
                        asteroide.setEstimated_diameter_max(asteroidObj.getDouble("estimated_diameter_max"));
                        asteroide.setMiss_distance(asteroidObj.getDouble("miss_distance"));
                        asteroide.setVelocity(asteroidObj.getDouble("velocity"));
                        asteroide.setHazardous(asteroidObj.getBoolean("is_potentially_hazardous_asteroid"));
                        asteroides.add(asteroide);
                    }
                }
                dao.insertAsteroides((Asteroide) asteroides);
                // Hacer algo con la lista de asteroides (por ejemplo, pasarla a un adaptador para mostrarla en una vista)
            } catch (JSONException e) {
                // Manejar la excepción de manera apropiada (por ejemplo, mostrando un mensaje de error al usuario)
            }
        }
    }

}