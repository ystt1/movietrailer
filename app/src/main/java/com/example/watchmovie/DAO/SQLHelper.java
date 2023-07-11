package com.example.watchmovie.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.watchmovie.R;

import java.io.ByteArrayOutputStream;

public class SQLHelper extends SQLiteOpenHelper {
    Context context;
    public SQLHelper( Context context) {
        super(context, "test", null, 3);
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
        CreateUser(sqLiteDatabase);
        CreatInteraction(sqLiteDatabase);
        CreateComment(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i!=i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS AnhBia");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Category");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CateItem");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS User");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Interaction");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Comment");
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


        String insertData="INSERT into Category VALUES(0,\"Hot\"),\n" +
                "(1,\"Mới\"),\n" +
                "(2,\"Kids\"),\n" +
                "(3,\"TV Shows\"),\n" +
                "(4,\"Hành động\"),\n" +
                "(5,\"Kinh dị\")" ;
        sqLiteDatabase.execSQL(insertData);//them du lieu banner o muc home
    }

    private void CreateMovie(SQLiteDatabase sqLiteDatabase)
    {
        String sql="CREATE TABLE CateItem(\n" +
                "    id integer PRIMARY KEY,\n" +
                "    movieName text,\n" +
                "    imgUrl text,\n" +
                "    fileurl text,\n" +
                "    idCate integer\n" +
                ")";
        sqLiteDatabase.execSQL(sql);//tao bang

        String insertData="INSERT INTO CateItem VALUES(1,\"Avengers: Endgame\",\"https://lumiere-a.akamaihd.net/v1/images/p_avengersendgame_19751_e14a0104.jpeg?region=0%2C0%2C540%2C810\",\"https://www.youtube.com/watch?v=TcMBFSGVi1c\",0),\n" +
                "(2,\"Spider-Man: Across The Spider-Verse\",\"https://pbs.twimg.com/media/FvDSl2VagAAHrbl.jpg:large\",\"https://www.youtube.com/watch?v=shW9i6k8cB0\",0),\n" +
                "(3,\"Avatar: Dòng chảy của nước\",\"https://i.imgur.com/sycdQ1H.jpg\",\"https://www.youtube.com/watch?v=bf4yyStDWHE\",0),\n" +
                "(4,\"Puss in Boots: The Last Wish\",\"https://cdn.hmv.com/r/w-640/hmv/files/f7/f7daf52d-aaf3-4abc-ab2f-681fd9d2fb83.jpg\",\"https://www.youtube.com/watch?v=xgZLXyqbYOc\",0),\n" +
                "(5,\"Tori and Lokita\",\"https://dx35vtwkllhj9.cloudfront.net/picturehouseentertainment/tori-and-lokita/images/regions/gb/onesheet.jpg\",\"https://www.youtube.com/watch?v=QoQn1gAO0Lg\",0),\n" +
                "(6,\"Minions 2: The Rise of Gru\",\"https://m.media-amazon.com/images/M/MV5BNDM3YWEwYTMtNmY3ZS00YzJiLWFlNWItOWFmNjY0YzA4ZDE3XkEyXkFqcGdeQXVyMTA3MDk2NDg2._V1_.jpg\",\"https://www.youtube.com/watch?v=6DxjJzmYsXo\",2),\n" +
                "(7,\"Doraemon Movie 2023: Nobita’s Sky Utopia\",\"https://www.cgv.vn/media/catalog/product/cache/1/image/c5f0a1eff4c394a251036189ccddaacd/m/a/main_poster_-_dmmovie2023.jpg\",\"https://www.youtube.com/watch?v=bUTfUVLP_Zk\",0),\n" +
                "(8,\"Frozen II (2019\",\"https://upload.wikimedia.org/wikipedia/en/8/89/Frozen_II_%282019_animated_film%29.jpg\",\"https://www.youtube.com/watch?v=Zi4LMpSDccc\",2),\n" +
                "(9,\"Elemental 2023\",\"https://upload.wikimedia.org/wikipedia/en/4/4d/Elemental_final_poster.jpg\",\"https://www.youtube.com/watch?v=hXzcyx9V0xw\",2),\n" +
                "(10,\"Ruby Thủy Quái Tuổi Teen\",\"https://www.cgv.vn/media/catalog/product/cache/1/image/1800x/71252117777b696995f01934522c402d/p/o/poster-kraken.jpg\",\"https://www.youtube.com/watch?v=MqTNbromP-0\",2),\n" +
                "(11,\"Wednesday\",\"https://upload.wikimedia.org/wikipedia/vi/6/66/Wednesday_Netflix_series_poster.png\",\"https://www.youtube.com/watch?v=Di310WS8zLk\",3),\n" +
                "(12,\"Arcane\",\"https://m.media-amazon.com/images/M/MV5BYmU5OWM5ZTAtNjUzOC00NmUyLTgyOWMtMjlkNjdlMDAzMzU1XkEyXkFqcGdeQXVyMDM2NDM2MQ@@._V1_FMjpg_UX1000_.jpg\",\"https://www.youtube.com/watch?v=3Svs_hl897c\",3),\n" +
                "(13,\"Girl From Nowhere 2\",\"https://m.media-amazon.com/images/M/MV5BYzZjOTg3MWQtMjE0NC00ZGJhLTg3NzAtNWRlZmY2Y2QwYTYyXkEyXkFqcGdeQXVyMTEzMTI1Mjk3._V1_.jpg\",\"https://www.youtube.com/watch?v=WYAMLoLOVYI\",3),\n" +
                "(14,\"Squid Game\",\"https://m.media-amazon.com/images/M/MV5BYWE3MDVkN2EtNjQ5MS00ZDQ4LTliNzYtMjc2YWMzMDEwMTA3XkEyXkFqcGdeQXVyMTEzMTI1Mjk3._V1_FMjpg_UX1000_.jpg\",\"https://www.youtube.com/watch?v=oqxAJKy0ii4\",3),\n" +
                "(15,\"The Glory Part 2\",\"https://kenh14cdn.com/203336854389633024/2023/2/9/3300257244074930015998526165443108377177265n-16759059550781859998525.jpg\",\"https://www.youtube.com/watch?v=rvJP7sAhXk4\",3),\n" +
                "(16,\"Mission: Impossible – Dead Reckoning Part 1\",\"https://dx35vtwkllhj9.cloudfront.net/paramountpictures/mission-impossible-7/images/regions/us/onesheet.jpg\",\"https://www.youtube.com/watch?v=2m1drlOZSDw\",4),\n" +
                "\n" +
                "(17,\"Kingsman: The Secret Service\",\"https://prod-ripcut-delivery.disney-plus.net/v1/variant/disney/162022A2C259144C89780911DFDB95B194DA9F4A463B3D71AC6E023D2440960B/scale?width=1200&aspectRatio=1.78&format=jpeg\",\"https://www.youtube.com/watch?v=kl8F-8tR8to\",4),\n" +
                "\n" +
                "(18,\"Dunkirk\",\"https://pgtipsonfilms.files.wordpress.com/2017/09/dunkirk-title-banner.jpg\",\"https://www.youtube.com/watch?v=F-eMt3SrfFU\",4),\n" +
                "\n" +
                "(19,\"Fast X\",\"https://movies.universalpictures.com/media/fastx-1900x3000-unipics-poster-1-1-1-6488d900807c7-1.jpg\",\"https://www.youtube.com/watch?v=1fvzVMmARqY\",4),\n" +
                "\n" +
                "(20,\"John Wick: Chapter 4\",\"https://upload.wikimedia.org/wikipedia/en/thumb/d/d0/John_Wick_-_Chapter_4_promotional_poster.jpg/220px-John_Wick_-_Chapter_4_promotional_poster.jpg\",\"https://www.youtube.com/watch?v=qEVUtrk8_B4\",4),\n" +
                "(21,\"Smile\",\"https://upload.wikimedia.org/wikipedia/en/7/7f/Smile_%282022_film%29.jpg\",\"https://www.youtube.com/watch?v=BcDK7lkzzsU\",5),\n" +
                "\n" +
                "(22,\"Five Nights at Freddy's\",\"https://movies.universalpictures.com/media/fnf-poster-64643a920d591-1.jpg\",\"https://www.youtube.com/watch?v=0VH9WCFV6XQ\",5),\n" +
                "\n" +
                "(23,\"Scream VI\",\"https://upload.wikimedia.org/wikipedia/en/thumb/c/c9/Scream_VI_poster.jpg/220px-Scream_VI_poster.jpg\",\"https://www.youtube.com/watch?v=h74AXqw4Opc\",5),\n" +
                "\n" +
                "(24,\"Annabelle Comes Home\",\"https://upload.wikimedia.org/wikipedia/en/5/50/AnnabelleComesHomePoster.jpg\",\"https://www.youtube.com/watch?v=bCxm7cTpBAs\",5),\n" +
                "\n" +
                "(25,\"Nope\",\"https://upload.wikimedia.org/wikipedia/en/thumb/e/e6/Nope_%28film%29_poster.jpg/220px-Nope_%28film%29_poster.jpg\",\"https://www.youtube.com/watch?v=nwQo9RWVhnE\",5)\n" +
                "\n" ;
        sqLiteDatabase.execSQL(insertData);//them du lieu banner o muc home
    }


    private void CreateUser(SQLiteDatabase sqLiteDatabase)
    {
        String sql="CREATE TABLE User(\n" +
                "  id interger PRIMARY KEY,\n" +
                "    userName text,\n" +
                "    passWord text,\n" +
                "    avatar blob,\n" +
                "    displayName text,\n" +
                "    isAdmin bool\n" +
                ")";
        sqLiteDatabase.execSQL(sql);//tao bang



        String insertData=("INSERT into User VALUES(0,\"Tuan\",\"tuan4105\",null,\"admin\",true)");
        sqLiteDatabase.execSQL(insertData);//them du lieu banner o muc home
    }



    private void CreatInteraction(SQLiteDatabase sqLiteDatabase)
    {
        String sql="Create table Interaction(\n" +
                "  id integer PRIMARY key,\n" +
                "  UserId integer,\n" +
                "  MovieId integer,\n" +
                "  yeuThich integer,\n" +
                "  rating integer\n" +
                ")";
        sqLiteDatabase.execSQL(sql);//tao bang
    }

    private void CreateComment(SQLiteDatabase sqLiteDatabase)
    {
        String sql="create table Comment(\n" +
                "  id integer PRIMARY key,\n" +
                "  UserId integer,\n" +
                "  MovieId integer,\n" +
                "  comment text,\n" +
                "  dateTime text\n" +
                ")\n";
        sqLiteDatabase.execSQL(sql);//tao bang
    }
}
