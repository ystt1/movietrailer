package com.example.watchmovie.model;

public class CateItem {
    Integer id;
    String movieName;
    String imgUrl;
    String fileurl;
    Integer idCate;



    public CateItem(Integer id, String movieName, String imgUrl, String fileurl, Integer idCate) {
        this.id = id;
        this.movieName = movieName;
        this.imgUrl = imgUrl;
        this.fileurl = fileurl;
        this.idCate = idCate;
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
