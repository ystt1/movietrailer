package com.example.watchmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.watchmovie.BienToanCuc.BienToanCuc;
import com.example.watchmovie.DAO.AnhBiaDAO;
import com.example.watchmovie.DAO.CateItemDAO;
import com.example.watchmovie.DAO.InteractionDAO;
import com.example.watchmovie.adapter.BannerMovieViewPager2Adapter;
import com.example.watchmovie.adapter.FavoriesRecyclerAdapter;
import com.example.watchmovie.model.AnhBia;
import com.example.watchmovie.model.CateItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Favorite extends AppCompatActivity {

    InteractionDAO interactionDAO;
    RecyclerView recyclerView;
    FavoriesRecyclerAdapter favoriesRecyclerAdapter;
    ImageView imageView;

    CateItemDAO cateItemDAO;
    List<CateItem> cateItemList=new ArrayList<>();
    Context context=this;
    TextView textView;
    int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        idUser=BienToanCuc.getInstance().getLoggedInUserID();
        interactionDAO=new InteractionDAO(context);
        textView=findViewById(R.id.tvAct);
        cateItemDAO=new CateItemDAO(context);
        cateItemList.clear();
        int type= getIntent().getIntExtra("type",0);
        String key= getIntent().getStringExtra("key");
        if(type==0)
        {
            cateItemList=interactionDAO.getListMovieYeuThich(idUser);
        }
        else
        {
            textView.setText("Tìm kiếm");
            cateItemList=cateItemDAO.getListItemNameLike(key);
        }
        setFavoritesRecyclerAdapter(cateItemList);
        onClickImgBackBtn(); // nhan vao nut quy lai
    }

    void onClickImgBackBtn()
    {
        imageView=findViewById(R.id.back_button_favorite);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context,MainActivity.class);
                startActivity(i);
            }
        });
    }
    void setFavoritesRecyclerAdapter(List<CateItem> cateItemList)
    {
        recyclerView=findViewById(R.id.favories_recycler);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        favoriesRecyclerAdapter=new FavoriesRecyclerAdapter(this,cateItemList);
        recyclerView.setAdapter(favoriesRecyclerAdapter);

    }
}