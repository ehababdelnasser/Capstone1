package com.example.lenovo.my_app_mov2;

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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lenovo on 06/10/2017.
 */

public class MainActivity extends AppCompatActivity implements Mov_Async_Task.interface_listner1 {

    @BindView(R.id.recycle_viewid)
    RecyclerView rec_View;
    String url = "http://api.themoviedb.org/3/movie/popular?";
    Rec_Adapter rec_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            rec_View.setLayoutManager(new GridLayoutManager(this, 2));
        }
        else{
            rec_View.setLayoutManager(new GridLayoutManager(this, 4));
        }

        UpdateTask(url);

    }

    @Override
    public void onTaskComplete(final ArrayList moviesObjectsArrayList) {


        rec_adapter = new Rec_Adapter((ArrayList<Opj_Mov>) moviesObjectsArrayList, new Rec_Adapter.OnItemClickListener1() {
            @Override
            public void onItemClick(Opj_Mov movieObjects) {

                Intent intent = new Intent();
                intent.setClass(com.example.lenovo.my_app_mov2.MainActivity.this, Detail_class.class);
                intent.putExtra("title", movieObjects.orig_tit);
                intent.putExtra("releaseDate", movieObjects.rel_date);
                intent.putExtra("voteAvg", movieObjects.vot_avg);
                intent.putExtra("image", movieObjects.mov_imag);
                intent.putExtra("overview", movieObjects.ov_vw);
                intent.putExtra("id", movieObjects.mov_id);

                com.example.lenovo.my_app_mov2.MainActivity.this.startActivity(intent);
            }
        });

        rec_View.setAdapter(rec_adapter);
        rec_adapter.notifyDataSetChanged();
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
                intent.setClass(com.example.lenovo.my_app_mov2.MainActivity.this, History.class);
                startActivity(intent);
                break;
            case R.id.Logout:
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(com.example.lenovo.my_app_mov2.MainActivity.this, LoginActivty.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    private void UpdateTask(String url) {
        Mov_Async_Task moviesTask = new Mov_Async_Task(this, this);
        moviesTask.execute(url);
    }

}