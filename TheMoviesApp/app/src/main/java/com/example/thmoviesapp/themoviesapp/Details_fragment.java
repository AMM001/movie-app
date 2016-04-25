package com.example.thmoviesapp.themoviesapp;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.linearlistview.LinearListView;
import com.squareup.picasso.Picasso;

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
import java.util.concurrent.ExecutionException;

/**
 * Created by Abed Eid on 23/04/2016.
 */
public class Details_fragment extends Fragment implements View.OnClickListener, LinearListView.OnItemClickListener {
    TextView title;
    ImageView poster;
    TextView releasedTime;
    TextView time;
    Button favourite;
    TextView overview;
    Movie movie;
    TextView voteAverage;
    LinearListView trailers;
    LinearListView reviewsListView;
    List<Trailer> trailerList;
    List<Review> reviewList;
    DataBaseConnection db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movies_details, container, false);
        title = (TextView) v.findViewById(R.id.Title);
        releasedTime = (TextView) v.findViewById(R.id.ReleasedTime);
        time = (TextView) v.findViewById(R.id.Time);
        overview = (TextView) v.findViewById(R.id.Overview);
        poster = (ImageView) v.findViewById(R.id.image);
        favourite = (Button) v.findViewById(R.id.favourite_Btn);
        favourite.setOnClickListener(this);
        trailers = (LinearListView) v.findViewById(R.id.trailersList);
        reviewsListView = (LinearListView) v.findViewById(R.id.reviewsList);
        voteAverage = (TextView) v.findViewById(R.id.VoteAverage);
        db = new DataBaseConnection(getActivity());
        return v;
    }


    public void chage(Movie movie) {

       if(movie == null){
           title.setText("");
           releasedTime.setText("");
           time.setText("");
           overview.setText("");
           voteAverage.setText("");
           //Adapter adapter = new ArrayAdapter<>();
           trailers.setAdapter(null);
           reviewsListView.setAdapter(null);
           poster.setImageBitmap(null);
           return;
       }
        int flag = db.search(movie.id);
        if (flag == 1) {
            favourite.setBackgroundColor(Color.parseColor("#2ED3FC"));
            favourite.setText("Make as Unfavourite");
        }


        Fetch_Details fetch_movies = new Fetch_Details();
        try {
            movie = fetch_movies.execute(movie.id + "").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        this.movie = movie;
        title.setText(movie.title);
        releasedTime.setText(movie.releaseDate.substring(0, 4));
        time.setText(movie.time + " min");
        overview.setText(movie.overview);
        voteAverage.setText(movie.vote_average + "");
        Picasso.with(getActivity()).load(movie.posterURL).into(poster);
        FetchTrailers fetchTrailers = new FetchTrailers();
        trailerList = null;
        try {
            trailerList = fetchTrailers.execute(movie.id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        List<String> trailersNames = new ArrayList<>();
        for (Trailer t : trailerList) {
            trailersNames.add(t.name);
        }

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.trailer, R.id.trailer, trailersNames);
        trailers.setAdapter(adapter);
        trailers.setOnItemClickListener(this);
        FetchReviews fetchReviews = new FetchReviews();
        reviewList = null;
        try {
            reviewList = fetchReviews.execute(movie.id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        List<Review> reviews = new ArrayList<>();

        for (Review r : reviewList) {
            reviews.add(new Review(r.author, r.content));
        }


        ReviewAdapter reviewAdapter = new ReviewAdapter(getActivity(), reviews);
        reviewsListView.setAdapter(reviewAdapter);
        reviewsListView.setOnItemClickListener(this);
    }


    public void onItemClick(LinearListView parent, View view, int position, long id) {
        if (parent.getId() == R.id.trailersList) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerList.get(position).url));
            intent = Intent.createChooser(intent, "");
            startActivity(intent);
        }
    }

    MenuItem item;

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.favourite_Btn) {

            int flag = db.search(movie.id);


            if (flag == 0) {
                long id = db.insert(movie.id, movie.posterURL);
                if (id < 0) {
                    Toast.makeText(getActivity(), "Database Not inserted ", Toast.LENGTH_SHORT).show();
                } else {
                }
                favourite.setText("Make as Unfavourite");

                Toast.makeText(getActivity(), "Favourite", Toast.LENGTH_SHORT).show();
                favourite.setBackgroundColor(Color.parseColor("#2ED3FC"));
            } else if (flag == 1) {
                favourite.setText("Make as favourite");

                db.deleteName(movie.id);
                favourite.setBackgroundColor(Color.parseColor("#4c7bf8"));

                Toast.makeText(getActivity(), "Not Favourite", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

class Fetch_Details extends AsyncTask<String, Void, Movie> {


    @Override
    protected Movie doInBackground(String[]  params) {

            String baseUrl = "https://api.themoviedb.org/3/movie/" + params[0] + "?";

            Movie movie = get_json_Details(get_json(baseUrl));


            return movie;

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

    public Movie get_json_Details(String jsonstring) {
        Movie movie = new Movie();
        try {
            JSONObject jsonObject = new JSONObject(jsonstring);
            String poster = jsonObject.getString("poster_path");
            int id = jsonObject.getInt("id");
            String title = jsonObject.getString("original_title");
            String overview = jsonObject.getString("overview");
            String releaseDate = jsonObject.getString("release_date");
            int time = jsonObject.getInt("runtime");
            double vote_average = jsonObject.getDouble("vote_average");
            movie.setDetails(id, "http://image.tmdb.org/t/p/w320/" + poster, title, overview, releaseDate, time, vote_average);
            return movie;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

}
