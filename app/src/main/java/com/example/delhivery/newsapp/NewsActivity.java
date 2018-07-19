package com.example.delhivery.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.delhivery.newsapp.DB.DBArticleStructure;
import com.example.delhivery.newsapp.DB.DBArticleStructureDao;
import com.example.delhivery.newsapp.DB.DaoSession;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.delhivery.newsapp.Constants.API_KEY;
import static com.example.delhivery.newsapp.Constants.BASE_URL;

public class NewsActivity extends AppCompatActivity implements NewsAdapter.NewsAdapterOnClickHandler {


    private RecyclerView mRecyclerView;

    private NewsAdapter mNewsAdapter;

    private static String newsSource = null;


    ArrayList<ArticleStructure> getNewsFromDB()
    {
        DaoSession mDaoSession =  ((NewsApp)getApplication()).getDaoSession();
        ArrayList<ArticleStructure> articles = new ArrayList<ArticleStructure>();
        getSourceFromIntent();
        Log.d("sdata","a");
        List<DBArticleStructure> dbArticleStructures = (List<DBArticleStructure>) mDaoSession.getDBArticleStructureDao().queryBuilder().where(DBArticleStructureDao.Properties.Id.eq(newsSource)).list();
        Log.d("sdata","b");
        for (int idx = 0; idx < dbArticleStructures.size() ; idx++)
        {
            ArticleStructure article = new ArticleStructure();
            article.setSource(new ArticleSource(dbArticleStructures.get(idx).getId(),dbArticleStructures.get(idx).getName()));
            article.setAuthor(dbArticleStructures.get(idx).getAuthor());
            article.setDescription(dbArticleStructures.get(idx).getDescription());
            article.setTitle(dbArticleStructures.get(idx).getTitle());
            article.setPublishedAt(dbArticleStructures.get(idx).getPublishedAt());
            article.setUrl(dbArticleStructures.get(idx).getUrl());
            article.setUrlToImage(dbArticleStructures.get(idx).getUrlToImage());


            articles.add(article);

        }
        return articles;
    }

    boolean checkIfNewsInDB()
    {
        DaoSession mDaoSession =  ((NewsApp)getApplication()).getDaoSession();
        getSourceFromIntent();
        Long cnt = mDaoSession.getDBArticleStructureDao().queryBuilder().where(DBArticleStructureDao.Properties.Id.eq(newsSource)).count();
        if(cnt == 0)
        {
            return false;
        }
        return true;
    }


    void deleteNewsFromDB()
    {
        DaoSession mDaoSession =  ((NewsApp)getApplication()).getDaoSession();
        mDaoSession.getDBArticleStructureDao().deleteAll();
    }


    void addNewsToDB(ArrayList<ArticleStructure> articleStructures1)
    {
        DaoSession mDaoSession =  ((NewsApp)getApplication()).getDaoSession();
        DBArticleStructure dbArticleStructure = new DBArticleStructure();
        for(int idx = 0;idx < articleStructures1.size();idx++ )
        {
            dbArticleStructure.setId(articleStructures1.get(idx).getSource().getId());
            dbArticleStructure.setName(articleStructures1.get(idx).getSource().getName());
            dbArticleStructure.setAuthor(articleStructures1.get(idx).getAuthor());
            dbArticleStructure.setTitle(articleStructures1.get(idx).getTitle());
            dbArticleStructure.setDescription(articleStructures1.get(idx).getDescription());
            dbArticleStructure.setUrl(articleStructures1.get(idx).getUrl());
            dbArticleStructure.setUrlToImage(articleStructures1.get(idx).getUrlToImage());
            dbArticleStructure.setPublishedAt(articleStructures1.get(idx).getPublishedAt());

            Log.d("a","b");

            mDaoSession.getDBArticleStructureDao().insert(dbArticleStructure);
        }
    }

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
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build());

        Retrofit retrofit = builder.client(httpClient.build()).build();

        ApiClient apiClient = retrofit.create(ApiClient.class);

        getSourceFromIntent();

        Call<NewsResp> call= apiClient.getNews(newsSource,API_KEY);

        call.enqueue(new Callback<NewsResp>() {
            @Override
            public void onResponse(Call<NewsResp> call, Response<NewsResp> response) {
                if(response.isSuccessful() && response.body().getArticles()!=null)
                {
//                    Toast.makeText(NewsActivity.this,"Success! News.",Toast.LENGTH_LONG).show();
                    mNewsAdapter.setNewsData(response.body());
                    Log.d("this","yes");
                    ArrayList<ArticleStructure> articles= response.body().getArticles();

                    addNewsToDB(articles);
                    Log.d("what","is");
                }
                else
                {
                    Toast.makeText(NewsActivity.this,"Error id "+response.code(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NewsResp> call, Throwable t) {
                Toast.makeText(NewsActivity.this,"Failure!",Toast.LENGTH_LONG).show();
                t.printStackTrace();
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
    protected void onResume() {
        super.onResume();

        if(checkIfNewsInDB())
        {
            ArrayList<ArticleStructure> articles = getNewsFromDB();
            NewsResp newsResp = new NewsResp("ok",articles.size(),articles);
            mNewsAdapter.setNewsData(newsResp);
        }
        else
        {
            getNews();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteNewsFromDB();
    }

    @Override
    public void onClick(ArticleStructure article) {
        startDetailActivity(article);
    }
}
