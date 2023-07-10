package com.example.watchmovie.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchmovie.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    TextView cateName;
    ImageView menu;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        cateName=itemView.findViewById(R.id.name_cate);
        menu=itemView.findViewById(R.id.cate_menu_item);
    }
}
