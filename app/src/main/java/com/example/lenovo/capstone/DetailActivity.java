package com.example.lenovo.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Lenovo on 06/10/2017.
 */

public class DetailActivity extends AppCompatActivity {


    ImageView imageView;
    TextView titleTextView, releaseDateTextView, voteAvgTextView, overviewTextView;
   FloatingActionButton floatingActionButton ;


    private MovieObjects movieObjects;
    int flag = 0;


    String movieID, movieTitle, movieImage, movieOverview, voteAverage, release_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_main);



        imageView = (ImageView) findViewById(R.id.mov_imag);
        titleTextView = (TextView) findViewById(R.id.t_v_mov_na);
        releaseDateTextView = (TextView) findViewById(R.id.txt_view_date);
        voteAvgTextView = (TextView) findViewById(R.id.txt_view_vot);
        overviewTextView = (TextView) findViewById(R.id.text_view_over);
       floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        try {
            flag = getIntent().getExtras().getInt("xeno", 0);
        } catch (Exception e) {
        }
        if (flag == 0) {
            movieTitle = getIntent().getExtras().getString("title");
            titleTextView.setText(movieTitle);

            release_date = getIntent().getExtras().getString("releaseDate");
            releaseDateTextView.setText(release_date);

            voteAverage = getIntent().getExtras().getString("voteAvg");
            voteAvgTextView.setText(voteAverage);

            movieOverview = getIntent().getExtras().getString("overview");
            overviewTextView.setText(movieOverview);

            movieID = (getIntent().getExtras().getString("id"));

            movieImage = getIntent().getExtras().getString("image");
            Picasso.with(this).load("http://image.tmdb.org/t/p/w185/" + movieImage)
                    .into(imageView);








        }//if
        else {

            Bundle bundle = getIntent().getExtras();
            bundle.getSerializable("value");
            movieObjects = (MovieObjects) bundle.getSerializable("value");
            titleTextView.setText(movieObjects.original_title);
            releaseDateTextView.setText(movieObjects.release_date);
            voteAvgTextView.setText(movieObjects.vote_average);
            overviewTextView.setText(movieObjects.overview);
            Picasso.with(this).load("http://image.tmdb.org/t/p/w185/" + movieObjects.movie_image)
                    .into(imageView);

        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this,History.class);
                startActivity(intent);
            }
        });
    }


}