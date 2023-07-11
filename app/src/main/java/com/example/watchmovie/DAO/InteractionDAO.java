package com.example.watchmovie.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.watchmovie.model.CateItem;

import java.util.ArrayList;
import java.util.List;


public class InteractionDAO {
    CateItemDAO cateItemDAO;
    private SQLHelper sqlHelper;
    private SQLiteDatabase database;
    Context context;
    public InteractionDAO(Context context)
    {
        this.context=context;
        sqlHelper=new SQLHelper(context);
        database=sqlHelper.getWritableDatabase();
    }

    public int iSCreate(int userId,int movieId)
    {
        database=sqlHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT id FROM Interaction WHERE userId = " + userId + " AND movieId = " + movieId, null);
        if(cursor.getCount()>0)
        {
           cursor.moveToFirst();
           return cursor.getInt(0);
        }
        return -1;
    }

    public int getYeuThich(int userId, int movieId) {
        database = sqlHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT yeuThich FROM Interaction WHERE userId = " + userId + " AND movieId = " + movieId, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return 0;
    }

    public int getRating(int userId, int movieId) {
        database = sqlHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT rating FROM Interaction WHERE userId = " + userId + " AND movieId = " + movieId, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return -1;
    }

    public int getMaxId()
    {
        database=sqlHelper.getReadableDatabase();
        Cursor cursor=database.rawQuery("Select Max(id) from Interaction",null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return -1;
    }

    public void setYeuThich(int userId, int movieId, int yeuThich) {
        database = sqlHelper.getWritableDatabase();
        int idCreate = iSCreate(userId, movieId);
        if (idCreate == -1) {
            int id = getMaxId() + 1;
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("UserId", userId);
            values.put("MovieId", movieId);
            values.put("yeuThich", yeuThich);
            values.put("rating", -1);
            database.insert("Interaction", null, values);
        } else {
            ContentValues values = new ContentValues();
            values.put("yeuThich", yeuThich);
            database.update("Interaction", values, "id=?", new String[]{String.valueOf(idCreate)});
        }
    }


    public void setRating(int userId, int movieId, int rating) {
        database = sqlHelper.getWritableDatabase();
        int idCreate = iSCreate(userId, movieId);
        if (idCreate == -1) {
            int id = getMaxId() + 1;
            ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("UserId", userId);
            values.put("MovieId", movieId);
            values.put("yeuThich", 0);
            values.put("rating", rating);
            database.insert("Interaction", null, values);
        } else {
            ContentValues values = new ContentValues();
            values.put("rating", rating);
            database.update("Interaction", values, "id=?", new String[]{String.valueOf(idCreate)});
        }
    }

    public List<CateItem> getListMovieYeuThich(int userId) {
        database = sqlHelper.getReadableDatabase();
        cateItemDAO=new CateItemDAO(context);
        ArrayList<CateItem> list=new ArrayList<>();
        list.clear();
        Cursor cursor = database.rawQuery("SELECT movieId FROM Interaction WHERE userId = " + userId +" AND yeuThich=1" , null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            do{
                int movieId=cursor.getInt(0);
                CateItem cateItem=cateItemDAO.getCateItemWithID(movieId);
                if(cateItem!=null) {
                    list.add(cateItem);
                }
            }while (cursor.moveToNext());
        }
        return list;
    }

    public float getEverageRating(int idMovie)
    {
        database = sqlHelper.getReadableDatabase();
        cateItemDAO=new CateItemDAO(context);
        Cursor cursor = database.rawQuery("SELECT rating FROM Interaction WHERE MovieId = " + idMovie +" AND rating!=-1" , null);
        int quantity=cursor.getCount();
        int totalRating=0;
        if (quantity>0) {
            cursor.moveToFirst();
            do{
                quantity+=1;
                totalRating=totalRating+cursor.getInt(0);
            }while (cursor.moveToNext());
            return (float)totalRating/quantity;
        }
        return 0;
    }
}
