package com.example.watchmovie.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.watchmovie.model.CateItem;
import com.example.watchmovie.model.User;

import java.util.ArrayList;

public class UserDAO {

    private SQLHelper sqlHelper;
    private SQLiteDatabase database;

    public UserDAO(Context context)
    {
        sqlHelper=new SQLHelper(context);//goi lenh tao db
        database=sqlHelper.getWritableDatabase();
    }


    public long addUser(User user)
    {
        ContentValues values=new ContentValues();
        values.put("id",user.getId());
        values.put("userName",user.getUserName());
        values.put("passWord",user.getPassWord());
        values.put("avatar",user.getAvatar());
        values.put("displayName",user.getDisplayName());
        values.put("isAdmin",user.isAdmin());
        long check=database.insert("User",null,values);
        return check;
    }

    public long deleteUser(int id)
    {
        long check=database.delete("User","id= ?",
                new String[]{String.valueOf(id)});
        return check;

    }

    public long updateUser(User user)
    {
        ContentValues values=new ContentValues();
        values.put("id",user.getId());
        values.put("userName",user.getUserName());
        values.put("passWord",user.getPassWord());
        values.put("avatar",user.getAvatar());
        values.put("displayName",user.getDisplayName());
        values.put("isAdmin",user.isAdmin());
        long check=database.update("User",values,"id=?",
                new String[]{String.valueOf(user.getId())});
        return check;
    }

    public ArrayList<User> getListUser()
    {
        ArrayList<User> list=new ArrayList<>();
        database=sqlHelper.getReadableDatabase();

        Cursor cursor=database.rawQuery("Select * from User",null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            do{
                list.add(new User(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5) != 0
                ));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public int getIdUserWithNameandPass(String userName,String passWord)
    {
        database=sqlHelper.getReadableDatabase();

        Cursor cursor=database.rawQuery("SELECT id FROM User WHERE userName=\""+userName+"\" and password=\""+passWord+"\"",null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
        return  cursor.getInt(0);
        }
        return -1;
    }

    public long createUser(User user)
    {
        database=sqlHelper.getReadableDatabase();

        Cursor cursor=database.rawQuery("SELECT id FROM User WHERE userName=\""+user.getUserName()+"\" or displayName=\""+user.getDisplayName()+"\"",null);
        if(cursor.getCount()>0)
        {
            return -1;
        }
        return addUser(user);
    }

    public int getMaxId()
    {
        database=sqlHelper.getReadableDatabase();
        Cursor cursor=database.rawQuery("Select Max(id) from User",null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return -1;
    }
    public String getNameWithId(int id)
    {
        database=sqlHelper.getReadableDatabase();
        String name="";
        Cursor cursor=database.rawQuery("SELECT displayName FROM User WHERE id="+id,null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            name=cursor.getString(0);
        }
        return name;
    }

    public User getUserWithId(int id)
    {
        database=sqlHelper.getReadableDatabase();
        Cursor cursor=database.rawQuery("Select * from User where id="+id,null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            return new User(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5) != 0
                );
        }
        return null;
    }

}
