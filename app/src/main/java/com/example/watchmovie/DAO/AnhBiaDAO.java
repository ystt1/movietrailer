package com.example.watchmovie.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.watchmovie.model.AnhBia;

import java.util.ArrayList;

public class AnhBiaDAO {
    private SQLHelper sqlHelper;
    private SQLiteDatabase database;
    public AnhBiaDAO(Context context)
    {
        sqlHelper=new SQLHelper(context);//goi lenh tao db
        database=sqlHelper.getWritableDatabase();
    }
    //insert anhBia
    public long addAnhBia(AnhBia anhBia)
    {
        ContentValues values=new ContentValues();
        values.put("id",anhBia.getId());
        values.put("movieName",anhBia.getMovieName());
        values.put("imageUrl",anhBia.getImageUrl());
        values.put("fileUrl",anhBia.getFileUrl());
        long check=database.insert("AnhBia",null,values);
        return check;
    }

    public long deleteAnhBia(int id)
    {
        long check=database.delete("AnhBia","id= ?",
                new String[]{String.valueOf(id)});
        return check;

    }

    public long updateAnhBia(AnhBia anhBia)
    {
        ContentValues values=new ContentValues();
        values.put("id",anhBia.getId());
        values.put("movieName",anhBia.getMovieName());
        values.put("imageUrl",anhBia.getImageUrl());
        values.put("fileUrl",anhBia.getFileUrl());
        long check=database.update("AnhBia",values,"id=?",
                new String[]{String.valueOf(anhBia.getId())});
        return check;
    }

    public ArrayList<AnhBia> getListAnhBia()
    {
        ArrayList<AnhBia> list=new ArrayList<>();
        database=sqlHelper.getReadableDatabase();

            Cursor cursor=database.rawQuery("Select * from AnhBia",null);
            if(cursor.getCount()>0)
            {

                cursor.moveToFirst();
                do{
                    list.add(new AnhBia(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                    ));
                }while (cursor.moveToNext());
            }

        return list;
    }
}
