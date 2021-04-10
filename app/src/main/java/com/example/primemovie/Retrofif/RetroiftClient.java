package com.example.primemovie.Retrofif;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroiftClient { //https://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/api/banner_movie.json
    public static final String BASE_URL = "https://androidappsforyoutube.s3.ap-south-1.amazonaws.com/primevideo/api/";

    public static ApiInterface getRetrofitClient()
    {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl(BASE_URL);
                return builder.build().create(ApiInterface.class);
    }
}