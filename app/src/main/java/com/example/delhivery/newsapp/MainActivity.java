//package com.example.delhivery.newsapp;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.widget.Toast;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//import okhttp3.OkHttpClient;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//import static com.example.delhivery.newsapp.Constants.API_KEY;
//import static com.example.delhivery.newsapp.Constants.BASE_URL;
//
//public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener,MainAdapter.MainAdapterOnClickHandler{
//
//
//    private final String TAG = MainActivity.class.getSimpleName();
//
//    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;
//
//    private RecyclerView mRecyclerView;
//
//
//    private MainAdapter mMainAdapter;
//
//    private void setRecyclerView()
//    {
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_mainactivity);
//
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//
//        mRecyclerView.setLayoutManager(layoutManager);
//
//        mRecyclerView.setHasFixedSize(true);
//
//        mMainAdapter = new MainAdapter(this);
//
//        mRecyclerView.setAdapter(mMainAdapter);
//    }
//
//
//    ApiClient getApiClient()
//    {
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//        Gson gson = new GsonBuilder()
//                .setLenient().create();
//
//        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(httpClient.build());
//
//
//
//        Retrofit retrofit = builder.client(httpClient.build()).build();
//
//        ApiClient apiClient = retrofit.create(ApiClient.class);
//
//        return apiClient;
//    }
//
//
//    private void refreshDataCountry(String s) {
//        ApiClient apiClient = getApiClient();
//
//        Call<SourceResp> call= apiClient.getSourcesSourceRespCountry(s,API_KEY);
//
//
//        call.enqueue(new Callback<SourceResp>() {
//            @Override
//            public void onResponse(Call<SourceResp> call, Response<SourceResp> response) {
//                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
//                mMainAdapter.setSourceData(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<SourceResp> call, Throwable t) {
//                Toast.makeText(MainActivity.this,"Failure! Try again later!",Toast.LENGTH_LONG).show();
//
//                String s = t.toString();
//                t.printStackTrace();
//            }
//        });
//    }
//
//
//    private void getSourcesOnCreateSourceResp()
//    {
//
//        ApiClient apiClient = getApiClient();
//
//        Call<SourceResp> call= apiClient.getSourcesSourceResp(Constants.API_KEY);
//
//        call.enqueue(new Callback<SourceResp>() {
//            @Override
//            public void onResponse(Call<SourceResp> call, Response<SourceResp> response) {
//                if(response.isSuccessful() && response.body()!= null)
//                {
//                    Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
//                    mMainAdapter.setSourceData(response.body());
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this,"Error id "+response.code(),Toast.LENGTH_LONG).show();
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<SourceResp> call, Throwable t) {
////                Log.v(TAG,  ""+call.request().url());
//                Toast.makeText(MainActivity.this,"Failure! Try again later!",Toast.LENGTH_LONG).show();
////                Log.v(TAG, t.toString());
//                String s = t.toString();
//                t.printStackTrace();
//            }
//        });
//    }
//
//
//    private void startNewsActivity(String src)
//    {
//        Intent i = new Intent(MainActivity.this,NewsActivity.class);
//        i.putExtra("articleSource",src);
//        startActivity(i);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.d("a","yes");
//        setContentView(R.layout.activity_main);
//
//        setRecyclerView();
//
//        getSourcesOnCreateSourceResp();
//
//        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
//
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(PREFERENCES_HAVE_BEEN_UPDATED)
//        {
//            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
//            String key = "country";
//            String s = sp.getString(key,"");
//            refreshDataCountry(s);
//
//            PREFERENCES_HAVE_BEEN_UPDATED = false;
//        }
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.news_menu,menu);
//        return true;
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if(id == R.id.news_settings)
//        {
//            Intent optionsIntent = new Intent(MainActivity.this,SettingsActivity.class);
//            startActivity(optionsIntent);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
//        PREFERENCES_HAVE_BEEN_UPDATED = true;
//    }
//
//
//    @Override
//    public void onClick(Sources src) {
//        startNewsActivity(src.getId());
//    }
//}
