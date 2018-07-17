package com.example.delhivery.newsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class DetailActivity extends AppCompatActivity {

    private TextView mTitle;
    private TextView mDesc;
    private TextView mAuthor;
    private TextView mUrl;
    private TextView mPublishedAt;
    private ImageView mImage;


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if(result!=null)
                bmImage.setImageBitmap(result);
        }
    }

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

        mAuthor = (TextView) findViewById(R.id.newsAuthor);
        mTitle = (TextView) findViewById(R.id.newsTitle);
        mUrl = (TextView) findViewById(R.id.newsUrl);
        mPublishedAt = (TextView) findViewById(R.id.newsPublishedAt);
        mDesc = (TextView) findViewById(R.id.newsDesc);
        mImage = (ImageView) findViewById(R.id.imageViewDetail);

        mTitle.setText(title);
        mAuthor.append(": "+author);
        mPublishedAt.append(": "+publishedAt);
        mUrl.append(": "+url);
        mDesc.setText(desc);
        new DownloadImageTask(mImage)
                .execute(UrlToImage);

    }
}
