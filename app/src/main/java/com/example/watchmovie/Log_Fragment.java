package com.example.watchmovie;

import android.content.Context;
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
import com.google.android.material.textfield.TextInputEditText;


public class Log_Fragment extends Fragment {
    Context context;
    TextInputEditText logUserName;
    TextInputEditText logPassWord;
    Button logButton;

    UserDAO userDAO;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_log_, container, false);
           userDAO=new UserDAO(requireContext());
        logUserName=view.findViewById(R.id.log_userName);
        logPassWord=view.findViewById(R.id.log_passWord);
        logButton=view.findViewById(R.id.log_button);
        logButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String userName= String.valueOf(logUserName.getText());
                String passWord= String.valueOf(logPassWord.getText());
                if(userName.equals("") || passWord.equals(""))
                {
                    Toast.makeText(requireContext(), "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    int id=userDAO.getIdUserWithNameandPass(userName,passWord);
                    if(id!=-1)
                    {
                        Intent i=new Intent(requireContext(), MainActivity.class);
                        BienToanCuc.getInstance().setLoggedInUserID(id);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(requireContext(), "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        return view;
    }
}