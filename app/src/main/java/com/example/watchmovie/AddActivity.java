package com.example.watchmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;

import com.example.watchmovie.BienToanCuc.BienToanCuc;
import com.example.watchmovie.DAO.CateDAO;
import com.example.watchmovie.adapter.CategoryRecycler;
import com.example.watchmovie.model.AllCate;
import com.example.watchmovie.model.CateItem;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    SearchView searchCate;
    CategoryRecycler categoryRecycler;
    Context context=this;

    CateDAO cateDAO;

    List<AllCate> cateList=new ArrayList<>();

    Button showDialogbtn,addCate;

    TextInputEditText textInputEditText;

    ImageView menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(BienToanCuc.getInstance().getLoggedInUserID()==-1)
        {
            Intent i=new Intent(this,LoginAcivity.class);
            startActivity(i);
        }else {
            setContentView(R.layout.activity_add);
            AnhXaVaKhaiBao();
            setAddAdapter(cateList);
            setOnClickAddCate();
            setSearch_cateChange();
        }

    }


    void setAddAdapter(List<AllCate> cateList)
    {

        recyclerView=findViewById(R.id.cate_recycler);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        categoryRecycler=new CategoryRecycler(this,cateList);
        recyclerView.setAdapter(categoryRecycler);
    }

    void setOnClickAddCate()
    {
        showDialogbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.add_cate_dialog);
                textInputEditText=dialog.findViewById(R.id.input_cateName);
                addCate=dialog.findViewById(R.id.confirm_add_cate_btn);

                addCate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name=textInputEditText.getText().toString();
                        if(name.equals("")){}
                        else{
                            cateDAO.addCateWithNameOnly(name);
                            cateList=cateDAO.getListCate();
                            setAddAdapter(cateList);
                            dialog.cancel();
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    void setSearch_cateChange()
    {
        searchCate.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                cateList=cateDAO.searchCate(s);
                setAddAdapter(cateList);
                return false;
            }
        });
    }

    void AnhXaVaKhaiBao()
    {
        searchCate=findViewById(R.id.search_cate);
        showDialogbtn=findViewById(R.id.add_category_button);
        cateList.clear();
        cateDAO=new CateDAO(context);
        cateList=cateDAO.getListCate();
    }
}