package com.example.thmoviesapp.themoviesapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Abed Eid on 23/04/2016.
 */
public class Details_activity extends Activity {
FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_fragment);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Movie movie = (Movie) bundle.getSerializable("movie");
        manager = getFragmentManager();

       Details_fragment details_fragment = (Details_fragment) manager.findFragmentById(R.id.detailsFragment);
        details_fragment.chage(movie);



    }

}
