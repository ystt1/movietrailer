package com.example.watchmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.watchmovie.BienToanCuc.BienToanCuc;
import com.example.watchmovie.DAO.CateItemDAO;
import com.example.watchmovie.DAO.CommentDAO;
import com.example.watchmovie.DAO.InteractionDAO;
import com.example.watchmovie.adapter.CommentRecyclerAdapter;
import com.example.watchmovie.adapter.MainRecycleAdapter;
import com.example.watchmovie.model.CateItem;
import com.example.watchmovie.model.Comment;
import com.example.watchmovie.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MovieDetails extends AppCompatActivity {

    SwipeRefreshLayout swipeRefreshLayout;
    int idUser;
    InteractionDAO interactionDAO;
    ImageView imageView;
    TextView textView,tvMore,tvLove,tvRating;
    Button button;
    String mName,mImg,mFile;
    int iYeuThich,mId,iComment=1;
    Context context=this;
    CateItemDAO cateItemDAO;
    Button textViewYeuThich;
    RatingBar ratingBar;
    int mRating;
    TextInputEditText comment;
    TextInputLayout commentLayout;
    CommentDAO commentDAO;
    List<Comment> commentList=new ArrayList<>();

    CateItem cateItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if(BienToanCuc.getInstance().getLoggedInUserID()==-1)
        {
            Intent i=new Intent(this,LoginAcivity.class);
            startActivity(i);
        }else {
            AnhXaVaKhaiBao();
            onClickWatchTrailer();
            setTextViewYeuThich();
            onClickYeuThichBtn();
            ratingBarChange();
            CommentRecycler();
            onCLickComment();
            onSwipe();
            onClickMoreComment();
        }
    }

    void onClickMoreComment()
    {
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iComment+=1;
                CommentRecycler();
            }
        });
    }

    void ratingBarChange()
    {
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRating= (int) v;
                interactionDAO.setRating(idUser,mId,mRating);
                float everageRating=interactionDAO.getEverageRating(mId);
                cateItem.setRating(everageRating);
                cateItemDAO.updateCateItem(cateItem);
                tvRating.setText(String.valueOf(cateItem.getRating()));
            }
        });
    }

    void onSwipe()
    {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = new Intent(MovieDetails.this, MovieDetails.class);
                intent.putExtra("movieId",mId);
                intent.putExtra("movieName",mName);
                intent.putExtra("movieImageUrl",mImg);
                intent.putExtra("movieFileUrl",mFile);
                startActivity(intent);
                finish();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    void setTextViewYeuThich()
    {
        if(iYeuThich==0)
            textViewYeuThich.setText("Thích");
            else
            textViewYeuThich.setText("Đã thích");
    }

    void onClickYeuThichBtn()
    {
        textViewYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(iYeuThich==1) {
                    cateItem.setLuotThich(cateItem.getLuotThich()-1);
                    cateItemDAO.updateCateItem(cateItem);
                    iYeuThich = 0;
                }
                else {
                    cateItem.setLuotThich(cateItem.getLuotThich()+1);
                    cateItemDAO.updateCateItem(cateItem);
                    iYeuThich = 1;
                }
                interactionDAO.setYeuThich(idUser,mId,iYeuThich);
                setTextViewYeuThich();
                tvLove.setText(String.valueOf(cateItem.getLuotThich()));
            }
        });
    }


    void CommentRecycler()
    {

        commentList=commentDAO.getListComment(idUser,mId,iComment);

        RecyclerView recyclerView=findViewById(R.id.recycler_comment);
        CommentRecyclerAdapter commentRecyclerAdapter;
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        commentRecyclerAdapter=new CommentRecyclerAdapter(this,commentList);
        recyclerView.setAdapter(commentRecyclerAdapter);
    }



    void onCLickComment()
    {
        commentLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String dateTime = dateFormat.format(calendar.getTime());
                String text= String.valueOf(comment.getText());
                if(text.equals("")==false)
                {
                    commentDAO.addComment(idUser,mId,text,dateTime);
                    comment.setText("");
                    CommentRecycler();
                }
            }
        });
    }

    void onClickWatchTrailer()
    {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mFile)));
            }
        });
    }

    void AnhXaVaKhaiBao()
    {
        swipeRefreshLayout=findViewById(R.id.Detail_Swipe);
        imageView=findViewById(R.id.movie_img);
        textView=findViewById(R.id.tvMovie);
        button=findViewById(R.id.playButton);
        textViewYeuThich=findViewById(R.id.tvYeuThich);
        ratingBar=findViewById(R.id.ratingBar);
        comment=findViewById(R.id.Input_comment);
        commentLayout=findViewById(R.id.Input_comment_layout);
        tvMore=findViewById(R.id.tv_More);
        tvLove=findViewById(R.id.tv_love);
        tvRating=findViewById(R.id.tv_ratingInDetail);

        idUser=BienToanCuc.getInstance().getLoggedInUserID();

        mId=getIntent().getIntExtra("movieId",0);
        mName=getIntent().getStringExtra("movieName");
        mImg=getIntent().getStringExtra("movieImageUrl");
        mFile=getIntent().getStringExtra("movieFileUrl");

        Glide.with(this).load(mImg).into(imageView);

        cateItemDAO=new CateItemDAO(context);
        interactionDAO=new InteractionDAO(context);
        commentDAO=new CommentDAO(context);


        iYeuThich=interactionDAO.getYeuThich(idUser, mId);
        mRating= interactionDAO.getRating(idUser,mId);
        if(mRating!=-1) {
            ratingBar.setRating(mRating);
        }
        else {
            ratingBar.setRating(0);
        }
        textView.setText(mName);
        cateItem=cateItemDAO.getCateItemWithID(mId);
        tvLove.setText(String.valueOf(cateItem.getLuotThich()));
        tvRating.setText(String.valueOf(cateItem.getRating()));
        commentList.clear();
    }

}