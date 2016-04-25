package com.example.thmoviesapp.themoviesapp;

import android.view.View;
import android.widget.TextView;

/**
 * Created by Abed Eid on 23/04/2016.
 */
public class ReviewHolder {


    TextView content;
    TextView authorTextView;
    ReviewHolder(View v){
        content = (TextView) v.findViewById(R.id.review);
        authorTextView = (TextView) v.findViewById(R.id.author);

    }
}
