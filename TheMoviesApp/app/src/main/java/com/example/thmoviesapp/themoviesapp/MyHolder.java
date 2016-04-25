package com.example.thmoviesapp.themoviesapp;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by Abed Eid on 22/04/2016.
 */
public class MyHolder {
    ImageView imageView;

    public MyHolder(View v) {
        this.imageView = (ImageView) v.findViewById(R.id.movie);
    }
}
