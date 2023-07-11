package com.example.watchmovie.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.example.watchmovie.BienToanCuc.BienToanCuc;
import com.example.watchmovie.DAO.CateItemDAO;
import com.example.watchmovie.DAO.UserDAO;
import com.example.watchmovie.MovieDetails;
import com.example.watchmovie.R;
import com.example.watchmovie.model.AnhBia;
import com.example.watchmovie.model.CateItem;
import com.example.watchmovie.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class FavoriesRecyclerAdapter extends RecyclerView.Adapter<FavorieViewHolder> {

    Context context,mContext;
    List<CateItem> cateItemList,newCateItem;
    CateItemDAO cateItemDAO;

    User user;

    UserDAO userDAO;

    public FavoriesRecyclerAdapter(Context context, List<CateItem> cateItemList) {
        this.context = context;
        this.cateItemList = cateItemList;
        cateItemDAO=new CateItemDAO(this.context);
        mContext=this.context.getApplicationContext();
        userDAO=new UserDAO(context);
        this.user=userDAO.getUserWithId(BienToanCuc.getInstance().getLoggedInUserID());
    }

    @NonNull
    @Override
    public FavorieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavorieViewHolder(LayoutInflater.from(context).inflate(R.layout.favorites_recycler,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavorieViewHolder holder, int position) {
        int flag=position;
        holder.textView.setText(cateItemList.get(flag).getMovieName());
        Glide.with(context).load(cateItemList.get(flag).getImgUrl()).into(holder.imageView);
        holder.iRating.setText(String.valueOf(cateItemList.get(flag).getRating()));
        holder.iYeuThich.setText(String.valueOf(cateItemList.get(flag).getLuotThich()));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, MovieDetails.class);
                i.putExtra("movieId",cateItemList.get(flag).getId());
                i.putExtra("movieName",cateItemList.get(flag).getMovieName());
                i.putExtra("movieImageUrl",cateItemList.get(flag).getImgUrl());
                i.putExtra("movieFileUrl",cateItemList.get(flag).getFileurl());
                context.startActivity(i);
            }
        });
        if(user.getIsAdmin()==0)
        {
            holder.menu.setVisibility(View.INVISIBLE);
        }
        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(holder.menu,cateItemList.get(flag),flag);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cateItemList.size();
    }



    void showMenu(ImageView menu,CateItem cateItem,int flag)
    {
        PopupMenu popupMenu=new PopupMenu(mContext,menu);
        popupMenu.getMenuInflater().inflate(R.menu.option_delete_path,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.delete:
                        deleteCateItem(cateItem, flag);
                        break;
                    case R.id.change:
                        changeMovie(cateItem,flag);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    void deleteCateItem(CateItem cateItem,int flag)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .create();
        alertDialog.setTitle("Thông báo");
        alertDialog.setMessage("Bạn có chắc muốn xóa phim "+cateItem.getMovieName());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cateItemDAO.deleteCateItem(cateItem.getId());
                        cateItemList.remove(flag);
                        notifyDataSetChanged();
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

    void changeMovie(CateItem cateItem,int flag)
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

                                    CateItem newItem=new CateItem(cateItem.getId(),String.valueOf(nameInput.getText()),String.valueOf(bannerInput.getText()),String.valueOf(trailerInput.getText()),cateItem.getIdCate(),cateItem.getRating(),cateItem.getLuotThich());
                                    cateItemDAO.updateCateItem(newItem);

                                    cateItemList.get(flag).setImgUrl(String.valueOf(bannerInput.getText()));
                                    cateItemList.get(flag).setFileurl(String.valueOf(trailerInput.getText()));
                                    cateItemList.get(flag).setMovieName(String.valueOf(nameInput.getText()));
                                    notifyDataSetChanged();
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
}
