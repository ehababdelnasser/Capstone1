package com.example.lenovo.my_app_mov2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Lenovo on 06/10/2017.
 */


public class Rec_Adapter extends RecyclerView.Adapter<com.example.lenovo.my_app_mov2.Rec_Adapter.ViewHolder> {

    public interface OnItemClickListener1 {
        void onItemClick(com.example.lenovo.my_app_mov2.Opj_Mov item);

    }

    private List<com.example.lenovo.my_app_mov2.Opj_Mov> mov_Obj;
    private final OnItemClickListener1 listen1;

    public Rec_Adapter(List<com.example.lenovo.my_app_mov2.Opj_Mov> movieObjects, OnItemClickListener1 listner)
    {
        this.mov_Obj = movieObjects;
        this.listen1 = listner;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        com.example.lenovo.my_app_mov2.Opj_Mov movieObjects = mov_Obj.get(position);
        holder.bind(movieObjects, listen1);
    }

    @Override
    public int getItemCount() {
        if (mov_Obj == null) {
            return 0;
        }
        return mov_Obj.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_id);
        }

        public void bind(final com.example.lenovo.my_app_mov2.Opj_Mov item, final OnItemClickListener1 listener) {

            final Context context = itemView.getContext();
            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/w185/" + item.mov_imag)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.warning)
                    .into(imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}