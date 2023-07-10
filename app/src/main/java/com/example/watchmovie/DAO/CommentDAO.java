package com.example.watchmovie.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CommentDAO {
    private SQLHelper sqlHelper;
    private SQLiteDatabase database;

    public CommentDAO(Context context)
    {
        sqlHelper=new SQLHelper(context);
        database=sqlHelper.getWritableDatabase();
    }

    public int getMaxId()
    {
        database=sqlHelper.getReadableDatabase();
        Cursor cursor=database.rawQuery("Select Max(id) from Comment",null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return -1;
    }
    public void addComment(int userId,int movieId,String comment)
    {
        database = sqlHelper.getWritableDatabase();
        int id=getMaxId()+1;
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("UserId",id);
        values.put("MovieId",id);
        values.put("comment",id);
        database.insert("Comment", null, values);
    }
}
