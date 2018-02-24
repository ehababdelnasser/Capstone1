package com.example.lenovo.capstone;

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


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(MovieObjects item);

    }

    private List<MovieObjects> movieObjes;
    private final OnItemClickListener listener;

    public RecycleAdapter(List<MovieObjects> movieObjects, OnItemClickListener listner)
    {
        this.movieObjes = movieObjects;
        this.listener = listner;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MovieObjects movieObjects = movieObjes.get(position);
        holder.bind(movieObjects, listener);
    }

    @Override
    public int getItemCount() {
        if (movieObjes == null) {
            return 0;
        }
        return movieObjes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_id);
        }

        public void bind(final MovieObjects item, final OnItemClickListener listener) {

            final Context context = itemView.getContext();
            Picasso.with(context)
                    .load("http://image.tmdb.org/t/p/w185/" + item.movie_image)
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