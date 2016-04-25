package com.example.thmoviesapp.themoviesapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Abed Eid on 22/04/2016.
 */
public class Movie_Fragment extends Fragment implements AdapterView.OnItemClickListener {
    Fetch_Movies fetchMovie;
    GridView gridView = null;

    Movie movie;

    Communicator_Interface communicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        communicator = (Communicator_Interface) getActivity();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (Communicator_Interface) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.grid_view, container, false);
        gridView = (GridView) v.findViewById(R.id.grid_view);

        return v;

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.popular) {
            getMovies("popular");
        } else if (item.getItemId() == R.id.top_rated) {
            getMovies("top_rated");
        } else if (item.getItemId() == R.id.favourite) {
            DataBaseConnection db = new DataBaseConnection(getActivity());
            movies = db.getmovie();
            MyAdapter adapter = new MyAdapter(getActivity(), movies);
            gridView.setAdapter(adapter);
            if(((MainActivity)communicator).manager.findFragmentById(R.id.detailsFragment) != null) {
                if (movies != null && movies.size() > 0) {
                    communicator.SendMovie(movies.get(0));
                } else {
                    communicator.SendMovie(null);
                }
            }
        }
        return true;
    }

    List<Movie> movies;
    MyAdapter adapter;

    void getMovies(String type) {

        fetchMovie = new Fetch_Movies(getActivity(), movies);
        movies = null;
        try {
            movies = fetchMovie.execute(type).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(((MainActivity)communicator).manager.findFragmentById(R.id.detailsFragment) != null) {
            communicator.SendMovie(movies.get(0));
        }
        adapter = new MyAdapter(getActivity(), movies);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getMovies("popular");
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        communicator.SendMovie(movies.get(position));
    }
}

interface Communicator_Interface {
    public void SendMovie(Movie movie);
}
