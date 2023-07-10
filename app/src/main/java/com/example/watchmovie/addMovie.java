package com.example.watchmovie;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.watchmovie.BienToanCuc.BienToanCuc;
import com.example.watchmovie.DAO.CateItemDAO;
import com.example.watchmovie.adapter.MovieRecycler;
import com.example.watchmovie.model.CateItem;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class addMovie extends AppCompatActivity {

    Context context=this;
    RecyclerView movieRecycler;
    MovieRecycler movieAdapter;
    List<CateItem> cateItemList=new ArrayList<>();
    CateItemDAO cateItemDAO;
    int cateId=0;

    SearchView searchMovie;

    Button showDialogbtn,confirmAdd;
    ImageView addBanner;
    TextInputEditText bannerInput,trailerInput,nameInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        if(BienToanCuc.getInstance().getLoggedInUserID()==-1)
        {
            Intent i=new Intent(this,LoginAcivity.class);
            startActivity(i);
        }else {
            AnhXaVaKhaiBao();
            setMovieAdapter(cateItemList);
            setOnClickAddMovie();
        }
    }

    void setMovieAdapter(List<CateItem> cateItemList)
    {

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        movieRecycler.setLayoutManager(layoutManager);
        movieAdapter=new MovieRecycler(this,cateItemList,cateId);
        movieRecycler.setAdapter(movieAdapter);
        setSearchMovieChange();
    }

    void setOnClickAddMovie()
    {
        showDialogbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.add_movie_dialog);
                nameInput=dialog.findViewById(R.id.input_movie_name);
                bannerInput=dialog.findViewById(R.id.input_banner);
                trailerInput=dialog.findViewById(R.id.input_trailer);
                confirmAdd=dialog.findViewById(R.id.confirm_add_movie_btn);
                addBanner=dialog.findViewById(R.id.banner_add);

                confirmAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Glide.with(context).load(String.valueOf(bannerInput.getText()))
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        Toast.makeText(context, "Url Banner không hợp lệ", Toast.LENGTH_SHORT).show();

                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                        int id=cateItemDAO.getMaxId();
                                        Log.e("tag", String.valueOf(id));
                                        if (URLUtil.isValidUrl(String.valueOf(trailerInput.getText())) && id!=-1) {

                                            CateItem cateItem=new CateItem(id+1,String.valueOf(nameInput.getText()),String.valueOf(bannerInput.getText()),String.valueOf(trailerInput.getText()),cateId);
                                            cateItemDAO.addCateItem(cateItem);
                                            cateItemList.clear();
                                            cateItemList=cateItemDAO.getListCateItemWithCateId(cateId);
                                            setMovieAdapter(cateItemList);
                                            dialog.cancel();
                                        } else {
                                            Toast.makeText(context, "Có gì đó không hợp lệ", Toast.LENGTH_SHORT).show();
                                        }
                                        return false;
                                    }
                                })
                                .into(addBanner);
                    }
                });
                dialog.show();
            }
        });
    }

    void setSearchMovieChange()
    {
        searchMovie.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                    return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                cateItemList=cateItemDAO.getListItemNameLike(s);
                setMovieAdapter(cateItemList);
                return false;
            }
        });
    }
    void AnhXaVaKhaiBao()
    {
        showDialogbtn=findViewById(R.id.add_movie);
        movieRecycler=findViewById(R.id.movie_recycler);
        cateId= getIntent().getIntExtra("categoryId",0);
        searchMovie=findViewById(R.id.search_movie);
        cateItemDAO=new CateItemDAO(context);

        cateItemList.clear();
        cateItemList=cateItemDAO.getListCateItemWithCateId(cateId);
    }
}