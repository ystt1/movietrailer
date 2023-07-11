package com.example.watchmovie.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.watchmovie.model.CateItem;
import com.example.watchmovie.model.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    UserDAO userDAO;
    Context context;
    private SQLHelper sqlHelper;
    private SQLiteDatabase database;

    public CommentDAO(Context context)
    {
        this.context=context;
        sqlHelper=new SQLHelper(context);
        database=sqlHelper.getWritableDatabase();
        userDAO=new UserDAO(context);
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
    public void addComment(int userId,int movieId,String comment,String dateTime)
    {
        database = sqlHelper.getWritableDatabase();
        int id=getMaxId()+1;
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("UserId",userId);
        values.put("MovieId",movieId);
        values.put("comment",comment);
        values.put("dateTime",dateTime);
        database.insert("Comment", null, values);
    }

    public List<Comment> getListComment(int userID,int movieId,int limit)
    {
        database = sqlHelper.getReadableDatabase();
        List<Comment> commentList=new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM Comment WHERE MovieId = " + movieId +" ORDER BY id DESC LIMIT "+limit*5 , null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            do{
                int idUser=cursor.getInt(1);
                String text=cursor.getString(3);
                String dateTime=cursor.getString(4);
                String name= userDAO.getNameWithId(idUser);
                commentList.add(new Comment(name,text,dateTime));

            }while (cursor.moveToNext());
        }
        return commentList;
    }
}
