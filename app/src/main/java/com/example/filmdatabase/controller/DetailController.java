package com.example.filmdatabase.controller;

import android.util.Log;

import com.example.filmdatabase.model.RestDetailResponse;
import com.example.filmdatabase.restapi.FilmDatabaseRestAPI;
import com.example.filmdatabase.restapi.Singleton;
import com.example.filmdatabase.view.DetailActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailController {

    private DetailActivity activity;

    public DetailController(DetailActivity activity) {
        this.activity = activity;
    }

    public void onCreate() {
            FilmDatabaseRestAPI filmAPI = Singleton.getRestApi(activity);

            Call<RestDetailResponse> call = filmAPI.getId(activity.idFilm);

            call.enqueue(new Callback<RestDetailResponse>() {
                @Override
                public void onResponse(Call<RestDetailResponse> call, Response<RestDetailResponse> response) {
                    RestDetailResponse restDetailResponse = response.body();
                    String overview = restDetailResponse.getOverview();
                    String backdrop_path = restDetailResponse.getBackdrop_path();
                    String release_date = restDetailResponse.getRelease_date();
                    int runtime = restDetailResponse.getRuntime();
                    String tagline = restDetailResponse.getTagline();
                    String title = restDetailResponse.getTitle();
                    Boolean video = restDetailResponse.getVideo();
                    float vote_average = restDetailResponse.getVote_average();

                    activity.showDetails(overview, backdrop_path, release_date, runtime, tagline, title, video, vote_average);
                }

                @Override
                public void onFailure(Call<RestDetailResponse> call, Throwable t) {
                    Log.d("ERREUR", "API K.O.");
                }
            });
        }
}
