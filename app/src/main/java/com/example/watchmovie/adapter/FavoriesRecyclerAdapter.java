package com.example.watchmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.watchmovie.MovieDetails;
import com.example.watchmovie.R;
import com.example.watchmovie.model.AnhBia;
import com.example.watchmovie.model.CateItem;

import java.util.List;

public class FavoriesRecyclerAdapter extends RecyclerView.Adapter<FavorieViewHolder> {

    Context context;
    List<CateItem> cateItemList;

    public FavoriesRecyclerAdapter(Context context, List<CateItem> cateItemList) {
        this.context = context;
        this.cateItemList = cateItemList;
    }

    @NonNull
    @Override
    public FavorieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavorieViewHolder(LayoutInflater.from(context).inflate(R.layout.favorites_recycler,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavorieViewHolder holder, int position) {
        int flag=position;
        holder.textView.setText(cateItemList.get(flag).getMovieName());
        Glide.with(context).load(cateItemList.get(flag).getImgUrl()).into(holder.imageView);

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, MovieDetails.class);
                i.putExtra("movieId",cateItemList.get(flag).getId());
                i.putExtra("movieName",cateItemList.get(flag).getMovieName());
                i.putExtra("movieImageUrl",cateItemList.get(flag).getImgUrl());
                i.putExtra("movieFileUrl",cateItemList.get(flag).getFileurl());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cateItemList.size();
    }
}
