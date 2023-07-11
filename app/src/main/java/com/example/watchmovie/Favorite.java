package com.example.watchmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.watchmovie.BienToanCuc.BienToanCuc;
import com.example.watchmovie.DAO.AnhBiaDAO;
import com.example.watchmovie.DAO.CateDAO;
import com.example.watchmovie.DAO.CateItemDAO;
import com.example.watchmovie.DAO.InteractionDAO;
import com.example.watchmovie.adapter.BannerMovieViewPager2Adapter;
import com.example.watchmovie.adapter.FavoriesRecyclerAdapter;
import com.example.watchmovie.model.AllCate;
import com.example.watchmovie.model.AnhBia;
import com.example.watchmovie.model.CateItem;

import java.security.AllPermission;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

public class Favorite extends AppCompatActivity {

    InteractionDAO interactionDAO;
    RecyclerView recyclerView;
    FavoriesRecyclerAdapter favoriesRecyclerAdapter;
    ImageView imageView,iconSortRating,iconSortLuotThich,iconSortTime;
    SwipeRefreshLayout swipeRefreshLayout;
    CateItemDAO cateItemDAO;
    CateDAO cateDAO;
    List<CateItem> cateItemList=new ArrayList<>();
    Context context=this;
    TextView textView,tvSortRating,tvSortLuotThich,tvSortTime;
    int idUser,type,sortRating=-1,sortLuotThich=-2,sortTime=-3;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(BienToanCuc.getInstance().getLoggedInUserID()==-1)
        {
            Intent i=new Intent(this,LoginAcivity.class);
            startActivity(i);
        }else {
            setContentView(R.layout.activity_favorite);
            AnhXaVaKhaiBao();
            setSort(0);
            getcateItemList();
            setFavoritesRecyclerAdapter(cateItemList);
            onClickImgBackBtn();
            onSwipe();

            setOnClickSort();
        }
    }

    void setOnClickSort()
    {
        tvSortRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortRating=-sortRating;
                setSort(sortRating);
            }
        });
        tvSortLuotThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortLuotThich=-sortLuotThich;
                setSort(sortLuotThich);
            }
        });
        tvSortTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortTime=-sortTime;
                setSort(sortTime);
            }
        });
    }

    void setSort(int sort)
    {
        iconSortRating.setVisibility(View.INVISIBLE);
        iconSortLuotThich.setVisibility(View.INVISIBLE);
        iconSortTime.setVisibility(View.INVISIBLE);
        switch (sort) {
            case 3:
                Collections.sort(cateItemList, new CateItem.idNhoDenLon());
                iconSortTime.setImageResource(R.drawable.arrow_down_icon);
                iconSortTime.setVisibility(View.VISIBLE);
                break;
            case 2:
                Collections.sort(cateItemList, new CateItem.luotThichLonDenNho());
                iconSortLuotThich.setImageResource(R.drawable.arrow_down_icon);
                iconSortLuotThich.setVisibility(View.VISIBLE);
                break;
            case 1:
                Collections.sort(cateItemList, new CateItem.ratingLonDenNho());
                iconSortRating.setImageResource(R.drawable.arrow_down_icon);
                iconSortRating.setVisibility(View.VISIBLE);
                break;
            case -1:
                Collections.sort(cateItemList, new CateItem.ratingNhoDenLon());
                iconSortRating.setImageResource(R.drawable.arrow_up_icon);
                iconSortRating.setVisibility(View.VISIBLE);
                break;
            case -2:
                Collections.sort(cateItemList, new CateItem.luotThichNhoDenLon());
                iconSortLuotThich.setImageResource(R.drawable.arrow_up_icon);
                iconSortLuotThich.setVisibility(View.VISIBLE);
                break;
            case -3:
                Collections.sort(cateItemList, new CateItem.idLonDenNho());
                iconSortTime.setImageResource(R.drawable.arrow_up_icon);
                iconSortTime.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        setFavoritesRecyclerAdapter(cateItemList);
    }

    void onSwipe()
    {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = new Intent(Favorite.this, Favorite.class);
                intent.putExtra("type",type);
                intent.putExtra("key",key);
                startActivity(intent);
                finish();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    void getcateItemList()
    {
        if(type==0)
        {
            cateItemList=interactionDAO.getListMovieYeuThich(idUser);
        }
        else if(type==1)
        {
            textView.setText("Tìm kiếm");
            cateItemList=cateItemDAO.getListItemNameLike(key);
        }
        else if(type==2)
        {
            int id= Integer.parseInt(key);
            AllCate cate=cateDAO.getCateWithId(id);
            textView.setText(cate.getCateTitle());
            cateItemList=cateItemDAO.getListCateItemWithCateId(id);
        }

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

    void AnhXaVaKhaiBao()
    {

        swipeRefreshLayout=findViewById(R.id.Favorite_Swipe);
        textView=findViewById(R.id.tvAct);
        tvSortRating=findViewById(R.id.tv_sortRating);
        tvSortLuotThich=findViewById(R.id.tv_sortLuotThich);
        tvSortTime=findViewById(R.id.tv_sortTime);
        iconSortRating=findViewById(R.id.icon_sortRating);
        iconSortLuotThich=findViewById(R.id.icon_sortLuotThich);
        iconSortTime=findViewById(R.id.icon_sortTime);

        cateItemDAO=new CateItemDAO(context);
        interactionDAO=new InteractionDAO(context);
        cateDAO=new CateDAO(context);
        cateItemList.clear();

        type= getIntent().getIntExtra("type",0);
        key= getIntent().getStringExtra("key");

        idUser=BienToanCuc.getInstance().getLoggedInUserID();
    }
}