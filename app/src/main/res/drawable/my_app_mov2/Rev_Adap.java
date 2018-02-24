package com.example.lenovo.my_app_mov2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Lenovo on 06/10/2017.
 */

public class Rev_Adap extends BaseAdapter {

    Context c_txt2;
    List<com.example.lenovo.my_app_mov2.Opj_Mov> list2;
    private  int lay_ID;


    public Rev_Adap(Context con2, int layout_id, List<com.example.lenovo.my_app_mov2.Opj_Mov> list){
        this.c_txt2 = con2;
        this.list2 = list;
        this.lay_ID = layout_id;

    }

    @Override
    public int getCount() {
        return list2.size();
    }

    @Override
    public Object getItem(int position) {
        return list2.get(position);
    }

    @Override
    public long getItemId(int position) { return position; }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {

            final LayoutInflater layoutInflater = ((Activity)c_txt2).getLayoutInflater();
            convertView = layoutInflater.inflate(lay_ID, parent, false);
        }
        ((TextView)convertView.findViewById(R.id.text_viewAuthor)).setText(list2.get(position).author);
        ((TextView)convertView.findViewById(R.id.text_viewReview)).setText(list2.get(position).review);

        return convertView;
    }

}