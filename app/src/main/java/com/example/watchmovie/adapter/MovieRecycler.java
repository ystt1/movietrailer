package com.example.watchmovie.adapter;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.watchmovie.AddActivity;
import com.example.watchmovie.DAO.CateItemDAO;
import com.example.watchmovie.R;
import com.example.watchmovie.model.AllCate;
import com.example.watchmovie.model.CateItem;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MovieRecycler extends  RecyclerView.Adapter<MovieViewHolder>{
    Context context,mContext;
    List<CateItem> cateItemList,newCateItem;

    CateItemDAO cateItemDAO;
    int idCate;
    public MovieRecycler(Context context, List<CateItem> cateItemList,int idCate) {
        this.context = context;
        this.cateItemList = cateItemList;
        mContext=this.context.getApplicationContext();
        cateItemDAO=new CateItemDAO(this.context);
        this.idCate=idCate;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_name,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        int flag=position;
        holder.movieName.setText(cateItemList.get(flag).getMovieName());
        Glide.with(context).load(cateItemList.get(flag).getImgUrl()).into(holder.img);

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(holder.menu,cateItemList.get(flag));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cateItemList.size();
    }

    void showMenu(ImageView menu,CateItem cateItem)
    {
        PopupMenu popupMenu=new PopupMenu(mContext,menu);
        popupMenu.getMenuInflater().inflate(R.menu.option_delete_path,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.delete:
                        deleteCateItem(cateItem);
                        break;
                    case R.id.change:
                        changeMovie(cateItem);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    void deleteCateItem(CateItem cateItem)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .create();
        alertDialog.setTitle("Thông báo");
        alertDialog.setMessage("Bạn có chắc muốn xóa phim "+cateItem.getMovieName());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        newCateItem=new ArrayList<>();
                        newCateItem.clear();
                        cateItemDAO.deleteCateItem(cateItem.getId());
                        newCateItem=cateItemDAO.getListCateItemWithCateId(idCate);
                        refreshData(newCateItem);
                        dialogInterface.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Huỷ",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "Hủy", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                }
        );

        alertDialog.show();
    }

    public void refreshData(List<CateItem> newCateItem) {
        cateItemList.clear();
        cateItemList.addAll(newCateItem);
        notifyDataSetChanged();
    }
    void changeMovie(CateItem cateItem)
    {
        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.add_movie_dialog);
        TextInputEditText nameInput=dialog.findViewById(R.id.input_movie_name);
        TextInputEditText bannerInput=dialog.findViewById(R.id.input_banner);
        TextInputEditText trailerInput=dialog.findViewById(R.id.input_trailer);
        Button confirmAdd=dialog.findViewById(R.id.confirm_add_movie_btn);
        ImageView addBanner=dialog.findViewById(R.id.banner_add);
        TextView textView=dialog.findViewById(R.id.addMovie_Type);
        textView.setText("Sửa phim");

        confirmAdd.setText("Đồng ý");
        nameInput.setText(cateItem.getMovieName());
        bannerInput.setText(cateItem.getImgUrl());
        trailerInput.setText(cateItem.getFileurl());
        Glide.with(context).load(String.valueOf(bannerInput.getText())).into(addBanner);


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
                                if (URLUtil.isValidUrl(String.valueOf(trailerInput.getText())) && id!=-1) {
                                    newCateItem=new ArrayList<>();
                                    newCateItem.clear();
                                    CateItem newItem=new CateItem(cateItem.getId(),String.valueOf(nameInput.getText()),String.valueOf(bannerInput.getText()),String.valueOf(trailerInput.getText()),idCate);
                                    cateItemDAO.updateCateItem(newItem);
                                    newCateItem=cateItemDAO.getListCateItemWithCateId(idCate);
                                    dialog.cancel();
                                    refreshData(newCateItem);

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
}
