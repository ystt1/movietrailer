package com.example.watchmovie;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.watchmovie.BienToanCuc.BienToanCuc;
import com.example.watchmovie.DAO.UserDAO;
import com.example.watchmovie.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.text.Normalizer;

public class Reg_Fragment extends Fragment {

    TextInputEditText userName,passWord,rePassWord,displayName;
    UserDAO userDAO;
    Button reg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_reg_, container, false);
        userDAO=new UserDAO(requireContext());
        userName=view.findViewById(R.id.reg_userName);
        passWord=view.findViewById(R.id.reg_passWord);
        rePassWord=view.findViewById(R.id.reg_rePassWord);
        displayName=view.findViewById(R.id.reg_displayName);
        reg=view.findViewById(R.id.reg_button);
        onClickButton();
        // Inflate the layout for this fragment
        return view;
    }


    void onClickButton()
    {
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sUserName= String.valueOf(userName.getText());
                String sPassWord= String.valueOf(passWord.getText());
                String sRePassWord= String.valueOf(rePassWord.getText());
                String sDislayName= String.valueOf(displayName.getText());

                if(sDislayName.equals("") || sUserName.equals("") || sPassWord.equals("") || sRePassWord.equals(""))
                {
                    Toast.makeText( requireContext(),"Vui Lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(sDislayName.length()<5 || sUserName.length()<5 || sPassWord.length()<5 || sRePassWord.length()<5)
                    {
                        Toast.makeText( requireContext(),"Mỗi trường có ít nhất 4 kí tự", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(checkIn(sDislayName) || checkIn(sUserName) || checkIn(sPassWord) || checkIn(sRePassWord))
                        {
                            Toast.makeText( requireContext(),"Vui lòng không nhập kí tự đặc biệt", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(sPassWord.equals(sRePassWord))
                            {
                                int id=userDAO.getMaxId();
                                User user=new User(id+1,sUserName,sPassWord,null,sDislayName,false);
                                if(userDAO.createUser(user)==-1)
                                {
                                    Toast.makeText( requireContext(),"Tên tài khoản hoặc hiển thị đã có người dùng", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Intent i=new Intent(requireContext(), MainActivity.class);
                                    BienToanCuc.getInstance().setLoggedInUserID(id+1);
                                    startActivity(i);
                                }
                            }
                            else{
                                Toast.makeText( requireContext(),"Mật khẩu nhập lại sai", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }

    boolean checkIn(String input)
    {
        if(isSpecialChar(input) || isCoDau(input))
            return true;
        return false;

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