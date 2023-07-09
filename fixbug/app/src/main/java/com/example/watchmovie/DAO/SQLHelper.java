package com.example.watchmovie.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLHelper extends SQLiteOpenHelper {
    Context context;
    public SQLHelper( Context context) {
        super(context, "ToDoDB", null, 2);
    }


    public void QueryData(String sql)
    {
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetData(String sql)
    {
        SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        CreateBanner(sqLiteDatabase);
        CreateCate(sqLiteDatabase);
        CreateMovie(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i!=i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS AnhBia");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Category");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CateItem");
            onCreate(sqLiteDatabase);
        }
    }


    private void CreateBanner(SQLiteDatabase sqLiteDatabase)
    {
        String sql="CREATE TABLE AnhBia (\n" +
                "  id integer PRIMARY KEY,\n" +
                "  movieName text,\n" +
                "  imageUrl text,\n" +
                "  fileUrl text\n" +
                "  )";
        sqLiteDatabase.execSQL(sql);//tao bang


        String insertData="INSERT INTO AnhBia VALUES(1,\"Avengers: Endgame\",\"https://lumiere-a.akamaihd.net/v1/images/p_avengersendgame_19751_e14a0104.jpeg?region=0%2C0%2C540%2C810\",\"https://www.youtube.com/watch?v=TcMBFSGVi1c\"),\n" +
                "(2,\"Spider-Man: Across The Spider-Verse\",\"https://pbs.twimg.com/media/FvDSl2VagAAHrbl.jpg:large\",\"https://www.youtube.com/watch?v=shW9i6k8cB0\"),\n" +
                "(3,\"Avatar: Dòng chảy của nước\",\"https://i.imgur.com/sycdQ1H.jpg\",\"https://www.youtube.com/watch?v=bf4yyStDWHE\"),\n" +
                "(4,\"Puss in Boots: The Last Wish\",\"https://cdn.hmv.com/r/w-640/hmv/files/f7/f7daf52d-aaf3-4abc-ab2f-681fd9d2fb83.jpg\",\"https://www.youtube.com/watch?v=xgZLXyqbYOc\"),\n" +
                "(5,\"Tori and Lokita\",\"https://dx35vtwkllhj9.cloudfront.net/picturehouseentertainment/tori-and-lokita/images/regions/gb/onesheet.jpg\",\"https://www.youtube.com/watch?v=QoQn1gAO0Lg\")";
        sqLiteDatabase.execSQL(insertData);//them du lieu banner o muc home
    }

    private void CreateCate(SQLiteDatabase sqLiteDatabase)
    {
        String sql="CREATE TABLE Category(\n" +
                "  cateId integer PRIMARY KEY,\n" +
                "  cateTitle text\n" +
                ")";
        sqLiteDatabase.execSQL(sql);//tao bang


        String insertData="INSERT INTO Category VALUES(0,\"HOT\")" ;
        sqLiteDatabase.execSQL(insertData);//them du lieu banner o muc home
    }

    private void CreateMovie(SQLiteDatabase sqLiteDatabase)
    {
        String sql="CREATE TABLE CateItem(\n" +
                "    id integer PRIMARY KEY,\n" +
                "    movieName text,\n" +
                "    imgUrl text,\n" +
                "    fileurl text,\n" +
                "    idCate integer,\n" +
                "    yeuThich integer\n" +
                ")";
        sqLiteDatabase.execSQL(sql);//tao bang

        String insertData="INSERT INTO CateItem VALUES(1,\"Avengers: Endgame\",\"https://lumiere-a.akamaihd.net/v1/images/p_avengersendgame_19751_e14a0104.jpeg?region=0%2C0%2C540%2C810\",\"https://www.youtube.com/watch?v=TcMBFSGVi1c\",0,0),\n" +
                "(2,\"Spider-Man: Across The Spider-Verse\",\"https://pbs.twimg.com/media/FvDSl2VagAAHrbl.jpg:large\",\"https://www.youtube.com/watch?v=shW9i6k8cB0\",0,0),\n" +
                "(3,\"Avatar: Dòng chảy của nước\",\"https://i.imgur.com/sycdQ1H.jpg\",\"https://www.youtube.com/watch?v=bf4yyStDWHE\",0,0),\n" +
                "(4,\"Puss in Boots: The Last Wish\",\"https://cdn.hmv.com/r/w-640/hmv/files/f7/f7daf52d-aaf3-4abc-ab2f-681fd9d2fb83.jpg\",\"https://www.youtube.com/watch?v=xgZLXyqbYOc\",0,0),\n" +
                "(5,\"Tori and Lokita\",\"https://dx35vtwkllhj9.cloudfront.net/picturehouseentertainment/tori-and-lokita/images/regions/gb/onesheet.jpg\",\"https://www.youtube.com/watch?v=QoQn1gAO0Lg\",0,0)" ;
        sqLiteDatabase.execSQL(insertData);//them du lieu banner o muc home
    }
}
