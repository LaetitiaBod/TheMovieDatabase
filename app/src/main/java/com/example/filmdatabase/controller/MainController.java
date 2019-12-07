package com.example.filmdatabase.controller;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.example.filmdatabase.model.Movie;
import com.example.filmdatabase.model.RestListResponse;
import com.example.filmdatabase.restapi.FilmDatabaseRestAPI;
import com.example.filmdatabase.restapi.Singleton;
import com.example.filmdatabase.view.MovieListFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainController {

    private MovieListFragment activity;

    private static final String PREFS = "PREFS";
    private static final String PREFS_FAVORITE = "PREFS_FAVORITE";
    private static final String PREFS_TO_SEE = "PREFS_TO_SEE";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private List<Movie> listMovie;

    public MainController(MovieListFragment activity) { this.activity = activity;}

    public void onCreate() {

        sharedPreferences = activity.getActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        loadAPI();
    }

    public void loadAPI() {
        gson = new GsonBuilder()
                .setLenient()
                .create();

        FilmDatabaseRestAPI filmAPI = Singleton.getRestApi(activity.getContext());

        Call<RestListResponse> call = filmAPI.getListFilm(activity.idpage, activity.choiceSort);

        call.enqueue(new Callback<RestListResponse>() {
            @Override
            public void onResponse(Call<RestListResponse> call, Response<RestListResponse> response) {
                RestListResponse restListResponse = response.body();
                listMovie = restListResponse.getResults();
                activity.showList(listMovie);
            }

            @Override
            public void onFailure(Call<RestListResponse> call, Throwable t) {
                Log.d("ERREUR", "API K.O.");
            }
        });
    }

    public void onFavoriteAdded(Movie currentMovie) {
        List<Movie> favoriteList = getFavoriteList();
        currentMovie.setIs_favorite(true);
        favoriteList.add(currentMovie);
        saveFavoriteList(favoriteList);
    }

    public void onFavoriteRemove(Movie currentMovie) {
        List<Movie> favoriteList = getFavoriteList();
        currentMovie.setIs_favorite(false);
        //favoriteList.clear();
        //saveFavoriteList(favoriteList);
        for (int i = 0; i < favoriteList.size(); i++) {
            if(favoriteList.get(i).getId() == currentMovie.getId()){
                favoriteList.remove(i);
                saveFavoriteList(favoriteList);
            }
        }
    }

    private void saveFavoriteList(List<Movie> listMovie) {
        String listJson = gson.toJson(listMovie);
        sharedPreferences
                .edit()
                .putString(PREFS_FAVORITE, listJson)
                .apply();
    }

    private List<Movie> getFavoriteList() {
        String jsonList = sharedPreferences.getString(PREFS_FAVORITE, null);
        if(jsonList != null){
            String listJson = sharedPreferences.getString(PREFS_FAVORITE, null);
            Type listType = new TypeToken<List<Movie>>(){}.getType();
            return gson.fromJson(listJson, listType);
        } else {
            return new ArrayList<>();
        }
    }

    public void onToSeeAdded(Movie currentMovie) {
        List<Movie> toseeList = getToSeeList();
        currentMovie.setIs_to_see(true);
        toseeList.add(currentMovie);
        saveToSeeList(toseeList);
    }

    public void onToSeeRemove(Movie currentMovie) {
        List<Movie> toseeList = getToSeeList();
        currentMovie.setIs_to_see(false);
        //toseeList.clear();
        //saveToSeeList(toseeList);
        for (int i = 0; i < toseeList.size(); i++) {
            if(toseeList.get(i).getId() == currentMovie.getId()){
                toseeList.remove(i);
                saveToSeeList(toseeList);
            }
        }
    }

    private void saveToSeeList(List<Movie> listMovie) {
        String listJson = gson.toJson(listMovie);
        sharedPreferences
                .edit()
                .putString(PREFS_TO_SEE, listJson)
                .apply();
    }

    private List<Movie> getToSeeList() {
        String jsonList = sharedPreferences.getString(PREFS_TO_SEE, null);
        if(jsonList != null){
            String listJson = sharedPreferences.getString(PREFS_TO_SEE, null);
            Type listType = new TypeToken<List<Movie>>(){}.getType();
            return gson.fromJson(listJson, listType);
        } else {
            return new ArrayList<>();
        }
    }
}
