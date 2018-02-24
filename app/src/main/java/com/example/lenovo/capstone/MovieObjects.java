package com.example.lenovo.capstone;

import java.io.Serializable;

/**
 * Created by Lenovo on 06/10/2017.
 */

public class MovieObjects implements Serializable {


    public String movie_id;
    public String original_title;
    public String movie_image;
    public String overview;
    public String vote_average;
    public String release_date;

    public MovieObjects(String movie_id, String original_title, String movie_image, String overview, String vote_average, String release_date) {
        this.movie_id = movie_id;
        this.original_title = original_title;
        this.movie_image = movie_image;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }

    public MovieObjects()
    {

    }
    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setMovie_image(String movie_image) {
        this.movie_image = movie_image;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
