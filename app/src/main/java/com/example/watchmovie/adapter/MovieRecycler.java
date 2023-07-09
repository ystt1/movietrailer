package com.example.watchmovie.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.watchmovie.R;
import com.example.watchmovie.model.CateItem;

import java.util.List;

public class MovieRecycler extends  RecyclerView.Adapter<MovieViewHolder>{

    public MovieRecycler(Context context, List<CateItem> cateItemList) {
        this.context = context;
        this.cateItemList = cateItemList;
    }

    Context context;
    List<CateItem> cateItemList;

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_name,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        int flag=position;
        holder.movieName.setText(cateItemList.get(flag).getMovieName());
        Glide.with(context).load(cateItemList.get(flag).getImgUrl()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return cateItemList.size();
    }
}
