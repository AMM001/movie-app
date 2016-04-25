package com.example.thmoviesapp.themoviesapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abed Eid on 23/04/2016.
 */
public class FetchTrailers extends AsyncTask<Integer, Void, List<Trailer>> {

    @Override
    protected List<Trailer> doInBackground(Integer... params) {
        String base = "https://api.themoviedb.org/3/movie/" + params[0] + "/videos?";
        String json = getJSON(base);

        return getTrailers(json);
    }

    private List<Trailer> getTrailers(String json) {
        List<Trailer> trailers = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject resultsJSONObject = results.getJSONObject(i);
                String key = resultsJSONObject.getString("key");
                String name = resultsJSONObject.getString("name");
                Trailer trailer = new Trailer(name, "https://www.youtube.com/watch?v=" + key);
                trailers.add(trailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailers;
    }

    public String getJSON(String base) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonstr = null;


        String api_key = "78bf6a2ef253cfbbb8e3067ab8956a4b";
        int page = 1;
        try {
            String baseUrl = base;

            final String API_param = "api_key";
            Uri builtUri = Uri.parse(baseUrl).buildUpon()
                    .appendQueryParameter(API_param, api_key).build();

            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream == null) {
                return null;
            }

            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            jsonstr = buffer.toString();
            Log.d("json", jsonstr.toString());
            return jsonstr.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}