package com.example.lenovo.my_app_mov2;

import android.net.Uri;

/**
 * Created by Lenovo on 06/10/2017.
 */

public class Conr {

    public static final String Prov_N = "com.example.lenovo.my_app_mov1";
    public static final Uri Base_Con_Uri = Uri.parse("content://" + Prov_N);
    public static final String con_pth = "data";
    public static final Uri CONT_URI = Base_Con_Uri.buildUpon().appendPath(con_pth).build();


    public static final String B_id = "ID";
    public static final String CONTRACT_OVERVIEW = "COVERVIEW";
    public static final String CONTRACT_TITLE = "CTITLE";
    public static final String CONTRACT_RELEASE_DATE = "CRELEASEDATE";
    public static final String CONTRACT_POSTER_PATH = "CPOSTERPATH";
    public static final String CONTRACT_MOVIE_ID = "CMOVIEID";
    public static final String CONTRACT_VOTEAVG = "VOTEAVG";

}

