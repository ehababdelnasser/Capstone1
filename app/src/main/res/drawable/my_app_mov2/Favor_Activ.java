package com.example.lenovo.my_app_mov2;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Lenovo on 11/10/2017.
 */

public class Favor_Activ extends AppCompatActivity implements com.example.lenovo.my_app_mov2.Favor_Adabt.interface_listner2 {

    RecyclerView rec_View;
    com.example.lenovo.my_app_mov2.Favor_Adabt adapt;

    ArrayList<com.example.lenovo.my_app_mov2.Opj_Mov> data_Att;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activ_favor);
        rec_View = (RecyclerView) findViewById(R.id.recycle_viewid2);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            rec_View.setLayoutManager(new GridLayoutManager(this, 1));
        } else {
            rec_View.setLayoutManager(new GridLayoutManager(this, 3));
        }

        data_Att = new ArrayList<com.example.lenovo.my_app_mov2.Opj_Mov>();
        getdata();

    }

    private void getdatahelper() {
        data_Att.clear();
        Cursor result = getContentResolver().query(com.example.lenovo.my_app_mov2.Conr.CONT_URI, null, null, null, null);
        com.example.lenovo.my_app_mov2.Opj_Mov tmp;
        if (result != null) {
            result.moveToFirst();
        }
        assert result != null;
        while (!result.isAfterLast()) {
            tmp = new com.example.lenovo.my_app_mov2.Opj_Mov();
            tmp.setMovie_image(result.getString(result.getColumnIndex(com.example.lenovo.my_app_mov2.Conr.CONTRACT_POSTER_PATH)));
            tmp.setOriginal_title(result.getString(result.getColumnIndex(com.example.lenovo.my_app_mov2.Conr.CONTRACT_TITLE)));
            tmp.setOverview(result.getString(result.getColumnIndex(com.example.lenovo.my_app_mov2.Conr.CONTRACT_OVERVIEW)));
            tmp.setMovie_id(result.getString(result.getColumnIndex(com.example.lenovo.my_app_mov2.Conr.CONTRACT_MOVIE_ID)));
            tmp.setRelease_date(result.getString(result.getColumnIndex(com.example.lenovo.my_app_mov2.Conr.CONTRACT_RELEASE_DATE)));
            tmp.setVote_average(result.getString(result.getColumnIndex(com.example.lenovo.my_app_mov2.Conr.CONTRACT_VOTEAVG)));
            data_Att.add(tmp);
            result.moveToNext();
        }
        Log.v("sign", data_Att.size() + "s d ");
        if (data_Att.size() != 0) {
            Log.v("signoo", data_Att.get(0).orig_tit);
        }
    }

    public void getdata() {
        getdatahelper();
        adapt = new com.example.lenovo.my_app_mov2.Favor_Adabt(this, data_Att, this);
        rec_View.setAdapter(adapt);
    }

    @Override
    public void onTaskComplete2(Object result) {
        Intent i = new Intent(com.example.lenovo.my_app_mov2.Favor_Activ.this, com.example.lenovo.my_app_mov2.Detail_class.class);
        i.putExtra("xeno", 1);
        Bundle bundle = new Bundle();
        bundle.putSerializable("value", data_Att.get((int) result));
        i.putExtras(bundle);
        startActivity(i);
    }
}