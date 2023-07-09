package com.example.watchmovie.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.watchmovie.model.AllCate;

import java.util.ArrayList;

public class CateDAO {

    private SQLHelper sqlHelper;
    private SQLiteDatabase database;

    public CateDAO(Context context)
    {
        sqlHelper=new SQLHelper(context);//goi lenh tao db
        database=sqlHelper.getWritableDatabase();
    }


    public long addCate(AllCate category)
    {
        ContentValues values=new ContentValues();
        values.put("cateID",category.getCateId());
        values.put("cateTitle",category.getCateTitle());
        long check=database.insert("Category",null,values);
        return check;
    }

    public long deleteCate(int id)
    {
        long check=database.delete("Category","id= ?",
                new String[]{String.valueOf(id)});
        return check;

    }

    public long updateCate(AllCate cate)
    {
        ContentValues values=new ContentValues();
        values.put("cateId",cate.getCateId());
        values.put("cateTitle",cate.getCateTitle());
        long check=database.update("Category",values,"id=?",
                new String[]{String.valueOf(cate.getCateId())});
        return check;
    }

    public ArrayList<AllCate> getListCate()
    {
        ArrayList<AllCate> list=new ArrayList<>();
        database=sqlHelper.getReadableDatabase();

        Cursor cursor=database.rawQuery("Select * from Category",null);
        if(cursor.getCount()>0)
        {

            cursor.moveToFirst();
            do{
                list.add(new AllCate(cursor.getInt(0),
                        cursor.getString(1)
                ));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public void addCateWithNameOnly(String name)
    {
        int id= getListCate().size();
        ContentValues values=new ContentValues();
        values.put("cateID",id);
        values.put("cateTitle",name);
        long check=database.insert("Category",null,values);
    }
}
