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
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.watchmovie.DAO.CateItemDAO;
import com.example.watchmovie.MovieDetails;
import com.example.watchmovie.R;
import com.example.watchmovie.model.AnhBia;
import com.example.watchmovie.model.CateItem;

import java.util.ArrayList;
import java.util.List;

public class BannerMovieViewPager2Adapter extends PagerAdapter {


    Context context;
    List<CateItem> cateItemList;

    public BannerMovieViewPager2Adapter(Context context, List<CateItem> cateItemList) {
        this.context = context;
        this.cateItemList = cateItemList;
    }

    @Override
    public int getCount() {
        return cateItemList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(context).inflate(R.layout.banner_movie_layout,null);
        ImageView anhbiaImg=view.findViewById(R.id.banner_image);
        Glide.with(context).load(cateItemList.get(position).getImgUrl()).into(anhbiaImg);
        container.addView(view);

        anhbiaImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, MovieDetails.class);
                i.putExtra("movieId",String.valueOf(cateItemList.get(position).getId()));
                i.putExtra("movieName",cateItemList.get(position).getMovieName());
                i.putExtra("movieImageUrl",cateItemList.get(position).getImgUrl());
                i.putExtra("movieFileUrl",cateItemList.get(position).getFileurl());
                context.startActivity(i);
            }
        });
        return view;
    }
}
