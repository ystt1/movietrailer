package com.example.watchmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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
import java.text.Normalizer;

public class AccountActivity extends AppCompatActivity {
    UserDAO userDAO;
    User user;
    Context context=this;
    int idUser;
    TextView tvUserName;
    TextInputEditText inputDisPlayName;
    Button btnChangeDisplayName,btnChangePassWord;

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
            onClickShowDialogChangePassWord();
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
                            User newUser=new User(idUser,user.getUserName(),user.getPassWord(),user.getAvatar(),displayName,user.getIsAdmin());
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

    void onClickShowDialogChangePassWord()
    {
        btnChangePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.change_password_dialog);
                TextInputEditText inputOldPass=dialog.findViewById(R.id.input_oldPassWord);
                TextInputEditText inputNewPass=dialog.findViewById(R.id.input_newPassWord);
                TextInputEditText inputRePass=dialog.findViewById(R.id.input_rePassWord);
                Button btnConfirm=dialog.findViewById(R.id.btn_confirmChangePassWord);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String sOldPass= String.valueOf(inputOldPass.getText());
                        String sNewPass= String.valueOf(inputNewPass.getText());
                        String sReWord= String.valueOf(inputRePass.getText());
                        String currPass=user.getPassWord();
                        if(sNewPass.length()<5)
                        {
                            Toast.makeText(AccountActivity.this, "Mật khẩu mới ít nhất có 5 kí tự", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if(isValid(sNewPass)) {

                                if (sNewPass.equals(sReWord)) {
                                    if (sOldPass.equals(currPass)) {
                                        user.setPassWord(sNewPass);
                                        userDAO.updateUser(user);
                                        Toast.makeText(AccountActivity.this, "cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    } else {
                                        Toast.makeText(AccountActivity.this, "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(AccountActivity.this, "Nhập lại mật khẩu không giống", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(AccountActivity.this, "Mật khẩu mới không hợp lệ", Toast.LENGTH_SHORT).show();
                            }
                        }}
                });

                dialog.show();
            }
        });
    }

    void AnhXaVaKhaiBao()
    {
        btnChangeDisplayName=findViewById(R.id.btn_changeDisplayName);
        btnChangePassWord=findViewById(R.id.btn_changePassWord);
        tvUserName=findViewById(R.id.tv_UserName);
        inputDisPlayName=findViewById(R.id.input_displayName);
        userDAO=new UserDAO(context);
        user=userDAO.getUserWithId(idUser);
    }






    boolean isValid(String input)
    {
        if(isSpecialChar(input) || isCoDau(input))
            return false;
        return true;

    }


    boolean isSpecialChar(String input)
    {

        String specialCharacters = "[!@#$%&*()_+=|<>?{}\\[\\]~-]";
        if (input.matches(".*" + specialCharacters + ".*")) {
            return true;
        }
        return false;
    }

    boolean isCoDau(String input)
    {

        String normalizedInput = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");


        if (!input.equals(normalizedInput)) {
            return true;
        } else {
            return false;
        }

    }
}