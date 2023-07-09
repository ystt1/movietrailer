package com.example.watchmovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.watchmovie.addMovie;
import com.example.watchmovie.R;
import com.example.watchmovie.model.AllCate;

import java.util.List;

public class CategoryRecycler extends RecyclerView.Adapter<CategoryViewHolder> {


    Context context;
    List<AllCate> cateList;

    public CategoryRecycler(Context context, List<AllCate> cateList) {
        this.context = context;
        this.cateList = cateList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_name,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        int flag=position;
        holder.cateName.setText(cateList.get(flag).getCateTitle());
        holder.cateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,addMovie.class);
                i.putExtra("categoryId",cateList.get(flag).getCateId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cateList.size();
    }
}
