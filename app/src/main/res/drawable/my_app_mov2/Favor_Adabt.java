package com.example.lenovo.my_app_mov2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Lenovo on 11/10/2017.
 */

class Favor_Adabt extends RecyclerView.Adapter<com.example.lenovo.my_app_mov2.Favor_Adabt.MainVIewHOlder> {

    private Context Con3;
    private ArrayList<com.example.lenovo.my_app_mov2.Opj_Mov> img;
    private interface_listner2 on_List_Click;

    public interface interface_listner2 {
        void onTaskComplete2(Object result);
    }



    Favor_Adabt(Context C3, ArrayList<com.example.lenovo.my_app_mov2.Opj_Mov> D, interface_listner2 onListItemClick) {
        this.Con3 = C3;
        img = D;
        this.on_List_Click = onListItemClick;
    }


    @Override
    public MainVIewHOlder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_main, parent, false);
        return new com.example.lenovo.my_app_mov2.Favor_Adabt.MainVIewHOlder(view);
    }

    @Override
    public void onBindViewHolder(MainVIewHOlder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (img == null) {
            return 0;
        }
        return img.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class MainVIewHOlder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View v;
        ImageView img_view;

        private MainVIewHOlder(View itemView) {
            super(itemView);
            v = itemView;
            img_view = (ImageView) v.findViewById(R.id.mov_imag);
            img_view.setOnClickListener(this);


        }

        void bind(int position) {
            Picasso.with(Con3).load("http://image.tmdb.org/t/p/w185/" + (img.get(position).mov_imag))
                    .into(img_view);

        }

        @Override
        public void onClick(View v) {

            int Clickpos = getAdapterPosition();
            on_List_Click.onTaskComplete2(Clickpos);
        }
    }
}
