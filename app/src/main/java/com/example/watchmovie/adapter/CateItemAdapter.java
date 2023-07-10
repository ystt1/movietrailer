package com.example.watchmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.watchmovie.MovieDetails;
import com.example.watchmovie.R;
import com.example.watchmovie.model.CateItem;

import java.util.ArrayList;
import java.util.List;

public class CateItemAdapter extends RecyclerView.Adapter<CateItemAdapter.ItemViewHolder> {
    Context context;
    List<CateItem> cateItemList=new ArrayList<>();

    public CateItemAdapter(Context context, List<CateItem> cateItemList) {
        this.cateItemList.clear();
        this.context = context;
        this.cateItemList = cateItemList;

    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.cate_recycler_rows_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Glide.with(context).load(cateItemList.get(position).getImgUrl()).into(holder.imgItem);
        int flag=position;
        holder.imgItem.setOnClickListener(new View.OnClickListener() {
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
        return cateItemList.size() ;
    }

    public  static  final  class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView imgItem;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem=itemView.findViewById(R.id.cateItemView);
        }
    }



}
