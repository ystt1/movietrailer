package com.example.watchmovie.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CommentDAO {
    private SQLHelper sqlHelper;
    private SQLiteDatabase database;

    public CommentDAO(Context context)
    {
        sqlHelper=new SQLHelper(context);
        database=sqlHelper.getWritableDatabase();
    }
}
