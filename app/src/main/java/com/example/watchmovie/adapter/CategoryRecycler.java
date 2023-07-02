package com.example.watchmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    }

    @Override
    public int getItemCount() {
        return cateList.size();
    }
}
