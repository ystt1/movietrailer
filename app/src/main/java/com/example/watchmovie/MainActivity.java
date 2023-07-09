package com.example.watchmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toolbar;

import com.example.watchmovie.DAO.AnhBiaDAO;
import com.example.watchmovie.DAO.CateDAO;
import com.example.watchmovie.DAO.CateItemDAO;
import com.example.watchmovie.adapter.BannerMovieViewPager2Adapter;
import com.example.watchmovie.adapter.MainRecycleAdapter;
import com.example.watchmovie.model.AllCate;
import com.example.watchmovie.model.AnhBia;
import com.example.watchmovie.model.CateItem;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    BannerMovieViewPager2Adapter bannerMovieViewPager2Adapter;
    TabLayout tabLayout,cateTab;
    ViewPager bannerMovieViewPager;
    List<AnhBia> homeAnhBiaList=new ArrayList<>();
    List<AnhBia> showAnhBiaList;
    List<AnhBia> tvAnhBiaList;
    List<AnhBia> childAnhBiaList;

    AnhBiaDAO anhBiaDAO;

    MainRecycleAdapter mainRecycleAdapter;
    RecyclerView mainRecyclerView;

    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    List<AllCate> cateList;
    Context context=this;

    ImageView imgMenu;

    SearchView searchView;

    //cateItem
    List<CateItem> homeCateItemList=new ArrayList<>();

    CateItemDAO cateItemDAO;

    //cateList

    List<AllCate> cateList1=new ArrayList<>();

    CateDAO cateDAO;

    TabItem tabItem1,tabItem2,tabItem3;

    List<AllCate> cateListForBanner=new ArrayList<>();

    List<CateItem> bannerListItem=new ArrayList<>();
    List<AllCate> cateListBelow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

;


        tabLayout=findViewById(R.id.tab_autoChange);
        cateTab=findViewById(R.id.cateTab);
        appBarLayout=findViewById(R.id.appbar);
        nestedScrollView=findViewById(R.id.nest_scroll);
        imgMenu=findViewById(R.id.menu_icon);
        searchView=findViewById(R.id.search_bar);

        anhBiaDAO=new AnhBiaDAO(context);
    bannerListItem.clear();
        homeAnhBiaList.clear();
        homeAnhBiaList=anhBiaDAO.getListAnhBia();
        cateItemDAO=new CateItemDAO(context);
        cateDAO=new CateDAO(context);
        homeCateItemList.clear();
        homeCateItemList=cateItemDAO.getListCateItem();
        cateListForBanner.clear();
        cateListForBanner=cateDAO.getListCateForBanner();
        cateListBelow=cateDAO.getListCateBelowBanner();
        setBanner();
        onChangeBannerTab();
        setMainRecyclerView(cateListBelow);










//

//        cateList=new ArrayList<>();

//        cateList.add(new AllCate(2,"xyz",homeCateList));
//        cateList.add(new AllCate(3,"ijk",homeCateList));
//        cateList.add(new AllCate(5,"tt",homeCateList));
//        cateList.add(new AllCate(6,"yasuo",homeCateList));

//        setMainRecyclerView(cateList);


        cateList1.clear();
        cateDAO=new CateDAO(context);
        cateList1=cateDAO.getListCate();
        //setMainRecyclerView(cateList1);


        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu();
            }
        });

        onSubmitSearchbar();

    }

    void setBanner()
    {
       for(int i=0; i < cateListForBanner.size(); i++) {
           cateTab.getTabAt(i).setText(cateListForBanner.get(i).getCateTitle());
       }
       changeBannerList(cateListForBanner.get(0).getCateId());
    }

    void changeBannerList(int id)
    {
        bannerListItem=cateItemDAO.getListCateItemWithCateId(id);
        setBannerMovieViewPager2Adapter(bannerListItem);
    }

    void onChangeBannerTab()
    {
        cateTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1:
                        setScrollDefaultView();
                        changeBannerList(cateListForBanner.get(1).getCateId());
                        setBannerMovieViewPager2Adapter(bannerListItem);
                        return;
                    case 2:
                        setScrollDefaultView();
                        changeBannerList(cateListForBanner.get(2).getCateId());
                        setBannerMovieViewPager2Adapter(bannerListItem);
                        return;
                    default:
                        setScrollDefaultView();
                        changeBannerList(cateListForBanner.get(0).getCateId());
                        setBannerMovieViewPager2Adapter(bannerListItem);

                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    void onSubmitSearchbar()
    {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s!="")
                {
                    changeToFavoriteAct(1,s);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }




    void showMenu()
    {
        PopupMenu popupMenu=new PopupMenu(this,imgMenu);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_them:
                        changeToAddAct();
                        break;
                    case R.id.menu_thich:
                        changeToFavoriteAct(0,"");
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    void changeToAddAct()
    {
        Intent i=new Intent(context,AddActivity.class);
        context.startActivity(i);
    }

    void changeToFavoriteAct(int loai,String key)
    {

        Intent i = new Intent(context, Favorite.class);

        if(loai==1 && key!="")
        {
            i.putExtra("type","1");
            i.putExtra("key",key);
        }
        else {
            i.putExtra("type","0");
            i.putExtra("key",key);
        }
        context.startActivity(i);
    }


    void setBannerMovieViewPager2Adapter(List<CateItem> cateItemList)
    {
        bannerMovieViewPager=findViewById(R.id.banner_viewPager2);
        bannerMovieViewPager2Adapter=new BannerMovieViewPager2Adapter(this,cateItemList);
        bannerMovieViewPager.setAdapter(bannerMovieViewPager2Adapter);
        tabLayout.setupWithViewPager(bannerMovieViewPager);
        Timer timeSlide=new Timer();
        timeSlide.scheduleAtFixedRate(new AutoSlider(),20000,6000);
        tabLayout.setupWithViewPager(bannerMovieViewPager,true);
    }

    class AutoSlider extends TimerTask {
        public void run()
        {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (bannerMovieViewPager.getCurrentItem() < bannerListItem.size() - 1)
                    {
                        bannerMovieViewPager.setCurrentItem(bannerMovieViewPager.getCurrentItem()+1);
                    } else {
                        bannerMovieViewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    public void setMainRecyclerView(List<AllCate> cateListBelow)
    {
        mainRecyclerView =findViewById(R.id.recycle_main);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecycleAdapter=new MainRecycleAdapter(this,cateListBelow);
        mainRecyclerView.setAdapter(mainRecycleAdapter);
    }

    private void setScrollDefaultView()
    {
        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0,0);
        appBarLayout.setExpanded(true);
    }


}