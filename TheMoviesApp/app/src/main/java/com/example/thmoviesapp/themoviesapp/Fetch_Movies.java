package com.example.thmoviesapp.themoviesapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

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
 * Created by Abed Eid on 22/04/2016.
 */
public class Fetch_Movies extends AsyncTask<String, Void, List<Movie>> {

    List<Movie> movies;
    Context context;

    public Fetch_Movies(Context context, List<Movie> movies) {
        this.movies = movies;
        this.context = context;
    }

    @Override
    protected List<Movie> doInBackground(String[] params) {
        String baseUrl = "https://api.themoviedb.org/3/movie/" + params[0] + "?";
        return getMovieURL(get_json(baseUrl));
    }


    public String get_json(String base) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonstring = null;
        String api_key = "78bf6a2ef253cfbbb8e3067ab8956a4b";
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
            jsonstring = buffer.toString();

            return jsonstring.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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


    public List<Movie> getMovieURL(String jsonString) {
        movies = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject resultObject = results.getJSONObject(i);
                String poster = resultObject.getString("poster_path");
                int id = resultObject.getInt("id");
                Movie movie = new Movie();
                movie.setURL("http://image.tmdb.org/t/p/w500/" + poster, id);
                movies.add(movie);
            }
            return movies;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


}
