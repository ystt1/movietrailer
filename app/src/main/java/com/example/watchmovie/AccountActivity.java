package com.example.watchmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.watchmovie.BienToanCuc.BienToanCuc;
import com.example.watchmovie.DAO.UserDAO;
import com.example.watchmovie.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.net.ContentHandler;

public class AccountActivity extends AppCompatActivity {
    UserDAO userDAO;

    User user;

    Context context=this;
    int idUser;

    TextView tvUserName;
    TextInputEditText inputDisPlayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        idUser= BienToanCuc.getInstance().getLoggedInUserID();
        if(idUser==-1)
        {
            Intent i=new Intent(this,LoginAcivity.class);
            startActivity(i);
            finish();
        }
        else{
            AnhXaVaKhaiBao();
            setThuocTinh();
        }

    }

    void setThuocTinh()
    {
        tvUserName.setText(user.getUserName());
        inputDisPlayName.setText(user.getDisplayName());
    }

    void AnhXaVaKhaiBao()
    {
        tvUserName=findViewById(R.id.tv_UserName);
        inputDisPlayName=findViewById(R.id.input_displayName);
        userDAO=new UserDAO(context);
        user=userDAO.getUserWithId(idUser);
    }
}