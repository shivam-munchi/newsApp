package com.example.delhivery.newsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.delhivery.newsapp.Constants.API_KEY;
import static com.example.delhivery.newsapp.Constants.BASE_URL;

@SuppressLint("ValidFragment")
class CountryFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener,MainAdapter.MainAdapterOnClickHandler{

    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;

    @BindView(R.id.recyclerview_mainactivity) public RecyclerView mRecyclerView;

    private MainAdapter mMainAdapter;

    private void setRecyclerView()
    {

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMainAdapter = new MainAdapter(this);

        mRecyclerView.setAdapter(mMainAdapter);
    }


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

        if(s == null)
        {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String key = "country";
            s = sp.getString(key,"");
        }

        ApiClient apiClient = getApiClient();

        Call<SourceResp> call= apiClient.getSourcesSourceRespCountry(s,API_KEY);


        call.enqueue(new Callback<SourceResp>() {
            @Override
            public void onResponse(Call<SourceResp> call, Response<SourceResp> response) {
//                Toast.makeText(getActivity(),"Success",Toast.LENGTH_LONG).show();
                if(response.isSuccessful() && response.body()!=null)
                    mMainAdapter.setSourceData(response.body());
                else
                    Toast.makeText(getActivity(),"Error",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<SourceResp> call, Throwable t) {
                Toast.makeText(getActivity(),"Failure! Try again later!",Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }



    private void startNewsActivity(String src)
    {
        Intent i = new Intent(getActivity(),NewsActivity.class);
        i.putExtra("articleSource",src);
        startActivity(i);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(PREFERENCES_HAVE_BEEN_UPDATED)
        {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String key = "country";
            String s = sp.getString(key,"");
            refreshDataCountry(s);

            PREFERENCES_HAVE_BEEN_UPDATED = false;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(this);
    }


    public CountryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_country, container, false);
        ButterKnife.bind(this,rootView);
        setRecyclerView();
        refreshDataCountry(null);
        return rootView;
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        PREFERENCES_HAVE_BEEN_UPDATED=true;
    }

    @Override
    public void onClick(Sources src) {
        startNewsActivity(src.getId());
    }
}
