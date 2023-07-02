package com.example.watchmovie.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.watchmovie.model.CateItem;

import java.util.ArrayList;

public class CateItemDAO {

    private SQLHelper sqlHelper;
    private SQLiteDatabase database;

    public CateItemDAO(Context context)
    {
        sqlHelper=new SQLHelper(context);//goi lenh tao db
        database=sqlHelper.getWritableDatabase();
    }


    public long addCateItem(CateItem cateItem)
    {
        ContentValues values=new ContentValues();
        values.put("id",cateItem.getId());
        values.put("movieName",cateItem.getMovieName());
        values.put("imgUrl",cateItem.getImgUrl());
        values.put("fileurl",cateItem.getFileurl());
        values.put("idCate",cateItem.getIdCate());
        values.put("yeuThich",cateItem.getYeuThich());
        long check=database.insert("CateItem",null,values);
        return check;
    }

    public long deleteCateItem(int id)
    {
        long check=database.delete("CateItem","id= ?",
                new String[]{String.valueOf(id)});
        return check;

    }

    public long updateCateItem(CateItem cateItem)
    {
        ContentValues values=new ContentValues();
        values.put("id",cateItem.getId());
        values.put("movieName",cateItem.getMovieName());
        values.put("imgUrl",cateItem.getImgUrl());
        values.put("fileurl",cateItem.getFileurl());
        values.put("idCate",cateItem.getIdCate());
        values.put("yeuThich",cateItem.getYeuThich());
        long check=database.update("CateItem",values,"id=?",
                new String[]{String.valueOf(cateItem.getId())});
        return check;
    }

    public ArrayList<CateItem> getListCateItem()
    {
        ArrayList<CateItem> list=new ArrayList<>();
        database=sqlHelper.getReadableDatabase();

        Cursor cursor=database.rawQuery("Select * from CateItem",null);
        if(cursor.getCount()>0)
        {

            cursor.moveToFirst();
            do{
                list.add(new CateItem(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getInt(5)
                ));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public ArrayList<CateItem> getListItemNameLike(String key)
    {
        ArrayList<CateItem> list=new ArrayList<>();
        database=sqlHelper.getReadableDatabase();

        Cursor cursor=database.rawQuery("SELECT * from CateItem WHERE moviename like \"%"+key+"%\"",null);
        if(cursor.getCount()>0)
        {

            cursor.moveToFirst();
            do{
                list.add(new CateItem(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getInt(5)
                ));
            }while (cursor.moveToNext());
        }
        return list;
    }


    public int getYeuThich(int id)
    {
        database=sqlHelper.getReadableDatabase();

        Cursor cursor=database.rawQuery("SELECT yeuThich from CateItem WHERE id ="+id+"",null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return -1;
    }

    public  CateItem getCateItemWithID(int id)
    {
        CateItem cateItem;
        database=sqlHelper.getReadableDatabase();
        Cursor cursor=database.rawQuery("SELECT * from CateItem WHERE id ="+id,null);

        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            cateItem=new CateItem(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getInt(5));
            return cateItem;
       }
        return null;
    }

    public long setyeuThich(int id,int yeuThich)
    {
        database=sqlHelper.getWritableDatabase();
        CateItem cateItem= getCateItemWithID(id);
        ContentValues values=new ContentValues();
        values.put("id",cateItem.getId());
        values.put("movieName",cateItem.getMovieName());
        values.put("imgUrl",cateItem.getImgUrl());
        values.put("fileurl",cateItem.getFileurl());
        values.put("idCate",cateItem.getIdCate());
        values.put("yeuThich",yeuThich);
        long check=0;
        check=database.update("CateItem",values,"id=?",
                new String[]{String.valueOf(cateItem.getId())});
        return check;
    }
}
