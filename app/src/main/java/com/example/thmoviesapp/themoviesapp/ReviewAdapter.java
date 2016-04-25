package com.example.thmoviesapp.themoviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Abed Eid on 23/04/2016.
 */
class ReviewAdapter extends BaseAdapter {

    Context context;
    List<Review> reviewList;

    public ReviewAdapter(Context context, List<Review> reviewList) {
        this.reviewList = reviewList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return reviewList.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie_Fragment movie_fragment = new Movie_Fragment();
        ReviewHolder holder = null;
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.review, parent, false);
            holder = new ReviewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ReviewHolder) convertView.getTag();
        }
        holder.authorTextView.setText(reviewList.get(position).author);
        holder.content.setText(reviewList.get(position).content);

        return v;
    }


}
