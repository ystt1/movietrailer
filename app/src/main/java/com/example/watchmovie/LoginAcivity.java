package com.example.watchmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;

import com.example.watchmovie.adapter.LoginPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;

public class LoginAcivity extends AppCompatActivity {

    Context context=this;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acivity);
        tabLayout=findViewById(R.id.tab_login);
        viewPager=findViewById(R.id.viewPager_login);
        tabLayout.setupWithViewPager(viewPager);
        LoginPageAdapter loginPageAdapter=new LoginPageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        loginPageAdapter.addFragment(new Log_Fragment(),"Đăng nhập");
        loginPageAdapter.addFragment(new Reg_Fragment(),"Đăng kí");
        viewPager.setAdapter(loginPageAdapter);
    }
}