package com.example.watchmovie.model;

import android.content.Intent;

public class CateItem {
    Integer id;
    String movieName;
    String imgUrl;
    String fileurl;
    Integer idCate;
    float rating;
    Integer luotThich;

    public CateItem(Integer id, String movieName, String imgUrl, String fileurl, Integer idCate, float rating, Integer luotThich) {
        this.id = id;
        this.movieName = movieName;
        this.imgUrl = imgUrl;
        this.fileurl = fileurl;
        this.idCate = idCate;
        this.rating = rating;
        this.luotThich = luotThich;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Integer getLuotThich() {
        return luotThich;
    }

    public void setLuotThich(Integer luotThich) {
        this.luotThich = luotThich;
    }

    public Integer getIdCate() {
        return idCate;
    }

    public void setIdCate(Integer idCate) {
        this.idCate = idCate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }
}
