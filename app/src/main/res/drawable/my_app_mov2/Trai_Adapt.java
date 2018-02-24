package com.example.lenovo.my_app_mov2;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Lenovo on 06/10/2017.
 */

public class Trai_Adapt extends BaseAdapter {

    Context c_txt1;
    List<com.example.lenovo.my_app_mov2.Opj_Mov> list1;
    private int lay_ID;


    public Trai_Adapt(Detail_class con1, int layout_id, List<com.example.lenovo.my_app_mov2.Opj_Mov> list) {
        this.c_txt1 = con1;
        this.list1 = list;
        this.lay_ID = layout_id;

    }

    public Trai_Adapt(Class<Detail_class> detail_classClass) {

    }

    @Override
    public int getCount() {
        return list1.size();
    }

    @Override
    public Object getItem(int position) {
        return list1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v("TAG","here" + list1.size());
        if (convertView == null) {

            final LayoutInflater layoutInflater = ((Activity) c_txt1).getLayoutInflater();
            convertView = layoutInflater.inflate(lay_ID, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.tv_trailerName)).setText(list1.get(position).name);

        return convertView;
    }

}
