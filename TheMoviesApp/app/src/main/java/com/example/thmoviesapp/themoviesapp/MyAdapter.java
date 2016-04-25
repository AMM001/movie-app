package com.example.thmoviesapp.themoviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by Abed Eid on 22/04/2016.
 */
public class MyAdapter extends BaseAdapter {
    List<Movie> movies;
    Context context;

    public MyAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View poster = convertView;
        MyHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            poster = (ImageView) inflater.inflate(R.layout.grid_item, parent, false);
            holder = new MyHolder(poster);
            poster.setTag(holder);
        } else {
            holder = (MyHolder) poster.getTag();
        }
//        ImageView imm = holder.imageView;
        Picasso.with(context).load(movies.get(position).posterURL).into(holder.imageView);
        return poster;
    }

}
