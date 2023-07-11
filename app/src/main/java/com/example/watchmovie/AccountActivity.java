package com.example.watchmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    Button btnChangeDisplayName;

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
            onClickChangeDisplayName();
        }
    }

    void setThuocTinh()
    {
        tvUserName.setText(user.getUserName());
        inputDisPlayName.setText(user.getDisplayName());
    }

    void onClickChangeDisplayName()
    {
        btnChangeDisplayName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String displayName= String.valueOf(inputDisPlayName.getText());
                if(displayName.equals(user.getUserName())==true)
                {
                    Toast.makeText(context, "Bạn chưa thay đổi gì", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (displayName.length() < 5) {
                        Toast.makeText(context, "Vui lòng nhập tên dài hơn 4 kí tự", Toast.LENGTH_SHORT).show();
                    } else {

                        if (userDAO.isDislayNameHadBeenUsed(displayName)) {
                            Toast.makeText(context, "Tên hiển thị đã được dùng vui lòng đổi tên khác", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            User newUser=new User(idUser,user.getUserName(),user.getPassWord(),user.getAvatar(),displayName,user.isAdmin());
                            userDAO.updateUser(newUser);
                            Intent i=new Intent(AccountActivity.this,AccountActivity.class);
                            finish();
                            startActivity(i);
                        }
                    }
                }
            }
        });
    }

    void AnhXaVaKhaiBao()
    {
        btnChangeDisplayName=findViewById(R.id.btn_changeDisplayName);
        tvUserName=findViewById(R.id.tv_UserName);
        inputDisPlayName=findViewById(R.id.input_displayName);
        userDAO=new UserDAO(context);
        user=userDAO.getUserWithId(idUser);
    }
}