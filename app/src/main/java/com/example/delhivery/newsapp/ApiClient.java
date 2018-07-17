package com.example.delhivery.newsapp;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {

    @GET("/sources")
    Call<JsonElement> getSources(@Query("apiKey") String apiKey);

    @GET("/sources")
    Call<ErrorResp> checkError(@Query("apiKey") String apiKey);

    @GET("/sources")
    Call<Void> getSourcesVoid(@Query("apiKey") String apiKey);

    @GET("/sources")
    Call<SourceResp> getSourcesSourceResp(@Query("apiKey") String apiKey);

    @GET("/sources")
    Call<SourceResp> getSourcesSourceRespCountry(@Query("country") String country,
            @Query("apiKey") String apiKey);

    @GET("everything")
    Call<NewsResp> getNews(@Query("sources") String source,
                               @Query("apiKey") String apiKey);


}
