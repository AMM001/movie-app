package com.example.thmoviesapp.themoviesapp;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Communicator_Interface {
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getFragmentManager();
    }

    @Override
    public void SendMovie(Movie movie) {
        Details_fragment details_fragment = (Details_fragment) manager.findFragmentById(R.id.detailsFragment);
        if (details_fragment != null ) {
            details_fragment.chage(movie);
        } else {
            Intent intent = new Intent(this, Details_activity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("movie", movie);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }


}
