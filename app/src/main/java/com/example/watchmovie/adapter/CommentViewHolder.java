package com.example.watchmovie.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.watchmovie.R;

public class CommentViewHolder extends RecyclerView.ViewHolder {
    TextView comment;
    TextView displayName;

    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);
        comment=itemView.findViewById(R.id.tvComment);
        displayName=itemView.findViewById(R.id.tvUser);
    }
}
