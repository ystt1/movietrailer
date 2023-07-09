package com.example.watchmovie.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchmovie.DAO.CateItemDAO;
import com.example.watchmovie.R;
import com.example.watchmovie.model.AllCate;
import com.example.watchmovie.model.CateItem;

import java.util.ArrayList;
import java.util.List;

public class MainRecycleAdapter extends RecyclerView.Adapter<MainRecycleAdapter.MainViewHolder> {

    List<CateItem> cateItemList=new ArrayList<>();

    CateItemDAO cateItemDAO;
    Context context;
    List<AllCate> cateList;

    public MainRecycleAdapter(Context context,List<AllCate> cateList)
    {
        this.context=context;
        this.cateList=cateList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycler,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
    holder.cateName.setText(cateList.get(position).getCateTitle());
    cateItemList.clear();
        cateItemDAO=new CateItemDAO(context);
    cateItemList=cateItemDAO.getListCateItem();
    setCateItem(holder.cateItem,cateItemList);
    }

    @Override
    public int getItemCount() {
        return cateList.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder{

        TextView cateName;

        RecyclerView cateItem;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            cateName=itemView.findViewById(R.id.item_cate);
            cateItem=itemView.findViewById(R.id.item_recycler);
        }


    }

    private void setCateItem(RecyclerView recyclerView, List<CateItem> cateItemList)
    {
        CateItemAdapter cateItemAdapter=new CateItemAdapter(context,cateItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(cateItemAdapter);
    }

}
