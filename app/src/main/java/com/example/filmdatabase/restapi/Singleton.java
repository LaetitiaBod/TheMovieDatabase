package com.example.filmdatabase.restapi;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singleton{
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    private static FilmDatabaseRestAPI restAPI = null;

    public static FilmDatabaseRestAPI getRestApi(Context context){
        if(restAPI == null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cache(new Cache(context.getCacheDir(), (5 * 1024 * 1024)))
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();

            restAPI = retrofit.create(FilmDatabaseRestAPI.class);
        }
        return restAPI;
    }
}
