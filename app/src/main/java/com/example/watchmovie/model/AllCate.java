package com.example.watchmovie.model;

import java.util.List;

public class AllCate {

    Integer cateId;
    String cateTitle;

    private List<CateItem> cateItemList=null;


    public List<CateItem> getCateItemList() {
        return cateItemList;
    }


    public AllCate( Integer cateId,String cateTitle) {
        this.cateTitle = cateTitle;
        this.cateId = cateId;

    }

    public String getCateTitle() {
        return cateTitle;
    }

    public void setCateTitle(String cateTitle) {
        this.cateTitle = cateTitle;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }


}
