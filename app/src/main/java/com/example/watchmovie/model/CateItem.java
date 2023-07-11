package com.example.watchmovie.model;

import android.content.Intent;

import java.util.Comparator;

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


    public static class luotThichNhoDenLon implements Comparator<CateItem> {
        @Override
        public int compare(CateItem cateItem1, CateItem cateItem2) {
            return cateItem1.getLuotThich() - cateItem2.getLuotThich();
        }
    }

    public static class luotThichLonDenNho implements Comparator<CateItem> {
        @Override
        public int compare(CateItem cateItem1, CateItem cateItem2) {
            return cateItem2.getLuotThich() - cateItem1.getLuotThich();
        }
    }

    public static class idNhoDenLon implements Comparator<CateItem> {
        @Override
        public int compare(CateItem cateItem1, CateItem cateItem2) {
            return cateItem1.getId() - cateItem2.getId();
        }
    }
    public static class idLonDenNho implements Comparator<CateItem> {
        @Override
        public int compare(CateItem cateItem1, CateItem cateItem2) {
            return cateItem2.getId() - cateItem1.getId();
        }
    }

    public static class ratingNhoDenLon implements Comparator<CateItem> {
        @Override
        public int compare(CateItem cateItem1, CateItem cateItem2) {
            return Float.compare(cateItem1.getRating(), cateItem2.getRating());
        }
    }
    public static class ratingLonDenNho implements Comparator<CateItem> {
        @Override
        public int compare(CateItem cateItem1, CateItem cateItem2) {
            return Float.compare(cateItem2.getRating(), cateItem1.getRating());
        }
    }

    }

