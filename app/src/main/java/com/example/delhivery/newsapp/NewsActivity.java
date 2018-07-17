package com.example.delhivery.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NewsActivity extends AppCompatActivity implements NewsAdapter.NewsAdapterOnClickHandler {


    private RecyclerView mRecyclerView;

    private NewsAdapter mNewsAdapter;

    private static String newsSource = null;


    private void getSourceFromIntent()
    {
        Intent i = getIntent();
        String src = i.getStringExtra("articleSource");
        if(src!=null)
        {
            newsSource=src;
        }
    }

    private void getNews()
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        ApiClient apiClient = retrofit.create(ApiClient.class);

        getSourceFromIntent();

        Call<NewsResp> call= apiClient.getNews(newsSource,MainActivity.API_KEY);

        call.enqueue(new Callback<NewsResp>() {
            @Override
            public void onResponse(Call<NewsResp> call, Response<NewsResp> response) {
                if(response.isSuccessful() && response.body().getArticles()!=null)
                {
//                    Toast.makeText(NewsActivity.this,"Success! News.",Toast.LENGTH_LONG).show();
                    mNewsAdapter.setNewsData(response.body());
                }
                else
                {
                    Toast.makeText(NewsActivity.this,"Error id "+response.code(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NewsResp> call, Throwable t) {
                Toast.makeText(NewsActivity.this,"Failure!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setRecyclerView()
    {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_newsactivity);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mNewsAdapter = new NewsAdapter(this);

        mRecyclerView.setAdapter(mNewsAdapter);
    }

    private void startDetailActivity(ArticleStructure article)
    {
        Intent i = new Intent(NewsActivity.this,DetailActivity.class);
        i.putExtra("articleAuthor",article.getAuthor());
        i.putExtra("articleTitle",article.getTitle());
        i.putExtra("articleDesc",article.getDescription());
        i.putExtra("articlePublishedAt",article.getPublishedAt());
        i.putExtra("articleUrl",article.getUrl());
        i.putExtra("articleUrlToImage",article.getUrlToImage());
        i.putExtra("sourceId",article.getSource().getId());
        i.putExtra("sourceName",article.getSource().getName());
        startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        setRecyclerView();

        getNews();

    }


    @Override
    public void onClick(ArticleStructure article) {
        startDetailActivity(article);
    }
}
