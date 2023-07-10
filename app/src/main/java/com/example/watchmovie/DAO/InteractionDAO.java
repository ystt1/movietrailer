package com.example.watchmovie.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class InteractionDAO {
    private SQLHelper sqlHelper;
    private SQLiteDatabase database;

    public InteractionDAO(Context context)
    {
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
        return 0;
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
            values.put("rating", 0);
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
}
