package com.example.thmoviesapp.themoviesapp;

import java.io.Serializable;

/**
 * Created by Abed Eid on 22/04/2016.
 */
public class Movie implements Serializable {
    String posterURL;
    String title;
    String releaseDate;
    int time;
    String overview;
    int id;
    double vote_average;


    public void setDetails(int id,String Poster_path, String title, String overview, String releaseDate, int time, double vote_average) {
        this.id=id;
        this.posterURL=Poster_path;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.time = time;
        this.vote_average = vote_average;
    }

    public void setURL(String posterURL, int id) {
        this.posterURL = posterURL;
        this.id = id;
    }


}
