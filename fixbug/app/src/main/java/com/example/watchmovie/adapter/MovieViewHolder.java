package com.example.watchmovie.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchmovie.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    TextView movieName;
    ImageView img;

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);
        movieName=itemView.findViewById(R.id.name_movie);
        img=itemView.findViewById(R.id.add_movie_img);
    }
}
