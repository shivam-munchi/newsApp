package com.example.delhivery.newsapp;

import android.content.Intent;
import android.os.Bundle;
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

import static com.example.delhivery.newsapp.Constants.BASE_URL;

public class WorldFragment extends Fragment implements MainAdapter.MainAdapterOnClickHandler{

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


    private void getSourcesOnCreateSourceResp()
    {

        ApiClient apiClient = getApiClient();

        Call<SourceResp> call= apiClient.getSourcesSourceResp(Constants.API_KEY);

        call.enqueue(new Callback<SourceResp>() {
            @Override
            public void onResponse(Call<SourceResp> call, Response<SourceResp> response) {
                if(response.isSuccessful() && response.body()!= null)
                {
                    mMainAdapter.setSourceData(response.body());
                }
                else
                {
                    Toast.makeText(getActivity(),"Error id "+response.code(),Toast.LENGTH_LONG).show();

                }

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


    public WorldFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_world, container, false);
        ButterKnife.bind(this,rootView);
        setRecyclerView();
        getSourcesOnCreateSourceResp();
        return rootView;
    }

    @Override
    public void onClick(Sources src) {
        startNewsActivity(src.getId());
    }
}