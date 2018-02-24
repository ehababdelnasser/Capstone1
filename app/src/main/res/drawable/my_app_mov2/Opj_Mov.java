package com.example.lenovo.my_app_mov2;

import java.io.Serializable;

/**
 * Created by Lenovo on 06/10/2017.
 */

public class Opj_Mov implements Serializable {


    public String mov_id;
    public String orig_tit;
    public String mov_imag;
    public String ov_vw;
    public String vot_avg;
    public String rel_date;
    String author;
    String review;
    String key;
    String name;




    public Opj_Mov(String movie_id, String original_title, String movie_image, String overview, String vote_average, String release_date ) {
        this.mov_id = movie_id;
        this.orig_tit = original_title;
        this.mov_imag = movie_image;
        this.ov_vw = overview;
        this.vot_avg = vote_average;
        this.rel_date = release_date;

    }

    public Opj_Mov()
    {

    }
    public void setMovie_id(String movie_id) {
        this.mov_id = movie_id;
    }

    public void setOriginal_title(String original_title) {
        this.orig_tit = original_title;
    }

    public void setMovie_image(String movie_image) {
        this.mov_imag = movie_image;
    }

    public void setOverview(String overview) {
        this.ov_vw = overview;
    }

    public void setVote_average(String vote_average) {
        this.vot_avg = vote_average;
    }

    public void setRelease_date(String release_date) {
        this.rel_date = release_date;
    }


}
