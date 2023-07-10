package com.example.watchmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.watchmovie.DAO.CateItemDAO;

public class MovieDetails extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    Button button;
    String mName,mImg,mFile;
    int iYeuThich=0,mId;
    Context context=this;
    CateItemDAO cateItemDAO;

    Button textViewYeuThich;

    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        imageView=findViewById(R.id.movie_img);
        textView=findViewById(R.id.tvMovie);
        button=findViewById(R.id.playButton);
        textViewYeuThich=findViewById(R.id.tvYeuThich);
        ratingBar=findViewById(R.id.ratingBar);

        mId=getIntent().getIntExtra("movieId",0);
        mName=getIntent().getStringExtra("movieName");
        mImg=getIntent().getStringExtra("movieImageUrl");
        mFile=getIntent().getStringExtra("movieFileUrl");

        Glide.with(this).load(mImg).into(imageView);
        textView.setText(mName);
        cateItemDAO=new CateItemDAO(context);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mFile)));
            }
        });
        ratingBarChange();
        setTextViewYeuThich();
        onClickYeuThichBtn();
    }



    void ratingBarChange()
    {
        ratingBar.setRating(0);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
            }
        });
    }

    void setTextViewYeuThich()
    {
    }

    void onClickYeuThichBtn()
    {
        textViewYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iYeuThich==1)
                    iYeuThich = 0;
                else
                    iYeuThich=1;
                setTextViewYeuThich();
            }
        });
    }
}