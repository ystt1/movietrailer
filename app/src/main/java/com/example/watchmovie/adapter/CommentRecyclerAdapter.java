package com.example.watchmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchmovie.R;
import com.example.watchmovie.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    public CommentRecyclerAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    Context context;

    List<Comment> commentList;


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_recycler,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.comment.setText(commentList.get(position).getComment());
        holder.displayName.setText(commentList.get(position).getDisplayName());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
