package com.example.watchmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.watchmovie.DAO.CateDAO;
import com.example.watchmovie.adapter.CategoryRecycler;
import com.example.watchmovie.model.AllCate;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    CategoryRecycler categoryRecycler;
    Context context=this;

    CateDAO cateDAO;

    List<AllCate> cateList=new ArrayList<>();

    Button showDialogbtn,addCate;

    TextInputEditText textInputEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        showDialogbtn=findViewById(R.id.add_category_button);

        cateList.clear();
        cateDAO=new CateDAO(context);

        setAddAdapter();
        setOnClickAddCate();


    }


    void setAddAdapter()
    {
        cateList=cateDAO.getListCate();
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
                            setAddAdapter();
                        }
                    }
                });
                dialog.show();
            }
        });
    }
}