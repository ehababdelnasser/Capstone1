package com.example.lenovo.capstone;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
/**
 * Created by Lenovo on 06/10/2017.
 */

public class MainActivity extends AppCompatActivity implements listner1 {

    RecyclerView recyclerView;
    String url = "http://api.themoviedb.org/3/movie/popular?";
    RecycleAdapter recyclerMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.recycle_viewid);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        UpdateTask(url);

    }

    @Override
    public void onTaskComplete(final ArrayList moviesObjectsArrayList) {


        recyclerMainAdapter = new RecycleAdapter((ArrayList<MovieObjects>) moviesObjectsArrayList, new RecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MovieObjects movieObjects) {

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DetailActivity.class);
                intent.putExtra("title", movieObjects.original_title);
                intent.putExtra("releaseDate", movieObjects.release_date);
                intent.putExtra("voteAvg", movieObjects.vote_average);
                intent.putExtra("image", movieObjects.movie_image);
                intent.putExtra("overview", movieObjects.overview);
                intent.putExtra("id", movieObjects.movie_id);
                MainActivity.this.startActivity(intent);
            }
        });

        recyclerView.setAdapter(recyclerMainAdapter);
        recyclerMainAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_popular:
                UpdateTask(url);
                break;
            case R.id.menue_toprated:
                UpdateTask("http://api.themoviedb.org/3/movie/top_rated?");
                break;
            case  R.id.history:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, History.class);
                startActivity(intent);
                break;
            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(MainActivity.this, LoginActivty.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private void UpdateTask(String url) {
        MovieAsyncTask moviesTask = new MovieAsyncTask(this, this);
        moviesTask.execute(url);
    }

}