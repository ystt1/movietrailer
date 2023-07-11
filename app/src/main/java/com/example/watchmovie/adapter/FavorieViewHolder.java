package com.example.watchmovie.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchmovie.R;

public class FavorieViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView,menu;
    TextView textView,iYeuThich,iRating;
    public FavorieViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.favorite_movie_img);
        textView=itemView.findViewById(R.id.favorite_movie_name);
        iYeuThich=itemView.findViewById(R.id.tv_luotThich);
        iRating=itemView.findViewById(R.id.tv_rating);
        menu=itemView.findViewById(R.id.menu_favorite);
    }
}
