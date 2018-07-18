package com.example.delhivery.newsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.newsTitle) TextView mTitle;
    @BindView(R.id.newsDesc)  TextView mDesc;
    @BindView(R.id.newsAuthor) TextView mAuthor;
    @BindView(R.id.newsUrl) TextView mUrl;
    @BindView(R.id.newsPublishedAt) TextView mPublishedAt;
    @BindView(R.id.imageViewDetail) ImageView mImage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent i =getIntent();
        String title = i.getStringExtra("articleTitle");
        String author = i.getStringExtra("articleAuthor");
        String url = i.getStringExtra("articleUrl");
        String desc = i.getStringExtra("articleDesc");
        String publishedAt = i.getStringExtra("articlePublishedAt");
        String UrlToImage = i.getStringExtra("articleUrlToImage");

        ButterKnife.bind(this);

        mTitle.setText(title);
        mAuthor.append(": "+author);
        mPublishedAt.append(": "+publishedAt);
        mUrl.append(": "+url);
        mDesc.setText(desc);
        Picasso.get().load(UrlToImage).resize(150,100).into(mImage);
    }
}
