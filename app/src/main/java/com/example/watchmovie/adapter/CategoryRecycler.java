package com.example.watchmovie.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchmovie.DAO.CateDAO;
import com.example.watchmovie.addMovie;
import com.example.watchmovie.R;
import com.example.watchmovie.model.AllCate;
import com.example.watchmovie.model.CateItem;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class CategoryRecycler extends RecyclerView.Adapter<CategoryViewHolder> {

    CateDAO cateDAO;
    Context context;
    List<AllCate> allCates=new ArrayList<>();
    List<AllCate> cateList;
    Context mContext;

    public CategoryRecycler(Context context, List<AllCate> cateList) {
        this.context = context;
        this.cateList = cateList;
        cateDAO=new CateDAO(this.context);
        mContext=context.getApplicationContext();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(context).inflate(R.layout.category_name,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        int flag=position;
        holder.cateName.setText(cateList.get(flag).getCateTitle());
        holder.cateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,addMovie.class);
                i.putExtra("categoryId",cateList.get(flag).getCateId());
                context.startActivity(i);
            }
        });

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(holder.menu,cateList.get(flag));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cateList.size();
    }


    void showMenu(ImageView menu,AllCate cate)
    {
        PopupMenu popupMenu=new PopupMenu(mContext,menu);
        popupMenu.getMenuInflater().inflate(R.menu.option_delete_path,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.delete:
                        deleteCate(cate);
                        break;
                    case R.id.change:
                        changeCate(cate);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    void deleteCate(AllCate cate)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .create();
        alertDialog.setTitle("Thông báo");
        alertDialog.setMessage("Bạn có chắc muốn xóa loại "+cate.getCateTitle()+" ra khỏi thể loại?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        allCates.clear();
                        cateDAO.deleteCate(cate.getCateId());
                        allCates=cateDAO.getListCate();
                        refreshData(allCates);
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

    public void refreshData(List<AllCate> newCateList) {
        cateList.clear();
        cateList.addAll(newCateList);
        notifyDataSetChanged();
    }


    public void changeCate(AllCate cate)
    {
        TextInputEditText textInputEditText;
        Button confirm;
        TextView textView;

        Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.add_cate_dialog);
        textInputEditText=dialog.findViewById(R.id.input_cateName);
        confirm=dialog.findViewById(R.id.confirm_add_cate_btn);
        textView=dialog.findViewById(R.id.addCate_type);
        textView.setText("Sửa tên thể loại");
        textInputEditText.setText(cate.getCateTitle());
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title= String.valueOf(textInputEditText.getText());
                if(title.equals("")==false)
                {
                    cate.setCateTitle(title);
                    allCates.clear();
                    cateDAO.updateCate(cate);
                    allCates=cateDAO.getListCate();
                    dialog.cancel();
                    refreshData(allCates);
                }
            }
        });
        dialog.show();
    }
}
