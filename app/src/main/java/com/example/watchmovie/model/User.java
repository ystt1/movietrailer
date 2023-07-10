package com.example.watchmovie.model;

import android.graphics.Bitmap;

public class User {
    int id;
    String userName;
    String passWord;
    String avatar;
    String displayName;
    boolean isAdmin;


    public User(int id, String userName, String passWord, String avatar, String displayName, boolean isAdmin) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.avatar = avatar;
        this.displayName = displayName;
        this.isAdmin = isAdmin;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }


}
