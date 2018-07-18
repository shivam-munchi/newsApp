package com.example.delhivery.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.delhivery.newsapp.Constants.API_KEY;
import static com.example.delhivery.newsapp.Constants.BASE_URL;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    @BindView(R.id.b1) Button b1;

    @BindView(R.id.b2) Button b2;

    @BindView(R.id.b3) Button b3;

    @BindView(R.id.b4) Button b4;

    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;


    ApiClient getApiClient()
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Gson gson = new GsonBuilder()
                .setLenient().create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build());



        Retrofit retrofit = builder.client(httpClient.build()).build();

        ApiClient apiClient = retrofit.create(ApiClient.class);

        return apiClient;
    }


    private void refreshDataCountry(String s) {
        ApiClient apiClient = getApiClient();

        Call<SourceResp> call= apiClient.getSourcesSourceRespCountry(s,API_KEY);


        call.enqueue(new Callback<SourceResp>() {
            @Override
            public void onResponse(Call<SourceResp> call, Response<SourceResp> response) {
                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<SourceResp> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failure! Try again later!",Toast.LENGTH_LONG).show();

                String s = t.toString();
                t.printStackTrace();
            }
        });
    }


    private void getSourcesOnCreate()
    {
        ApiClient apiClient = getApiClient();

        Call<JsonElement> call= apiClient.getSources(API_KEY);


        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                String s = response.body().toString();
                Toast.makeText(MainActivity.this,"Success!",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failure!",Toast.LENGTH_LONG).show();
            }

        });
    }


    private void getSourcesOnCreateVoid()
    {

        ApiClient apiClient = getApiClient();

        Call<Void> call= apiClient.getSourcesVoid(API_KEY);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful())
                {
                    Toast.makeText(MainActivity.this,"Success!\n"+"Response code is "+response.code(),Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(MainActivity.this,"Connectted but error",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failure!",Toast.LENGTH_LONG).show();
            }

        });
    }


    private void getSourcesOnCreateSourceResp()
    {

        ApiClient apiClient = getApiClient();

        Call<SourceResp> call= apiClient.getSourcesSourceResp(Constants.API_KEY);

        call.enqueue(new Callback<SourceResp>() {
            @Override
            public void onResponse(Call<SourceResp> call, Response<SourceResp> response) {
                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<SourceResp> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failure! Try again later!",Toast.LENGTH_LONG).show();

                String s = t.toString();
                t.printStackTrace();
            }
        });
    }


    private void startNewsActivity(String s)
    {
        Intent i = new Intent(MainActivity.this,NewsActivity.class);
        i.putExtra("articleSource",s);
        startActivity(i);
    }


    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("a","yes");
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        getSourcesOnCreateSourceResp();

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

//        startNewsActivity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(PREFERENCES_HAVE_BEEN_UPDATED)
        {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            String key = "country";
            String s = sp.getString(key,"");
            refreshDataCountry(s);

            PREFERENCES_HAVE_BEEN_UPDATED = false;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.news_settings)
        {
            Intent optionsIntent = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(optionsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        PREFERENCES_HAVE_BEEN_UPDATED = true;
    }

    public void Button1(View view) {
        startNewsActivity("abc-news");
    }

    public void Button2(View view) {
        startNewsActivity("the-times-of-india");
    }

    public void Button3(View view) {
        startNewsActivity("bbc-news");
    }

    public void Button4(View view) {
        startNewsActivity("google-news");
    }
}
